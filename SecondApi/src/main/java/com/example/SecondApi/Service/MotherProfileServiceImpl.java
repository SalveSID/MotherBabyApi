package com.example.SecondApi.Service;

import com.example.SecondApi.Model.MotherProfileEntity;
import com.example.SecondApi.Repository.MotherProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MotherProfileServiceImpl implements MotherProfileService {

    @Autowired
    private MotherProfileRepository motherProfileRepository;

    @Override
    public MotherProfileEntity saveMotherProfile(MotherProfileEntity motherProfile) {
        return motherProfileRepository.save(motherProfile);
    }
}
