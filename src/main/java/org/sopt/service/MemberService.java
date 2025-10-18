package org.sopt.service;

import org.sopt.domain.Gender;
import org.sopt.domain.Member;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface MemberService {
    Long join(String name, String email, Timestamp birthDate, Gender gender);

    boolean deleteByEmail(String email);

    Optional<Member> findOneById(String memberId);

    Optional<Member> findAllMembersByEmail(String email);

    List<Member> findAllMembers();

}
