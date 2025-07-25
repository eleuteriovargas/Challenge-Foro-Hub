package com.vargas.forohub.config;

import com.vargas.forohub.segurity.FiltroAutenticacionJwt;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ConfiguracionSeguridad {

    private final FiltroAutenticacionJwt filtroJwt;
    private final AuthenticationProvider proveedorAutenticacion;

        @Bean
        public SecurityFilterChain cadenaFiltrosSeguridad(HttpSecurity http) throws Exception {
            return http.csrf(csrf -> csrf.disable())
                    .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests(req ->{
                        req.requestMatchers(HttpMethod.POST, "/autenticacion/**").permitAll()
                               .requestMatchers("v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll();
                        req.anyRequest().authenticated();
                    })
                    .addFilterBefore(filtroJwt, UsernamePasswordAuthenticationFilter.class)
                    .build();
        }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(proveedorAutenticacion)
                .build();
    }

    }
