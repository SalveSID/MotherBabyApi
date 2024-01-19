package com.example.SecondApi.Controller;

import com.example.SecondApi.Model.BabyDetailsEntity;
import com.example.SecondApi.Model.MotherProfileEntity;
import com.example.SecondApi.Service.EntityService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/entities")
public class EntityController {

    private static final String SECRET_KEY = "ZXCvbnm"; // My secret key

    @Autowired
    private EntityService entityService;

    @PostMapping("/create")
    public ResponseEntity<Object> createEntity(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String bearerToken,
            @RequestBody Object payload) {

        // Validate token
        /*if (!validateToken(bearerToken)) {
            return new ResponseEntity<>("Invalid or expired token", HttpStatus.UNAUTHORIZED);
        }*/
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


   /*private boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token.replace("Bearer ", "")).getBody();

            return true;
        } catch (ExpiredJwtException | MalformedJwtException e) {
            return false;
        }
    }*/
}


