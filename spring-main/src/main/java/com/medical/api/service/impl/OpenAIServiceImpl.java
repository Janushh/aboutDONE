package com.medical.api.service.impl;

import com.medical.api.dto.ChatGptRequest;
import com.medical.api.dto.ChatGptResponse;
import com.medical.api.exception.EmptyResponseException;
import com.medical.api.service.OpenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OpenAIServiceImpl implements OpenAIService {

    @Value("${openai.api.url}")
    private String apiURL;

    private final RestTemplate restTemplate;

    @Override
    public ChatGptResponse sendRequest(ChatGptRequest request) {
        ChatGptResponse response = restTemplate.postForObject(apiURL, request, ChatGptResponse.class);
        if (response == null || response.getChoices().isEmpty()) {
            throw new EmptyResponseException();
        }
        return response;
    }
}