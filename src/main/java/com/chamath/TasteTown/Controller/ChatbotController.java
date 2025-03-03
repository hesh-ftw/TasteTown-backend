package com.chamath.TasteTown.Controller;

import com.chamath.TasteTown.Service.ChatbotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Controller
public class ChatbotController {

    @Autowired
    ChatbotService chatbotService;

    @PostMapping("/chatbot")
    public ResponseEntity<String> createRecipe(@RequestBody Map<String, String> payload) {
        String ingredients = payload.get("ingredients");
        String cuisines = payload.getOrDefault("cuisines", "any");
        String dietRestrictions = payload.getOrDefault("dietRestrictions", "");

        String response = chatbotService.createRecipe(ingredients, cuisines, dietRestrictions);
        return ResponseEntity.ok(response);
    }
}
