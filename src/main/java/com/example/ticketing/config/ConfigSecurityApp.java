package com.example.ticketing.config;
import lombok.AllArgsConstructor;
import org.springframework.boot.ssl.DefaultSslBundleRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class ConfigSecurityApp {

    private final DetailServiceImpl detailService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, DefaultSslBundleRegistry sslBundleRegistry) throws Exception {
        httpSecurity.userDetailsService(detailService);
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> {
                    registry
                            .requestMatchers("/api/v1/auth/**",
                                    "/v3/api-docs",
                                    "/v2/api-docs",
                                    "/swagger-resources/**",
                                    "/swagger-ui/**",
                                    "/webjars/**").permitAll()
                            .requestMatchers("/users/admin/**").hasRole("Admin")
                            .requestMatchers("/tickets/**").hasRole("Apprenant")
                            .requestMatchers("/tickets/**").hasRole("Admin")
                            .requestMatchers("/assign/**").hasRole("Admin")
                            .requestMatchers("/knowledgebase/**").hasRole("Admin")
                            .requestMatchers("/knowledgebase/**").hasRole("Coach")
                            .requestMatchers("/knowledgebase/afficher").hasRole("Apprenant")
                            .requestMatchers("/resolutions/**").hasRole("Coach")
                            .requestMatchers("/resolutions/**").hasRole("Admin")
                            .requestMatchers("/resolutions/all").hasRole("Apprenant")
                            .anyRequest().authenticated();
                })
                .httpBasic(withDefaults())
                .formLogin(withDefaults())
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(detailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
