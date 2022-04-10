package com.bluebudy.SCQ.controllers;



import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


@Controller
public class GreetingController {


  @MessageMapping("/dispatcher")
  @SendTo("/reducer/return")
  public String greeting(String message) throws Exception {

    return message;
  }

}