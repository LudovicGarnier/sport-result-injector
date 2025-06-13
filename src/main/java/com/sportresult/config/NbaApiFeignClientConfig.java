package com.sportresult.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NbaApiFeignClientConfig {

    @Value("${x-rapidapi-host}")
    private String xRapidapiHost;

    @Value("${x-rapidapi-key}")
    private String xRapidapiKey;


    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                template.header("x-rapidapi-host", xRapidapiHost);
                template.header("x-rapidapi-key", xRapidapiKey);
            }
        };
    }
}
