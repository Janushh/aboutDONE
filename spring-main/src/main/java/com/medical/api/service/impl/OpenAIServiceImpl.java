package com.medical.api.service.impl;

import com.medical.api.dto.ChatGptRequest;
import com.medical.api.dto.ChatGptResponse;
import com.medical.api.service.OpenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenAIServiceImpl implements OpenAIService {

    @Value("${openai.api.url}")
    private String apiURL;

    private final RestTemplate restTemplate;

    public OpenAIServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ChatGptResponse sendRequest(ChatGptRequest request) {
        return restTemplate.postForObject(apiURL, request, ChatGptResponse.class);
        }
    }
