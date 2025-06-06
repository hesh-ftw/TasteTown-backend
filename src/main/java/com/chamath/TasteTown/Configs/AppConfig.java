package com.chamath.TasteTown.Configs;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class AppConfig {

    @Bean
    //defines all the request need to be authenticated
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        //disable server side state management because we use jwt stateless authentication
        http.sessionManagement(management-> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(Authorize-> Authorize
                        .requestMatchers("/api/admin/**").hasAnyRole("RESTAURANT_OWNER", "ADMIN")
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll())

                .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class) //validate incoming jwt token
                .csrf(csrf-> csrf.disable())
                .cors(cors-> cors.configurationSource(corsConfigurationSource()));

      return http.build();
    }


    private CorsConfigurationSource corsConfigurationSource(){
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                CorsConfiguration corsConfig = new CorsConfiguration();
                // Allow specific origins
                corsConfig.setAllowedOrigins(Arrays.asList("http://localhost:3000"));

                // Allow specific HTTP methods
                corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                // Allow specific headers
                corsConfig.setAllowedHeaders(Arrays.asList("*"));
                // Allow credentials (cookies, authorization headers)
                corsConfig.setAllowCredentials(true);
                corsConfig.setMaxAge(3600L);

            return corsConfig;
            }
        };
    }

    //password encryption
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
