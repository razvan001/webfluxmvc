package com.razvan.webfluxmvc.webfluxmvc.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@ConditionalOnWebApplication(type = Type.SERVLET)
public class MvcSecurityConfig  {


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

    http.csrf().disable()
        .authorizeHttpRequests((authorize)->{
          authorize.anyRequest().authenticated();
        }).httpBasic(Customizer.withDefaults());
    return http.build();
  }


  @Bean
  public UserDetailsService inMemoryUserDetailsManager() {
    UserDetails user = User.withUsername("mvcuser")
        .password(passwordEncoder().encode("password"))
        .roles("USER")
        .build();

    UserDetails admin = User.withUsername("admin")
        .password(passwordEncoder().encode("adminpass"))
        .roles("ADMIN")
        .build();

    return new InMemoryUserDetailsManager(user, admin);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
