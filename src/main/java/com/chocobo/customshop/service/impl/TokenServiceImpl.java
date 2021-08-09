package com.chocobo.customshop.service.impl;

import com.chocobo.customshop.service.TokenService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class TokenServiceImpl implements TokenService {

    private static final String TOKEN_PROPERTIES_NAME = "token.properties";
    private static final String SECRET_KEY_PROPERTY = "secretKey";
    private static final String VALIDITY_TIME_PROPERTY = "validityTime";
    private static final String ID_CLAIM = "id";

    private static Key secretKey;
    private static int validityTime;

    static {
        ClassLoader classLoader = MailServiceImpl.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(TOKEN_PROPERTIES_NAME)) {
            Properties properties = new Properties();
            properties.load(inputStream);
            String key = properties.getProperty(SECRET_KEY_PROPERTY);
            secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
            validityTime = Integer.parseInt(properties.getProperty(VALIDITY_TIME_PROPERTY));
        } catch (IOException e) {
            throw new RuntimeException("Couldn't read token properties file", e);
        }
    }

    @Override
    public String generateToken(long userId) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR, validityTime);
        Date expirationTime = calendar.getTime();

        return Jwts.builder()
                .claim(ID_CLAIM, userId)
                .signWith(secretKey)
                .setExpiration(expirationTime)
                .compact();
    }
}
