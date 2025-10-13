package org.sopt.controller;

import org.sopt.domain.Gender;
import org.sopt.domain.Member;
import org.sopt.service.MemberServiceImpl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class MemberController {

    private MemberServiceImpl memberServiceImpl;

    public Long createMember(String name, String email, Timestamp birthDate, Gender gender) {
        return memberServiceImpl.join(name, email, birthDate, gender);
    }

    public Optional<Member> findMemberById(Long id) {
        return memberServiceImpl.findOne(id);
    }

    public List<Member> getAllMembers() {
        return memberServiceImpl.findAllMembers();
    }
}