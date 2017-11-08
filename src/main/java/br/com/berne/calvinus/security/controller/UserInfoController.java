package br.com.berne.calvinus.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserInfoController {

  @GetMapping("user")
  public Principal getUserInfo(Principal principal) {
    return principal;
  }

}
