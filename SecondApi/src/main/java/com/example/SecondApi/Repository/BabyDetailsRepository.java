package com.example.SecondApi.Repository;

import com.example.SecondApi.Model.BabyDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BabyDetailsRepository extends JpaRepository<BabyDetailsEntity, Long> {
}
