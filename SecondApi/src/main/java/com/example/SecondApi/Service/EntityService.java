package com.example.SecondApi.Service;

import com.example.SecondApi.Model.BabyDetailsEntity;
import com.example.SecondApi.Model.MotherProfileEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EntityService {

    @Autowired
    private MotherProfileService motherProfileService;

    @Autowired
    private BabyDetailsService babyDetailsService;

    @Transactional
    public MotherProfileEntity saveMotherProfile(MotherProfileEntity motherProfile) {
        // Perform any additional processing if needed
        return motherProfileService.saveMotherProfile(motherProfile);
    }

    @Transactional
    public BabyDetailsEntity saveBabyDetails(BabyDetailsEntity babyDetails) {
        // Perform any additional processing if needed
        return babyDetailsService.saveBabyDetails(babyDetails);
    }
}

