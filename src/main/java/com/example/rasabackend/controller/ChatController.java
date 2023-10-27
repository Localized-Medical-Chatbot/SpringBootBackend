package com.example.rasabackend.controller;

import com.example.rasabackend.service.TranslatorService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ChatController {

    @PostMapping("/chat")
    public JsonNode[] handleChatRequest(@RequestBody JsonNode jsonNode) {

//        System.out.println(jsonNode.toString());
        jsonNode = TranslatorService.translateTOEnglish(jsonNode);
//        // Call Rasa server
        String rasaUrl = "http://0.0.0.0:5005/webhooks/rest/webhook";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<JsonNode> requestEntity = new HttpEntity<>(jsonNode, headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<JsonNode[]> responseEntity = restTemplate.exchange(
                rasaUrl,
                HttpMethod.POST,
                requestEntity,
                JsonNode[].class
        );

        // Extract Rasa response
        JsonNode[] rasaResponses = responseEntity.getBody();
        JsonNode[] rasaResponse = rasaResponses != null && rasaResponses.length > 0 ? rasaResponses : null;
        System.out.println(rasaResponse[0].toString());

        for (int i = 0; i < rasaResponses.length; i++) {
            rasaResponses[i]= TranslatorService.translateTOSinhala(rasaResponses[i]);
        }
//        rasaResponse[0] = TranslatorService.translateTOSinhala(rasaResponse[0]);
        rasaResponse[0] = TranslatorService.translatePayloads(rasaResponse);

        // You can perform additional actions here before sending the response to the frontend.
        return rasaResponse;
    }
}



