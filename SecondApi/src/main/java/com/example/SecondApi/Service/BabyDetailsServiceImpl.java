package com.example.SecondApi.Service;

import com.example.SecondApi.Model.BabyDetailsEntity;
import com.example.SecondApi.Repository.BabyDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BabyDetailsServiceImpl implements BabyDetailsService {

    @Autowired
    private BabyDetailsRepository babyDetailsRepository;

    @Override
    public BabyDetailsEntity saveBabyDetails(BabyDetailsEntity babyDetails) {
        // Perform any additional processing if needed before saving
        return babyDetailsRepository.save(babyDetails);
    }
}

