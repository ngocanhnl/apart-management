/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.configs;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.ws.rs.HttpMethod;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

/**
 *
 * @author Ngoc Anh
 */
@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@ComponentScan(basePackages = {
    "com.ngocanh.controllers",
    "com.ngocanh.repositories",
    "com.ngocanh.services"
})
public class SpringSecurityConfig {

    @Autowired
    private UserDetailsService userDetailService;

    @Bean
    public BCryptPasswordEncoder passwowrdEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
            Exception {
         http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(c -> c.disable()).authorizeHttpRequests(requests
                -> requests.requestMatchers("/", "/home").authenticated()
                        .requestMatchers("/api/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/products").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,
                                "/api/products/**").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true").permitAll())
                .logout(logout
                        -> logout.logoutSuccessUrl("/login").permitAll());
        return http.build();
    }

    @Bean
    public HandlerMappingIntrospector
            mvcHandlerMappingIntrospector() {
        return new HandlerMappingIntrospector();
    }

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "darr5at86",
                "api_key", "284429133985293",
                "api_secret", "uBM6EnO3A2Nfmp7lkkOGMNvi9u8",
                "secure", true));
    }
    
    @Bean
    @Order(0)
    public StandardServletMultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of("http://localhost:3000")); 
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setExposedHeaders(List.of("Authorization"));
        config.setAllowCredentials(true); 

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

}
