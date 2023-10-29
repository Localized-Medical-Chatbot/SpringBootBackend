package com.example.rasabackend.controller;

import com.example.rasabackend.service.LLMMessagePostProcess;
import com.example.rasabackend.service.ReplicateApiClient;
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
        String message = jsonNode.path("message").asText();
        System.out.println(jsonNode.toString());
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

        if (rasaResponses != null && rasaResponses.length > 0) {
//            JsonNode[] rasaResponse = rasaResponses
            System.out.println(rasaResponses[0].toString());

            for (int i = 0; i < rasaResponses.length; i++) {
                rasaResponses[i] = TranslatorService.translateTOSinhala(rasaResponses[i]);
            }
            //        rasaResponse[0] = TranslatorService.translateTOSinhala(rasaResponse[0]);
            rasaResponses[0] = TranslatorService.translatePayloads(rasaResponses);
        }

//        else{
            ReplicateApiClient client = new ReplicateApiClient();
            String prompt = message;

            String predictionURL = client.getURL(prompt);
            // Poll for prediction status
            JsonNode botMessage = client.getPrediction(predictionURL);
            System.out.println(botMessage.toString());
            LLMMessagePostProcess llmMessagePostProcess = new LLMMessagePostProcess();
            rasaResponses = llmMessagePostProcess.createResponseJson(jsonNode,botMessage);
            System.out.println(rasaResponses[0].toString());
        for (int i = 0; i < rasaResponses.length; i++) {
                rasaResponses[i] = TranslatorService.translateTOSinhala(rasaResponses[i]);
            }
            System.out.println(rasaResponses[0].toString());
//        }

        return rasaResponses;
    }
}



