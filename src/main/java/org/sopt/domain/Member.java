package org.sopt.domain;

import java.sql.Timestamp;

public class Member {

    private Long id;
    private String name;
    private String email;
    private Timestamp birthDate;
    private Gender gender;

    public Member(Long id, String name, String email, Timestamp birthDate, Gender gender) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Timestamp getBirthDate() {
        return birthDate;
    }

    public Gender getGender() {
        return gender;
    }
}