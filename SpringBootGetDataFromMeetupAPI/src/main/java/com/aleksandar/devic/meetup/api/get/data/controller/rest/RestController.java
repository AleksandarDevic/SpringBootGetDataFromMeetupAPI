/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aleksandar.devic.meetup.api.get.data.controller.rest;

import com.aleksandar.devic.meetup.api.get.data.util.AccessToken;
import com.aleksandar.devic.meetup.api.get.data.util.Singleton;
import com.aleksandar.devic.meetup.api.get.data.util.Utility;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Devic Laptop PC
 */
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/restController")
public class RestController {

    @RequestMapping(path = "/authorizationCode", method = RequestMethod.GET)
    public void getAuthorizationCode(@RequestParam(name = "code") String codeFromURL) {
        //FROM tj.kod koji je pozvao ovaj api iz browsera samo tako moze iz postaman-a ne moze ni iz aplikacije
        //https://secure.meetup.com/oauth2/authorize?client_id=3kbhn0p8dt9g6dcc1cre3euu1s&response_type=code&redirect_uri=http://localhost:8080/restController/authorizationCode
        //ovde je redirect_uri ima dodatak u odnosu na consumer_uri zbog putanje do REST-a
        System.out.println("Usao u REST : RequestMethod.GET");

        Singleton singleton = Singleton.getInstance();
        singleton.setAuthorizationCode(codeFromURL);
        System.out.println("Singleton  authorizationCode 123: " + singleton.getAuthorizationCode());

        String urlPostForAccessToken = "https://secure.meetup.com/oauth2/access?client_id=" + Utility.KEY + "&client_secret=" + Utility.SECRET + "&grant_type=authorization_code&redirect_uri=http://localhost:8080/restController/authorizationCode&code=" + Singleton.getInstance().getAuthorizationCode();
        RestTemplate restTemplate = new RestTemplate();
        AccessToken responseAccessToken = restTemplate.postForObject(urlPostForAccessToken, null, AccessToken.class);
        Singleton.getInstance().setAccessToken(responseAccessToken.getAccess_token());
        System.out.println("responseAccessToken 456: " + singleton.getAccessToken());

    }

}
