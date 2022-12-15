package com.gabriel.algafood.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class ResourceServerConfig {

    private static final String[] AUTH_WHITELIST = {
// -- Swagger UI v2
            "/v2/api-docs", "/swagger-resources", "/swagger-resources/**", "/configuration/ui",
            "/configuration/security", "/swagger-ui.html", "/webjars/**",
// -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**", "/swagger-ui/**", "/actuator/**",
// -- API
            "/login", "/logout", "/oauth2/logout" };

    @Bean
    public SecurityFilterChain resourceServerFilterChains(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                    .antMatchers(AUTH_WHITELIST).permitAll()
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and().logout()
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies()
                .and()
                .csrf().disable()
                .cors().and()
                .oauth2ResourceServer().jwt()
                    .jwtAuthenticationConverter(jwtAuthenticationConverter());

        http.formLogin(customizer -> customizer.loginPage("/login"));


        return http.build();
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            List<String> authorities = jwt.getClaimAsStringList("authorities");
            if (authorities == null) {
                return Collections.emptyList();
            }

            JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
            Collection<GrantedAuthority> grantedAuthorities = authoritiesConverter.convert(jwt);

            grantedAuthorities.addAll(authorities.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList()));

            return grantedAuthorities;

        });

        return converter;
    }

}
