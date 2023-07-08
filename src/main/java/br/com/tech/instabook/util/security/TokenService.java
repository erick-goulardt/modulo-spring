package br.com.tech.instabook.util.security;

import br.com.tech.instabook.models.ProfileModel;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.apache.tomcat.util.buf.UDecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;

@Service
public class TokenService {
    @Value(value = "${api.security.token.secret}")
    public String secret;
    public String generateToken(ProfileModel profileModel) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
                String token = JWT.create()
                    .withIssuer("instabookapi")
                    .withSubject(profileModel.getUsername())
                        .withClaim("id",profileModel.getId())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
            return token;

        } catch (JWTCreationException ex) {
            throw new RuntimeException("Erro na geração do Token!", ex);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("instabookapi")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Erro ao validar " + token);
        }
    }
    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
