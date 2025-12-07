package com.online.medicine.application.order.service.security;

import com.online.medicine.application.config.KeycloakRoleConverter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableMethodSecurity
public class OrderSecurityConfig  {


        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

            http
                    .csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers(new AntPathRequestMatcher("/orders", "POST"))
                            .hasRole("USER")
                            .requestMatchers(new AntPathRequestMatcher("/orders/**", "GET"))
                            .hasAnyRole("USER", "ADMIN")
                            .anyRequest().authenticated()
                    )
                    .oauth2ResourceServer(oauth -> oauth
                            .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                    );

            return http.build();
        }

        @Bean
        public Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
            JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
            converter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
            return converter;
        }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder
                .withJwkSetUri("http://localhost:8080/realms/online-medicine/protocol/openid-connect/certs")
                .build();
    }

}