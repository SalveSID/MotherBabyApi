package com.example.SecondApi.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class MotherProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private boolean isPregnant;

    private boolean isBreastfeeding;

    @OneToMany(mappedBy = "mother", cascade = CascadeType.ALL)
    private List<BabyDetailsEntity> babies;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isPregnant() {
        return isPregnant;
    }

    public void setPregnant(boolean pregnant) {
        isPregnant = pregnant;
    }

    public boolean isBreastfeeding() {
        return isBreastfeeding;
    }

    public void setBreastfeeding(boolean breastfeeding) {
        isBreastfeeding = breastfeeding;
    }

    public List<BabyDetailsEntity> getBabies() {
        return babies;
    }

    public void setBabies(List<BabyDetailsEntity> babies) {
        this.babies = babies;
    }
}
