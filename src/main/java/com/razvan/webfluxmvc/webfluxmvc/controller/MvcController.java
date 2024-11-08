package com.razvan.webfluxmvc.webfluxmvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class MvcController {

  @GetMapping
  @PreAuthorize("hasRole('ROLE_USER')")
  public String getAllEmployees(){
    var sec = SecurityContextHolder.getContext().getAuthentication();
    log.info("Curr mvc user is {}", sec.getName());
    return "You Received All Employees List";
  }
}
