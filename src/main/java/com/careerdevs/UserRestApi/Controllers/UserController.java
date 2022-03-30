package com.careerdevs.UserRestApi.Controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.env.Environment;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/user")
    public class UserController {


    //TODO Fix this error with the Environment Variable..

    @Autowired
    Environment env;

        @GetMapping("/token")
        public String getToken() {
            return this.env.getProperty("GO_REST_TOKEN");
        }


        @GetMapping("/{id}")
        public Object getOneUser(@PathVariable("id") String userId,
                             RestTemplate restTemplate) {

        try {
            String url = "https://gorest.co.in/public/v2/users" + userId;
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(env.getProperty("GO_REST_TOKEN"));

            HttpEntity<Object> request = new HttpEntity<>(headers);

            return restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    Object.class
            );
        } catch (Exception exception) {
            return "404: No user exists with the ID " + userId;
        }
    }
}



