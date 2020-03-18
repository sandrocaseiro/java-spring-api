package com.sandrocaseiro.apitemplate.configs;

import com.sandrocaseiro.apitemplate.filters.JWTAuthenticationFilter;
import com.sandrocaseiro.apitemplate.filters.JWTAuthorizationFilter;
import com.sandrocaseiro.apitemplate.filters.ServletErrorFilter;
import com.sandrocaseiro.apitemplate.properties.CorsProperties;
import com.sandrocaseiro.apitemplate.properties.JwtProperties;
import com.sandrocaseiro.apitemplate.security.TokenAuthProvider;
import com.sandrocaseiro.apitemplate.services.JwtAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenAuthProvider tokenAuthProvider;
    private final JwtAuthService jwtAuthService;
    private final JwtProperties jwtProperties;
    private final CorsProperties corsProperties;
    private final ServletErrorFilter errorFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .headers().frameOptions().sameOrigin();

        if (corsProperties.isEnabled())
            http.cors().and().authorizeRequests(r -> r.requestMatchers(CorsUtils::isPreFlightRequest).permitAll());

        http.authorizeRequests(r ->
                r
                    .antMatchers(
                        "/v2/api-docs",
                        "/swagger-resources/**",
                        "/swagger-ui.html**",
                        "/webjars/**",
                        "/_monitor/**",
                        "/error",
                        "/h2-console/**").permitAll()
                    .antMatchers("/v*/token/**").anonymous()
                    .antMatchers(HttpMethod.POST, "/v*/users").permitAll()
                    .anyRequest().authenticated()
            )
            .addFilterBefore(errorFilter, CorsFilter.class)
            .addFilterAfter(getJWTAuthenticationFilter(), CorsFilter.class)
            .addFilterAfter(getJWTAuthorizationFilter(), CorsFilter.class)
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(tokenAuthProvider);
    }

    @Bean
    @ConditionalOnProperty(value="cors.enabled", havingValue = "true", matchIfMissing = false)
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedMethods(Arrays.asList(corsProperties.getAllowedMethods()));
        config.setAllowedHeaders(Arrays.asList(corsProperties.getAllowedHeaders()));
        config.setExposedHeaders(Arrays.asList(corsProperties.getExposedHeaders()));
        config.setAllowedOrigins(Arrays.asList(corsProperties.getAllowedOrigins()));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    private JWTAuthenticationFilter getJWTAuthenticationFilter() throws Exception {
        final JWTAuthenticationFilter filter = new JWTAuthenticationFilter(authenticationManager(), jwtAuthService, jwtProperties);
        filter.setFilterProcessesUrl("/v1/token");
        return filter;
    }

    private JWTAuthorizationFilter getJWTAuthorizationFilter() throws Exception {
        return new JWTAuthorizationFilter(authenticationManager(), jwtAuthService, jwtProperties);
    }
}
