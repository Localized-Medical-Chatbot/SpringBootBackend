package com.example.rasabackend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class LLMMessagePostProcess {
    public JsonNode[] createResponseJson(JsonNode originalJson, JsonNode userMessage) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

//            System.out.println(userMessage.toString());
            StringBuilder resultStringBuilder = new StringBuilder();
            for (JsonNode element : userMessage) {
                resultStringBuilder.append(element.asText());
            }


            // Extract recipient_id from the original JSON
            String recipientId = originalJson.path("sender").asText();

            // Create a new JSON with the desired structure
            ObjectNode responseNode = JsonNodeFactory.instance.objectNode();
            responseNode.put("recipient_id", recipientId);
            responseNode.put("text", resultStringBuilder.toString());
            JsonNode[] responseNodes = new JsonNode[]{responseNode};
            return responseNodes;
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
            return new JsonNode[]{JsonNodeFactory.instance.objectNode().put("error", "Error creating response")};
        }
    }
}
