package com.wizeline.maven.learningjavamaven.service;

import com.wizeline.maven.learningjavamaven.config.RestTemplateConfiguration;
import com.wizeline.maven.learningjavamaven.model.UserGorest;
import com.wizeline.maven.learningjavamaven.model.UsersResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;


public interface GorestService {

    public List<UserGorest> getUsers();
    public List<UserGorest> getUsersV2();
    public UserGorest StringtoJsongetUsers();
}