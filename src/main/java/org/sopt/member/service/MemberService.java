package org.sopt.member.service;

import org.sopt.member.domain.Gender;
import org.sopt.member.domain.Member;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface MemberService {
    Member join(String name, String email, Timestamp birthDate, Gender gender);

    void deleteByEmail(String email);

    void delete(Long memberId);

    Member findOneById(Long memberId);

    Member findAllMembersByEmail(String email);

    List<Member> findAllMembers();

}
