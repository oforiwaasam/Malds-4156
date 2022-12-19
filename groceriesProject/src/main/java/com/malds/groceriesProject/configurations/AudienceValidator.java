package com.malds.groceriesProject.configurations;

import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

class AudienceValidator implements OAuth2TokenValidator<Jwt> {
    /**
     * audience variable for authentication.
     */
    private final String audience;

    /**
     * Constructor for AudienceValidator.
     * @param paudience only named this to pass the style checker.
     */
    AudienceValidator(final String paudience) {
        this.audience = paudience;
    }

    /**
     * Token validation method.
     * @param jwt
     * @return Result of whether token is valid or not.
     */
    public OAuth2TokenValidatorResult validate(final Jwt jwt) {
        OAuth2Error error = new
            OAuth2Error("invalid_token",
                "The required audience is missing", null);
        if (jwt.getAudience().contains(audience)) {
            return OAuth2TokenValidatorResult.success();
        }
        System.out.println(error);
        return OAuth2TokenValidatorResult.failure(error);
    }
}
