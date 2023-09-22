package org.acme.kafka.security;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logmanager.Logger;
import org.jose4j.jwt.JwtClaims;

import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

@RequestScoped
public class TokenService {

    @Inject
    JsonWebToken jwt;

    public final static Logger LOGGER = Logger.getLogger(TokenService.class.getSimpleName());

    public String generateUserToken(String email, String username) {
        return generateToken(email, username, Roles.USER);
    }

    public String generateServiceToken(String serviceId, String serviceName) {
        return generateToken(serviceId,serviceName,Roles.SERVICE);
    }

    public String generateToken(String subject, String name, String... roles) {
        try {
            JwtClaims jwtClaims = new JwtClaims();
            jwtClaims.setIssuer("DonauTech"); // change to your company
            jwtClaims.setJwtId(UUID.randomUUID().toString());
            jwtClaims.setSubject(subject);
            jwtClaims.setClaim(Claims.upn.name(), subject);
            jwtClaims.setClaim(Claims.preferred_username.name(), name); //add more
            jwtClaims.setClaim(Claims.groups.name(), Arrays.asList(roles));
            jwtClaims.setAudience("using-jwt");
            jwtClaims.setExpirationTimeMinutesInTheFuture(60); // TODO specify how long do you need


            String token = TokenUtils.generateTokenString(jwtClaims);
            LOGGER.info("TOKEN generated: " + token);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public void validateToken() {
        if (jwt == null) {
            throw new RuntimeException("Invalid JWT token");
        }
        // Kiểm tra tính hợp lệ của JWT
        JwtClaims claims = (JwtClaims) jwt.getClaimNames();

        // Kiểm tra xem JWT đã hết hạn chưa
        Instant expiration = Instant.ofEpochSecond(jwt.getExpirationTime());
        Instant now = Instant.now();
        if (expiration != null && now.isAfter(expiration)) {
            throw new RuntimeException("Token has expired");
        }
    }

    public Long getUserIdFromJWT() {
        try {
            if (jwt != null) {
                String userIdClaim = jwt.getClaim(Claims.sub.name());
                if (userIdClaim != null) {
                    return Long.parseLong(userIdClaim);
                }
            }
            return null;
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid user ID in JWT");
        }
    }
}
