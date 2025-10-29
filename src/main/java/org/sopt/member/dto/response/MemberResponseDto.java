package org.sopt.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.sopt.member.domain.Member;

@Getter
@Builder
@AllArgsConstructor
public class MemberResponseDto {
    private Long id;
    private String name;
    private String email;
    private String birthDate;
    private String gender;

    public static MemberResponseDto from(Member member) {
        return MemberResponseDto.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .birthDate(member.getBirthDate().toString())
                .gender(member.getGender().toString())
                .build();
    }
}