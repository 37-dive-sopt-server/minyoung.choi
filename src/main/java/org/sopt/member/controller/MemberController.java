package org.sopt.member.controller;

import org.sopt.domain.Member;
import org.sopt.service.MemberServiceImpl;

import java.util.List;
import java.util.Optional;

public class MemberController {

    private final MemberServiceImpl memberServiceImpl;

    public MemberController(MemberServiceImpl memberServiceImpl) {
        this.memberServiceImpl = memberServiceImpl;
    }

    public Long createMember(String name, String email, String birthDate, String gender) {
        try {
            return memberServiceImpl.join(name, email, birthDate, gender);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ 회원 가입 실패: " + e.getMessage());
            return null;
        }
    }

    public Optional<Member> findMemberById(String id) {
        try {
            return memberServiceImpl.findOneById(id);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ 회원 조회 실패: " + e.getMessage());
            return Optional.empty();
        }
    }

    public List<Member> getAllMembers() {
        return memberServiceImpl.findAllMembers();
    }

    public boolean deleteMemberByEmail(String email) {
        try {
            return memberServiceImpl.deleteByEmail(email);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ 회원 탈퇴 실패: " + e.getMessage());
            return false;
        }
    }
}
