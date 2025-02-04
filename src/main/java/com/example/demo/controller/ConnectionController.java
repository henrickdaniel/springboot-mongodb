package com.example.demo.controller;

import com.example.demo.service.ConnectionRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class ConnectionController {

    private final ConnectionRepository connectionRepository;

    @GetMapping("/databases")
    public ResponseEntity<List<String>> getDataBases(){
        return new ResponseEntity<>(connectionRepository.getDatabases(), HttpStatus.OK);
    }

}
