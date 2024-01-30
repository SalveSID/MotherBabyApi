package com.example.SecondApi.Controller;

import com.example.SecondApi.Model.BabyDetailsEntity;
import com.example.SecondApi.Model.MotherProfileEntity;
import com.example.SecondApi.Service.EntityService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.security.Key;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/entities")
public class EntityController {

    private static final byte[] SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();

    @Autowired
    private EntityService entityService;

    @PostMapping("/create")
    public ResponseEntity<Object> createEntity(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String bearerToken,
            @RequestBody Object payload) {

        // Validate token
        if (!validateToken(bearerToken)) {
            return new ResponseEntity<>("Invalid or expired token", HttpStatus.UNAUTHORIZED);
        }
        if (payload instanceof Map) {
            // Convert the payload Map to MotherProfileEntity
            MotherProfileEntity motherProfile = convertToMotherProfileEntity((Map<?, ?>) payload);
            MotherProfileEntity savedMotherProfile = entityService.saveMotherProfile(motherProfile);
            return new ResponseEntity<>(savedMotherProfile, HttpStatus.CREATED);
        } else if (payload instanceof BabyDetailsEntity) {
            // Convert the payload to BabyDetailsEntity
            BabyDetailsEntity babyDetails = (BabyDetailsEntity) payload;
            BabyDetailsEntity savedBabyDetails = entityService.saveBabyDetails(babyDetails);
            return new ResponseEntity<>(savedBabyDetails, HttpStatus.CREATED);
        } else {
            // Handle unknown payload type
            return new ResponseEntity<>("Invalid payload type", HttpStatus.BAD_REQUEST);
        }
    }

    private MotherProfileEntity convertToMotherProfileEntity(Map<?, ?> payload) {
        // Conversion Logic
        MotherProfileEntity motherProfile = new MotherProfileEntity();
        motherProfile.setName((String) payload.get("name"));
        motherProfile.setEmail((String) payload.get("email"));
        motherProfile.setPregnant((Boolean) payload.get("isPregnant"));
        motherProfile.setBreastfeeding((Boolean) payload.get("isBreastfeeding"));

        // Convert baby details
        List<Map<?, ?>> babyList = (List<Map<?, ?>>) payload.get("babies");
        List<BabyDetailsEntity> babies = babyList.stream()
                .map(this::convertToBabyDetailsEntity)
                .collect(Collectors.toList());
        motherProfile.setBabies(babies);

        return motherProfile;
    }

    private BabyDetailsEntity convertToBabyDetailsEntity(Map<?, ?> payload) {
        BabyDetailsEntity babyDetails = new BabyDetailsEntity();
        babyDetails.setBoy((Boolean) payload.get("isBoy"));
        babyDetails.setBabyName((String) payload.get("babyName"));
        babyDetails.setBirthDateTime(LocalDateTime.parse((String) payload.get("birthDateTime")));

        // Handle numeric values appropriately (convert to Integer)
        babyDetails.setBabyHeight(((Number) payload.get("babyHeight")).intValue());
        babyDetails.setBabyWeight(((Number) payload.get("babyWeight")).intValue());

        return babyDetails;
    }

    private boolean validateToken(String token) {
        try {
            // Log the received token for debugging
            System.out.println("Received token: " + token);

            // Check if the token starts with "Bearer "
            if (token.startsWith("Bearer ")) {
                // If yes, remove the "Bearer " prefix
                token = token.split(" ")[1].trim();
            }

            // Log the processed token for debugging
            System.out.println("Processed token: " + token);

            // Split the token parts
            String[] tokenParts = token.split("\\.");
            System.out.println("Header: " + tokenParts[0]);
            System.out.println("Payload: " + tokenParts[1]);
            System.out.println("Signature: " + tokenParts[2]);

            // Attempt to parse the token and log the claims
            Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);

            Claims claims = jws.getBody();

            // Log the claims for debugging
            System.out.println("Claims: " + claims);

            // Check if the "iss" claim is present and has the expected value
            String issuer = "Password";
            if (!claims.getIssuer().equals(issuer)) {
                return false;
            }

            // Check if the "pass" claim is present and has the expected value
            String expectedPass = "QWERTY@123";
            if (!claims.containsKey("pass") || !claims.get("pass").equals(expectedPass)) {
                return false;
            }

            return true;
        } catch (JwtException e) {
            // Log the exception for debugging
            System.out.println("JWT Exception: " + e.getMessage());
            return false;
        }
    }


}

