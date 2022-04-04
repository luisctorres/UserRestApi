package com.careerdevs.UserRestApi.controllers;

import com.careerdevs.UserRestApi.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.env.Environment;


import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/user")
    public class UserController {

    @Autowired
    Environment env;

    private RestTemplate restTemplate;

    @GetMapping("/token")
        public String getToken() {
            return this.env.getProperty("GO_REST_TOKEN");
        }

    //Get Request - Returns 1 user
    @GetMapping("/{id}")
    public Object getOneUser(@PathVariable("id") String userId,
                             RestTemplate restTemplate) {
        try {
        String url = "https://gorest.co.in/public/v2/users/" + userId;
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(getToken());

        HttpEntity<Object> request = new HttpEntity<>(headers);

        return restTemplate.exchange(
                                    url,
                                    HttpMethod.GET,
                                    request,
                                    Object.class
                                    );
            }
        catch (Exception exception) {
                return "404: No user exists with the ID " + userId;
            }
        }

    //Post mapping to add user
    @PostMapping("/")
    public Object postUser(
            RestTemplate restTemplate,
            @RequestBody UserModel newUser) {

        String url = "https://gorest.co.in/public/v2/users?access-token=640b92322772b544c91b13e11b180ac3e24157879f6da64af338e07ba655fd17";
        HttpHeaders headers = new HttpHeaders();
        //headers.setBearerAuth(getToken());
//        UserModel newUser = new UserModel(name, email, gender, status);

        HttpEntity<UserModel> request = new HttpEntity<>(newUser);
        return restTemplate.postForEntity(url, request, UserModel.class);

    }

    //put request for user
    @PutMapping("/")
    public ResponseEntity putUser(
            RestTemplate restTemplate,
            @RequestBody UserModel updateData
    ) {
        try {

            String url = "https://gorest.co.in/public/v2/users/" + updateData.getId();
            String token = env.getProperty("GO_REST_TOKEN");
            url += "?access-token=" + token;

            HttpEntity<UserModel> request = new HttpEntity<>(updateData);

            ResponseEntity<UserModel> response = restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    request,
                    UserModel.class
            );

            return new ResponseEntity(response.getBody(), HttpStatus.OK);


        } catch (HttpClientErrorException.UnprocessableEntity e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);


        } catch (Exception e) {
            System.out.println(e.getClass() + "\n" + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }


}



