package com.example.rasabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

//package com.example.rasabackend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RasaBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(RasaBackendApplication.class, args);
    }

}


//public class RasaBackendApplication {
//
//    private static final String RASA_SERVER_URL = "http://0.0.0.0:5005/webhooks/rest/webhook";
//
//    public static void main(String[] args) {
//        // Prepare headers
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        // Prepare your message object
//        String userMessage = "hello";
//        String senderId = "837705d4-5a77-4b21-a59a-b599e023519f";
//
//        // Create the request payload
//        String requestPayload = String.format("{\"message\": \"%s\", \"sender\": \"%s\"}", userMessage, senderId);
//
//        // Create the request entity with headers and payload
//        HttpEntity<String> requestEntity = new HttpEntity<>(requestPayload, headers);
//
//        // Create a RestTemplate
//        RestTemplate restTemplate = new RestTemplate();
//
//        // Make a POST request to Rasa server
//        ResponseEntity<String> responseEntity = restTemplate.exchange(
//                RASA_SERVER_URL,
//                HttpMethod.POST,
//                requestEntity,
//                String.class
//        );
//
//        // Get and print the response from Rasa server
//        String rasaResponse = responseEntity.getBody();
//        System.out.println("Rasa's response: " + rasaResponse);
//    }
//}
