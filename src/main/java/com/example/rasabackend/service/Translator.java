package com.example.rasabackend.service;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
public class Translator {
    private static final String apiKey  = "AIzaSyCZuwGEEmhY79fbZlLqJzYd2yb7j-8yMP8";
    private static final Translate translate = TranslateOptions.newBuilder().setApiKey(apiKey).build().getService();
    public String englishTOSinhala(String text){
        Translation translation = translate.translate(text, Translate.TranslateOption.targetLanguage("si"));
        return translation.getTranslatedText();
    }

    public String sinhalaToEnglish(String text){
        Translation translation = translate.translate(text, Translate.TranslateOption.targetLanguage("en"));
        System.out.println(translation.getTranslatedText());
        return translation.getTranslatedText();
    }
}
