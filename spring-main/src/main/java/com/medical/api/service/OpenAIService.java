package com.medical.api.service;

import com.medical.api.dto.ChatGptRequest;
import com.medical.api.dto.ChatGptResponse;

public interface OpenAIService {
    ChatGptResponse sendRequest(ChatGptRequest request);
}