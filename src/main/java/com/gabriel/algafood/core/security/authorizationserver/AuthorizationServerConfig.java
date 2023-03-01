package com.gabriel.algafood.core.security.authorizationserver;

import com.gabriel.algafood.domain.model.Usuario;
import com.gabriel.algafood.domain.repository.UsuarioRepository;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.server.authorization.*;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.security.KeyStore;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class AuthorizationServerConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        OAuth2AuthorizationServerConfigurer<HttpSecurity> authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
        authorizationServerConfigurer.authorizationEndpoint(customizer -> customizer.consentPage("/oauth2/consent"));
        RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();

        http.requestMatcher(endpointsMatcher).authorizeRequests((authorizeRequests) -> {
            ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)authorizeRequests.anyRequest()).authenticated();
            }).csrf((csrf) -> {
            csrf.ignoringRequestMatchers(new RequestMatcher[]{endpointsMatcher});
        }).apply(authorizationServerConfigurer);

        return http.formLogin(customizer -> customizer.loginPage("/login")).build();
    }

    @Bean
    public ProviderSettings providerSettings(AlgaFoodSecurityProperties properties) {
        return ProviderSettings.builder()
                .issuer(properties.getProviderUrl())
                .build();
    }

    @Bean
    public RegisteredClientRepository clientRepository(JdbcOperations operations) {
        return new JdbcRegisteredClientRepository(operations);
    }

    @Bean
    public OAuth2AuthorizationService oAuth2AuthorizationService(
            JdbcOperations jdbcOperations, RegisteredClientRepository registeredClientRepository) {

        return new JdbcOAuth2AuthorizationService(jdbcOperations, registeredClientRepository);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource(JwtKeyStoreProperties properties) throws Exception {
        char[] keyStorePass = properties.getPassword().toCharArray();
        String keyPairAlias = properties.getKeyPairAlias();
        Resource jksLocation = properties.getJksLocation();

        InputStream inputStream = jksLocation.getInputStream();
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(inputStream, keyStorePass);

        RSAKey rsaKey = RSAKey.load(keyStore, keyPairAlias, keyStorePass);
        return new ImmutableJWKSet<>(new JWKSet(rsaKey));
    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer(UsuarioRepository usuarioRepository) {
        return context -> {
            Authentication authentication = context.getPrincipal();
            if (authentication.getPrincipal() instanceof User) {
                User user = (User) authentication.getPrincipal();
                Usuario usuario = usuarioRepository.findByEmail(user.getUsername()).orElseThrow();

                Set<String> authorities = new HashSet<>();
                user.getAuthorities().forEach(authority -> authorities.add(authority.getAuthority()));

                context.getClaims().claim("usuario_id", usuario.getId().toString());
                context.getClaims().claim("authorities", authorities);
            }
        };
    }

    @Bean
    public OAuth2AuthorizationConsentService consentService(JdbcOperations operations, RegisteredClientRepository repository) {
        return new JdbcOAuth2AuthorizationConsentService(operations, repository);
    }

    @Bean
    public OAuth2AuthorizationQueryService oAuth2AuthorizationQueryService(JdbcOperations operations, RegisteredClientRepository repository) {
        return new JdbcOAuth2AuthorizationQueryService(operations, repository);
    }




}
