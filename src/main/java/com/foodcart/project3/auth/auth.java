package com.foodcart.project3.auth;

import com.foodcart.project3.filter.cookiefilter;
import com.foodcart.project3.filter.headerfilter;

import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class auth {
    @Autowired
    private headerfilter headerfilter;
    @Autowired
    private cookiefilter cookiefilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
     return    http
             .cors(cors->{})
                .csrf(csrf->csrf.disable())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
             .authorizeHttpRequests(auth->auth.requestMatchers("/actions/login","/actions/verify","/iterms/getiterms","/iterms/additerm","/itermsimage/**","/actions/getlogged","/actions/logout","/actions/iterms/{catagaries}").permitAll().anyRequest().authenticated())
             .addFilterBefore( cookiefilter, UsernamePasswordAuthenticationFilter.class)
             .addFilterAfter(headerfilter, cookiefilter.getClass())
             .build();
    }
}


//  .authorizeHttpRequests(auth->auth.requestMatchers("/actions/login","/actions/verify","/iterms/getiterms","/iterms/additerm","/itermsimage/**","/actions/getlogged","/actions/logout").permitAll().anyRequest().authenticated())