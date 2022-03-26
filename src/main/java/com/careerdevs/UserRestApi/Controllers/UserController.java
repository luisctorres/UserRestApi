package com.careerdevs.UserRestApi.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/user")
public class UserController {


@GetMapping("/{id}")
    public Object getOneUser(@PathVariable("id") String userId,
                             RestTemplate restTemplate){

    String url = "https://gorest.co.in/public/v2/users";
    return restTemplate.getForObject(url, Object.class);
    }


}
