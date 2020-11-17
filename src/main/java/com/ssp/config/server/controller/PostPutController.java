package com.ssp.config.server.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@RestController
@Log4j2
public class PostPutController {


  @ResponseStatus(value = HttpStatus.OK)
  @GetMapping(value = "/reload")
  public void propertieReload(){
    RestTemplate restTemplate = new RestTemplate();

    HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
    factory.setConnectTimeout(200);
    restTemplate.setRequestFactory(factory);

    HttpHeaders headers = new HttpHeaders();
    HttpEntity<String> entity = new HttpEntity<>(headers);
    List<String> list = new ArrayList<>();
    list.add("localhost:8081");
    for (String url : list) {
      try {
        ResponseEntity<String> response = restTemplate.exchange("http://" + url + "/actuator/refresh", HttpMethod.POST, entity, String.class);
        HttpStatus statusCode = response.getStatusCode();
        if (!HttpStatus.OK.equals(statusCode)) {
          return;
//          restTemplate.exchange(telegramUrlKakaoDaisy + url + " openrtb 응답실패", HttpMethod.GET, entity, String.class);
        }
      } catch (Exception e) {
        log.error("error messgae {}",e.getMessage(),e);
//        restTemplate.exchange(telegramUrlKakaoDaisy + url + " openrtb 타임아웃 실패" + e.getMessage(), HttpMethod.GET, entity, String.class);
      }
    }


  }


}
