package org.sopt.member.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class Member {

    private Long id;
    private String name;
    private String email;
    private LocalDate birthDate;
    private Gender gender;

    public int getAge() {
        LocalDate nowDate = LocalDate.now();
        return nowDate.getYear() - birthDate.getYear() -
                (nowDate.getDayOfYear() < birthDate.getDayOfYear() ? 1 : 0);
    }
}
