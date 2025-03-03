package com.chamath.TasteTown.Service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class ChatbotService {

    @Value("${gemini.api.url}")
    private String apiUrl;

    @Value("${gemini.api.key}")
    private String apiKey;

    private final WebClient webClient;
    public ChatbotService(WebClient.Builder webClient){
        this.webClient = webClient.build();
    }

    public String createRecipe(String ingredients, String cuisines, String dietRestrictions) {
        // Create message template
        String template = """
                I want to create a recipe using the following ingredients: {ingredients}.
                The preferred cuisine type is {cuisines}.
                Please consider the following dietary restrictions: {dietRestrictions} when making the recipe.
                
                Using the above details, give me a detailed recipe including title, list of ingredients, 
                and cooking instructions.
                """;

        // Replace placeholders with actual values
        String formattedMessage = template
                .replace("{ingredients}", ingredients)
                .replace("{cuisines}", cuisines)
                .replace("{dietRestrictions}", dietRestrictions);

        // Construct the API request payload
        Map<String, Object> requestBody = Map.of(
                "contents", new Object[]{
                        Map.of("parts", new Object[]{
                                Map.of("text", formattedMessage)
                        })
                }
        );

        // Make the API call
        String response = webClient.post()
                .uri(apiUrl + apiKey)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return response;
    }
}

