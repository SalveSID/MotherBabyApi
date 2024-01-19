package com.example.SecondApi.Model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class BabyDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isBoy;

    private String babyName;

    private LocalDateTime birthDateTime;

    private int babyHeight;

    private int babyWeight;

    @ManyToOne
    @JoinColumn(name = "mother_id")
    private MotherProfileEntity mother;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isBoy() {
        return isBoy;
    }

    public void setBoy(boolean boy) {
        isBoy = boy;
    }

    public String getBabyName() {
        return babyName;
    }

    public void setBabyName(String babyName) {
        this.babyName = babyName;
    }

    public LocalDateTime getBirthDateTime() {
        return birthDateTime;
    }

    public void setBirthDateTime(LocalDateTime birthDateTime) {
        this.birthDateTime = birthDateTime;
    }

    public int getBabyHeight() {
        return babyHeight;
    }

    public void setBabyHeight(int babyHeight) {
        this.babyHeight = babyHeight;
    }

    public int getBabyWeight() {
        return babyWeight;
    }

    public void setBabyWeight(int babyWeight) {
        this.babyWeight = babyWeight;
    }

    public MotherProfileEntity getMother() {
        return mother;
    }

    public void setMother(MotherProfileEntity mother) {
        this.mother = mother;
    }
}
