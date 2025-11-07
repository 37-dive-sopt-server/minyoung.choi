package org.sopt.member.dto.request;

public class MemberRequestDto {
    private String name;
    private String email;
    private String birthDate;
    private String gender;

    public MemberRequestDto() {}

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getGender() {
        return gender;
    }
}