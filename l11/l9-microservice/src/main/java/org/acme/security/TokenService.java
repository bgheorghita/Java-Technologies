package org.acme.security;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.acme.models.Role;
import org.acme.models.Token;
import org.acme.models.User;
import org.acme.repositories.UserRepository;
import org.eclipse.microprofile.jwt.Claims;
import org.jose4j.jwt.JwtClaims;

import java.util.Arrays;
import java.util.UUID;
import java.util.logging.Logger;

@RequestScoped
public class TokenService {
    public final static int TOKEN_AVAILABILITY_MIN = 60;

    @Inject
    UserRepository userRepository;

    public final static Logger LOGGER = Logger.getLogger(TokenService.class.getSimpleName());

    public Token generateUserToken(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found."));
        String[] roles = user.getRoles().stream().map(Role::getName).toArray(String[]::new);
        return generateToken(username, roles);
    }

    public Token generateToken(String subject, String... roles) {
        try {
            JwtClaims jwtClaims = new JwtClaims();
            jwtClaims.setIssuer("http://localhost:8090");
            jwtClaims.setJwtId(UUID.randomUUID().toString());
            jwtClaims.setSubject(subject);
            jwtClaims.setClaim(Claims.groups.name(), Arrays.asList(roles));
            jwtClaims.setExpirationTimeMinutesInTheFuture(TOKEN_AVAILABILITY_MIN);

            String token = TokenUtils.generateTokenString(jwtClaims);
            LOGGER.info("TOKEN generated: " + token);
            return new Token(token);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}