package com.razvan.webfluxmvc.webfluxmvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/webflux/v1")
@Slf4j
public class WebfluxController {

  @GetMapping
  @PreAuthorize("hasRole('ROLE_USER')")
  public Mono<String> getAllEmployees(){
    var sec = SecurityContextHolder.getContext().getAuthentication();
    return Mono.just("You Received All Employees List Reactive")
        .doOnSuccess(res -> log.info("Current reactive user is {}", sec.getName()));
  }

}
