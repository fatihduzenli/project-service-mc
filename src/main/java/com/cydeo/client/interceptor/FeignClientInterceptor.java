package com.cydeo.client.interceptor;

import com.cydeo.service.KeycloakService;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class FeignClientInterceptor implements RequestInterceptor {


    private final KeycloakService keycloakService;

    public FeignClientInterceptor(KeycloakService keycloakService) {
        this.keycloakService = keycloakService;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Authorization",keycloakService.getAccessToken());
    }
}

/*
Purpose: The FeignClientInterceptor class is designed to intercept HTTP requests made by Feign clients
in the application and modify them before they are sent out. Specifically, it adds an
Authorization header to each request.
 */