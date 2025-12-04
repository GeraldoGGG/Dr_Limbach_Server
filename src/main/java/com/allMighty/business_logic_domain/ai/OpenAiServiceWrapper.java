package com.allMighty.business_logic_domain.ai;

import org.springframework.stereotype.Service;


import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpenAiServiceWrapper {

/*    private final OpenAiService openAiService;

    public OpenAiServiceWrapper(@Value("${OPENAI_API_KEY}") String apiKey) {
        this.openAiService = new OpenAiService(apiKey);
    }

    *//**
     * Generate a short symptom description for a given analysis name
     *//*
    public String generateSymptomsDescription(String analysisName) {
        // Prompt GPT to generate symptom context
        String prompt = "List common symptoms for the medical analysis called \"" 
                        + analysisName + "\". Return a short description suitable for embeddings.";

        ChatMessage userMessage = new ChatMessage("user", prompt);

        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")   // or "gpt-4" if you have access
                .messages(List.of(userMessage))
                .maxTokens(100)           // short description
                .build();

        var response = openAiService.createChatCompletion(request);

        // Get text from first choice
        return response.getChoices().get(0).getMessage().getContent().trim();
    }*/
}