package io.github.rxcats.springcloudzuul;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import lombok.extern.slf4j.Slf4j;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Slf4j
public class JwtTokenTest {

    @Value("${jwt.secret-code}")
    private String secretCode;

    @Test
    public void create() {
        String token = Jwts.builder()
            .setExpiration(Date.from(Instant.now().plusSeconds(60000)))
            .setId("user#1")
            .claim("now", System.currentTimeMillis())
            .signWith(SignatureAlgorithm.HS256, secretCode.getBytes(StandardCharsets.UTF_8))
            .compact();
        log.info("token:{}", token);
    }
}
