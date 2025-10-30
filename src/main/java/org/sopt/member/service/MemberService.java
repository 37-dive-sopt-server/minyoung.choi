package org.sopt.member.service;

import org.sopt.member.domain.Member;

import java.util.List;

public interface MemberService {
    Member join(String name, String email, String birthDateStr, String genderStr);

    void deleteByEmail(String email);

    void delete(Long memberId);

    Member findOneById(Long memberId);

    Member findAllMembersByEmail(String email);

    List<Member> findAllMembers();

}
