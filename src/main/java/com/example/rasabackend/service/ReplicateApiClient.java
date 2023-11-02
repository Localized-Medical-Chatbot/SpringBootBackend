package com.example.rasabackend.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReplicateApiClient {

    private final String apiUrl = "https://api.replicate.com/v1/predictions";
    private final String apiToken = "Token r8_4ocInb538pLNftazhlF1ZK2xWA1hPit0FGYoJ";// "Token r8_0r4vgAfWiORRR6Kqo7B7VgYxe7MP5VN3uHTMU";

        private final Logger logger = LoggerFactory.getLogger(ReplicateApiClient.class);

        public String getURL(String prompt) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiToken);
            headers.set("Authorization", apiToken); // Add Authorization header

//            4f0a4744c7295c024a1de15e1a63c880d3da035fa1f49bfd344fe076074c8eea
//            bb5ef01913be92f7ee8453525d4e43ee5ce6f1299d8d024985bb681c715be40c
            String requestBody = String.format("{\"version\": \"f4e2de70d66816a838a89eeeb621910adffb0dd0baba3976c96980970978018d\", \"input\": {\"prompt\": \"%s\",\"system_prompt\": \"%s\",\"max_new_tokens\": 256}}", prompt, "Give answers if question is relevant to medical question");

            HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
//            System.out.println(request.toString());
            ResponseEntity<JsonNode> response = new RestTemplate().postForEntity(apiUrl, request, JsonNode.class);

            if (response.getStatusCode() == HttpStatus.valueOf(201)) {
//                System.out.println(response.getBody());
                JsonNode responseBody = response.getBody();

                // Extract the "urls" object from the response
                JsonNode urls = responseBody.path("urls");

                // Extract the "get" URL from the "urls" object
                String getUrl = urls.path("get").asText();
//                System.out.println(getUrl);
                return getUrl;
            } else {
                logger.error("Error: {} - {}", response.getStatusCodeValue(), response.getBody());
                return "Error: " + response.getStatusCodeValue();
            }
        }


    public JsonNode getPrediction(String predictionURL) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiToken);
        headers.set("Authorization", apiToken);

        HttpEntity<JsonNode> entity = new HttpEntity<JsonNode>(headers);
        ResponseEntity<JsonNode> response = new RestTemplate().exchange(
                predictionURL,
                HttpMethod.GET,
                entity,
                JsonNode.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            JsonNode responseBody = response.getBody();
            String status = String.valueOf(responseBody.path("status"));
            System.out.println(status);
            if (status.equals("\"succeeded\"")){
                JsonNode output = responseBody.path("output");
                return output;
            }
            try {
                // Sleep for a certain duration (e.g., 1 second)
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // Handle the exception if needed
                e.printStackTrace();
            }
            return getPrediction(predictionURL);
        } else {
            // Handle error
            logger.error("Error: {} - {}", response.getStatusCodeValue(), response.getBody());
            return null;
        }
    }
}

