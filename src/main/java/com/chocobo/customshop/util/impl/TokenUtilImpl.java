package com.chocobo.customshop.util.impl;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.util.TokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class TokenUtilImpl implements TokenUtil {

    private static final Logger logger = LogManager.getLogger();
    private static TokenUtil instance;

    private static final String TOKEN_PROPERTIES_NAME = "properties/token.properties";
    private static final String SECRET_KEY_PROPERTY = "secretKey";
    private static final String VALIDITY_TIME_PROPERTY = "validityTime";
    public static final String ID_CLAIM = "id";
    public static final String EMAIL_CLAIM = "email";

    private static final Key secretKey;
    private static final int validityTime;

    static {
        ClassLoader classLoader = MailUtilImpl.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(TOKEN_PROPERTIES_NAME)) {
            Properties properties = new Properties();
            properties.load(inputStream);
            String key = properties.getProperty(SECRET_KEY_PROPERTY);
            secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
            validityTime = Integer.parseInt(properties.getProperty(VALIDITY_TIME_PROPERTY));
        } catch (IOException e) {
            logger.fatal("Couldn't read token properties file", e);
            throw new RuntimeException(e);
        }
    }

    public static TokenUtil getInstance() {
        if (instance == null) {
            instance = new TokenUtilImpl();
        }
        return instance;
    }

    @Override
    public String generateToken(Map<String, Object> claimsMap) {
        Instant expirationInstant = LocalDateTime.now(Clock.systemUTC())
                .plus(validityTime, ChronoUnit.HOURS)
                .toInstant(ZoneOffset.UTC);
        Date expirationTime = Date.from(expirationInstant);

        JwtBuilder builder = Jwts.builder()
                .signWith(secretKey)
                .setExpiration(expirationTime);
        claimsMap.forEach(builder::claim);

        return builder.compact();
    }

    @Override
    public Map<String, Object> parseToken(String token) throws ServiceException {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return new HashMap<>(claims);
        } catch (JwtException | NumberFormatException e) {
            throw new ServiceException("Got invalid token", e);
        }
    }
}
