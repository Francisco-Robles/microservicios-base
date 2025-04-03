package com.base.gateway_service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

    /*@Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity httpSecurity){

        httpSecurity.authorizeExchange().anyExchange().authenticated()
                .and()
                .oauth2Login(Customizer.withDefaults());
        httpSecurity.csrf().disable();

        return httpSecurity.build();

    }*/
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/login/oauth2/**", "/oauth2/**").permitAll()
                        .anyExchange().authenticated()
                )
                .oauth2Login(Customizer.withDefaults())
                .csrf(ServerHttpSecurity.CsrfSpec::disable);

        return http.build();
    }

}
