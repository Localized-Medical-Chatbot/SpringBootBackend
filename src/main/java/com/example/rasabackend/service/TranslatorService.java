package com.example.rasabackend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class TranslatorService {

    public static JsonNode translateTOSinhala(JsonNode jsonNode){
        String engText = jsonNode.get("text").asText();
        Translator translator = new Translator();
        String sinTest = translator.englishTOSinhala(engText);
        return ((ObjectNode) jsonNode).put("text", sinTest);
    }

    public static JsonNode translateTOEnglish(JsonNode jsonNode){
        String sinText = jsonNode.get("message").asText();
        Translator translator = new Translator();
        String engText = translator.sinhalaToEnglish(sinText);
        return ((ObjectNode) jsonNode).put("message", engText);
    }

    public static JsonNode translatePayloads(JsonNode[] jsonNodes) {
        if (jsonNodes != null && jsonNodes.length > 0) {
            JsonNode firstResponse = jsonNodes[0];

            if (firstResponse.has("buttons")) {
                JsonNode buttonsNode = firstResponse.get("buttons");

                if (buttonsNode.isArray()) {
                    ArrayNode buttonsArray = (ArrayNode) buttonsNode;
                    Translator translator = new Translator();

                    for (JsonNode button : buttonsArray) {
                        if (button.has("title")) {
                            String originalTitle = button.get("title").asText();
                            String translatedTitle = translator.englishTOSinhala(originalTitle);

                            // Update the button title with the translated text
                            ((ObjectNode) button).put("title", translatedTitle);
                        }
                    }
                }
            }
        }

        return jsonNodes[0];
    }

}

