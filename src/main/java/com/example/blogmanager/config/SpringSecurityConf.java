package com.example.blogmanager.config;

import com.example.blogmanager.security.*;
import com.example.blogmanager.service.CustomUserDetailService;
import com.example.blogmanager.service.JWTService;
import com.example.blogmanager.service.UserService;
import jakarta.servlet.FilterChain;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConf{
    private final JWTService jwtService;
    private final UserService userService;
    private final JWTAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors( (cors) -> cors
                        .configurationSource((request) -> {
                            return getCorsConfiguration();
                        }))
                .csrf(
                        AbstractHttpConfigurer::disable
                )
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/visitor/**")
                            .permitAll()
                        .anyRequest()
                            .authenticated()
                )
                .formLogin((formLogin) -> formLogin
                        .loginProcessingUrl("/visitor/login")
                        .successHandler(customAuthenticationSuccessHandler(jwtService))
                        .failureHandler(customAuthenticationFailureHandler()))
                .logout( (logout) -> logout
                        .logoutSuccessHandler(customLogoutSuccessHandler())
                        .logoutUrl("/admin/logout")
                        .addLogoutHandler(new CustomLogoutHandler(jwtService))
                )
                .exceptionHandling((exceptions) -> exceptions
                        .authenticationEntryPoint(customAuthenticationEntryPoint())
                        .accessDeniedHandler(customAccessDeniedHandler())
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement((sessionManagement) -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }

    @Bean
    public CustomAuthenticationEntryPoint customAuthenticationEntryPoint(){
        return new CustomAuthenticationEntryPoint();
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public CustomLogoutSuccessHandler customLogoutSuccessHandler(){
        return new CustomLogoutSuccessHandler();
    }

    @Bean
    public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler(JWTService jwtService){
        return new CustomAuthenticationSuccessHandler(jwtService);
    }

    @Bean
    public CustomAuthenticationFailureHandler customAuthenticationFailureHandler(){
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        return new CustomerAuthenticationProvider(userService);
    }

    @Bean
    public CorsConfiguration getCorsConfiguration(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://101.200.222.214:3000");
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setMaxAge(86400L);
        return corsConfiguration;
    }
}
