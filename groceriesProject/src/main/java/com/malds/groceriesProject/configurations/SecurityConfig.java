package com.malds.groceriesProject.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configures our application with Spring Security to restrict
 * access to our API endpoints.
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    /**
     * Authentication audience from application.properties.
     */
   @Value("${auth0.audience}")
   private String audience;

   /**
    * Authentticiation URI from application.properties.
    */
   @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
   private String issuer;

    /**
     * This is where we configure the security required for our endpoints and
     * setup our app to serve as
     * an OAuth2 Resource Server, using JWT validation.
     * @param http
     * @return Builder to authorize requests
     */
    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http)
        throws Exception {
        /*
        This is where we configure the security required for our endpoints and
        setup our app to serve as
        an OAuth2 Resource Server, using JWT validation.
        */
        http.authorizeRequests()
                .mvcMatchers("/shopping_list/**").authenticated()
                .mvcMatchers(HttpMethod.GET, "/products/**").permitAll()
                .mvcMatchers("/products/**").authenticated()
                .mvcMatchers("/clients/**").authenticated()
                .mvcMatchers("/vendors/**").authenticated()
                //.mvcMatchers("/api/private").authenticated()
                //.mvcMatchers("/api/private-scoped")
                //.hasAuthority("SCOPE_read:messages")
                .and().cors()
                .and()
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
        return http.build();
    }
    /**
     * By default, Spring Security does not validate the "aud" claim of
     * the token, to ensure that this token is
     * indeed intended for our app. Adding our own validator is easy to do:
     * @return jwtDecoder
     */
    @Bean
    JwtDecoder jwtDecoder() {
        /*
        By default, Spring Security does not validate the "aud"
        claim of the token, to ensure that this token is
        indeed intended for our app. Adding our own validator is easy to do:
        */

        NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder)
                JwtDecoders.fromOidcIssuerLocation(issuer);

        OAuth2TokenValidator<Jwt> audienceValidator =
            new AudienceValidator(audience);
        OAuth2TokenValidator<Jwt> withIssuer =
            JwtValidators.createDefaultWithIssuer(issuer);
        OAuth2TokenValidator<Jwt> withAudience = new
            DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);

        jwtDecoder.setJwtValidator(withAudience);

        return jwtDecoder;
    }
}
