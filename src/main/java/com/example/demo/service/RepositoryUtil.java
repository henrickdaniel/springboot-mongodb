package com.example.demo.service;

import org.bson.Document;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RepositoryUtil {

    public static List<String> toStringList(List<Document> documents, String key) {
        List<String> dataBases = new ArrayList<>();
        documents.forEach(document -> dataBases.add(document.getString(key)));
        return dataBases;
    }

    public static LocalDateTime getLocalDateTime(Document document, String key) {
        Date inputDate = document.getDate(key);
        if (inputDate != null) {
            return convertToLocalDateTime(inputDate);
        }
        return null;
    }

    public static LocalDateTime convertToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}