package org.sopt.member.service;

import org.sopt.member.domain.Member;
import org.sopt.member.dto.request.MemberRequestDto;

import java.util.List;

public interface MemberService {
    Member join(MemberRequestDto memberRequestDto);

    void deleteByEmail(String email);

    void delete(Long memberId);

    Member findOneById(Long memberId);

    Member findAllMembersByEmail(String email);

    List<Member> findAllMembers();

}
