package com.razvan.webfluxmvc.webfluxmvc;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.CsrfSpec;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@EnableReactiveMethodSecurity(useAuthorizationManager = false)
@ConditionalOnWebApplication(type = Type.REACTIVE)
public class WebFluxSecurityConfig {

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    http
        .csrf(CsrfSpec::disable)
        .authorizeExchange(exchanges -> exchanges
            .pathMatchers("/api/webflux2/**").authenticated())
        .httpBasic(Customizer.withDefaults());

    return http.build();
  }


  @Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }

  @Bean
  public MapReactiveUserDetailsService reactiveUserDetailsService() {
    // Define users with roles
    PasswordEncoder encoder = passwordEncoder();
    UserDetails user = User.withUsername("user")
        .password(encoder.encode("password"))
        .roles("USER")
        .build();

    UserDetails admin = User.withUsername("admin")
        .password(encoder.encode("password"))
        .roles("USER")
        .build();

    return new MapReactiveUserDetailsService(user, admin);
  }


//  @Bean
//  public ReactiveAuthenticationManager reactiveAuthenticationManager() {
//    // A simple in-memory authentication manager for WebFlux
//    return new CustomReactiveAuthenticationManager(userDetailsService());
//  }
//}
//
//// Custom ReactiveAuthenticationManager
//class CustomReactiveAuthenticationManager implements ReactiveAuthenticationManager {
//  private final MapReactiveUserDetailsService userDetailsService;
//
//  public CustomReactiveAuthenticationManager(MapReactiveUserDetailsService userDetailsService) {
//    this.userDetailsService = userDetailsService;
//  }
//
//  @Override
//  public Mono<Authentication> authenticate(Authentication authentication) {
//    return userDetailsService.findByUsername(authentication.getName())
//        .filter(user -> user.getPassword().equals(authentication.getCredentials()))
//        .map(user -> new UsernamePasswordAuthenticationToken(user, authentication.getCredentials(), user.getAuthorities()));
//  }
}