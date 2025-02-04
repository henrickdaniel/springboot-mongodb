package com.example.demo.service;

import com.example.demo.model.Movie;
import com.example.demo.repository.MongoClientInstance;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.StreamSupport;

@Component
public class MovieRepository {

    public List<Movie> showMovies() {
        MongoClient mongoClient = MongoClientInstance.getMongoClient();

        FindIterable<Document> movies = getCollection(mongoClient).find();
        List<Document> documents = StreamSupport.stream(movies.spliterator(), false).toList();

        return documents.stream().map(MovieRepository::getMovie).toList();
    }

    private static MongoCollection<Document> getCollection(MongoClient mongoClient) {
        return mongoClient.getDatabase("sample_mflix").getCollection("movies");
    }

    private static Movie getMovie(Document document) {
        Movie movie = new Movie();
        movie.setTitle(document.getString("title"));
        movie.setPlot(document.getString("plot"));
        movie.setReleased(RepositoryUtil.getLocalDateTime(document, "released"));
        movie.setGenres(document.getList("genres", String.class));
        movie.setRuntime(document.getInteger("runtime"));
        movie.setFullplot(document.getString("fullplot"));
        movie.setLanguages(document.getList("languages", String.class));
        movie.setCast(document.getList("cast", String.class));
        movie.setDirectors(document.getList("directors", String.class));
        movie.setRated(document.getString("rated"));
        movie.set_id(document.getObjectId("_id").toString());
        return movie;
    }


}