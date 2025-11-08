package org.sopt.member.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;

@Getter
@Builder
@AllArgsConstructor
public class Member {

    private Long id;
    private String name;
    private String email;
    // TO DO 추후 LocalData로 수정예정
    private Timestamp birthDate;
    private Gender gender;

    public int getAge() {
        LocalDate birthLocalDate = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate nowDate = LocalDate.now();
        return nowDate.getYear() - birthLocalDate.getYear() -
                (nowDate.getDayOfYear() < birthLocalDate.getDayOfYear() ? 1 : 0);
    }
}