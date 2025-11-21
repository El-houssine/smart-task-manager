package com.smarttask.smart_task_manager.service.impl;

import com.smarttask.smart_task_manager.enums.Priority;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AIService {
    private final OpenAiService openAiService;

    // Priorisation des tâches
    public Priority suggestPriority(String taskDescription) {
        String prompt = """
            Analyse cette tâche et retourne la priorité (HIGH, MEDIUM, LOW) :
            Tâche: %s
            Réponds uniquement par le mot en MAJUSCULES.
        """.formatted(taskDescription);

        String response = getCompletion(prompt);
        return Priority.valueOf(response.trim());
    }

    // Génération de sous-tâches
    public List<String> generateSubtasks(String taskDescription) {
        String prompt = """
            Décompose cette tâche en 3 à 5 sous-tâches :
            Tâche: %s
            Format de réponse : 
            - [Sous-tâche 1]
            - [Sous-tâche 2]
        """.formatted(taskDescription);

        String response = getCompletion(prompt);
        return parseSubtasks(response);
    }

    private String getCompletion(String prompt) {
        try {
            ChatCompletionRequest request = ChatCompletionRequest.builder()
                    .model("gpt-3.5-turbo")
                    .messages(List.of(new ChatMessage("user", prompt)))
                    .temperature(0.7)
                    .maxTokens(200)
                    .build();

            return openAiService.createChatCompletion(request)
                    .getChoices().get(0).getMessage().getContent();
        } catch (Exception e) {
            log.error("Erreur OpenAI", e);
            return "";
        }
    }

    private List<String> parseSubtasks(String response) {
        return Arrays.stream(response.split("\n"))
                .filter(line -> line.startsWith("- "))
                .map(line -> line.substring(2))
                .toList();
    }
}