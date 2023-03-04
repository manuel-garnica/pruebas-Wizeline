package com.wizeline.maven.learningjavamaven.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wizeline.maven.learningjavamaven.model.UserGorest;
import com.wizeline.maven.learningjavamaven.model.UsersResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
@Service
public class GorestServiceImpl  implements  GorestService{
    @Autowired
    private RestTemplate restTemplate;
    private final String API_BASE_URL = "https://gorest.co.in/public/v1";
    @Value("${token.gorest}")
    private String API_TOKEN;
    public List<UserGorest> getUsers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(API_TOKEN);
        headers.setContentType(MediaType.valueOf(MediaType.TEXT_PLAIN_VALUE));
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<UsersResponse> response = restTemplate.exchange(API_BASE_URL + "/users", HttpMethod.GET, entity, UsersResponse.class);
        return response.getBody().getData();
    }
    public List<UserGorest> getUsersV2() {
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter(new ObjectMapper()));
        UsersResponse response = restTemplate.getForObject(API_BASE_URL + "/users", UsersResponse.class);
        return response.getData();
    }

    @Override
    public UserGorest StringtoJsongetUsers() {
        ObjectMapper objectMapper = new ObjectMapper();
        UserGorest person = new UserGorest();
                String json = "{\"id\":515721,\"name\":\"Dipali Mehrotra\",\"email\":\"dipali_mehrotra@bosco.name\",\"gender\":\"female\",\"status\":\"inactive\"}";
        try {
             person = objectMapper.readValue(json, UserGorest.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return person;
    }
}

