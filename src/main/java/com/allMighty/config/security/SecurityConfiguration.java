package com.allMighty.config.security;

import static com.allMighty.config.security.person.role.Role.ADMIN;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import com.allMighty.client.UrlProperty;
import com.allMighty.client.UrlProperty.Analysis;
import com.allMighty.client.UrlProperty.Article;
import com.allMighty.client.UrlProperty.Event;
import com.allMighty.client.UrlProperty.MedicalService;
import com.allMighty.config.jwt.JwtAuthenticationFilter;
import com.allMighty.config.security.person.role.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

  private static final String[] WHITE_LIST_URL = {
    UrlProperty.Auth.PATH + "/**",
    UrlProperty.Image.PATH + "/**",
    "/v2/api-docs",
    "/v3/api-docs",
    "/v3/api-docs/**",
    "/swagger-resources",
    "/swagger-resources/**",
    "/configuration/ui",
    "/configuration/security",
    "/swagger-ui/**",
    "/webjars/**",
    "/swagger-ui.html",
    "/rest-api/**"
  };

  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.cors(Customizer.withDefaults())
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            req ->
                req.requestMatchers(WHITE_LIST_URL)
                    .permitAll()
                    // articles
                    .requestMatchers(HttpMethod.GET, Article.PATH, Article.PATH + "/**")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, Article.PATH)
                    .hasRole(Role.ADMIN.name())
                    .requestMatchers(HttpMethod.PUT, Article.PATH + "/**")
                    .hasRole(ADMIN.name())

                    // event
                    .requestMatchers(HttpMethod.GET, Event.PATH, Event.PATH + "/**")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, Event.PATH)
                    .hasRole(Role.ADMIN.name())
                    .requestMatchers(HttpMethod.PUT, Event.PATH + "/**")
                    .hasRole(ADMIN.name())

                    // analysis
                    .requestMatchers(HttpMethod.GET, Analysis.PATH, Analysis.PATH + "/**")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, Analysis.PATH)
                    .hasRole(Role.ADMIN.name())
                    .requestMatchers(HttpMethod.PUT, Analysis.PATH + "/**")
                    .hasRole(ADMIN.name())

                    // services
                    .requestMatchers(
                        HttpMethod.GET, MedicalService.PATH, MedicalService.PATH + "/**")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, MedicalService.PATH)
                    .hasRole(Role.ADMIN.name())
                    .requestMatchers(HttpMethod.PUT, MedicalService.PATH + "/**")
                    .hasRole(ADMIN.name())

                    /* .requestMatchers(HttpMethod.POST, UrlProperty.Email.PATH + "/**")
                    .hasRole(Role.ADMIN.name())*/
                    /*
                    .requestMatchers(HttpMethod.POST, UrlProperty.Email.PATH + "/**")
                    .hasRole(Role.ADMIN.name())*/
                    .anyRequest()
                    .authenticated())
        .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        .logout(
            logout ->
                logout.logoutSuccessHandler(
                    (request, response, authentication) -> SecurityContextHolder.clearContext()));

    return http.build();
  }
}
