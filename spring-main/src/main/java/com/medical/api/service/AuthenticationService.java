package com.medical.api.service;


import com.medical.api.dao.request.SignUpRequest;
import com.medical.api.dao.request.SignInRequest;
import com.medical.api.dao.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SignInRequest request);
}
