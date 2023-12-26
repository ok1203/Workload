package com.example;

import com.example.service.JwtService;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    private final UserDetails userDetails = new User("testUser", "password", Collections.emptyList());

    @Test
    public void testGenerateTokenWithExtraClaims() {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("key1", "value1");
        String token = jwtService.generateToken(extraClaims, userDetails);
        Assertions.assertNotNull(token);
    }

    @Test
    public void testGenerateTokenWithoutExtraClaims() {
        String token = jwtService.generateToken(userDetails);
        Assertions.assertNotNull(token);
    }

    @Test
    public void testIsTokenValidWithValidToken() {
        String token = jwtService.generateToken(userDetails);
        boolean isValid = jwtService.isTokenValid(token, userDetails);
        Assertions.assertTrue(isValid);
    }

    @Test
    public void testExtractUsernameWithValidToken() {
        String token = jwtService.generateToken(userDetails);
        String extractedUsername = jwtService.extractUsername(token);
        Assertions.assertEquals(userDetails.getUsername(), extractedUsername);
    }

    @Test
    public void testExtractUsernameWithInvalidToken() {
        String invalidToken = "invalid-token";
        Assertions.assertThrows(JwtException.class, () -> jwtService.extractUsername(invalidToken));
    }
}
