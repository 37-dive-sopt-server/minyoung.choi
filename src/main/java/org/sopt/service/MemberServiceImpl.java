package org.sopt.service;

import org.sopt.domain.Gender;
import org.sopt.repository.MemoryMemberRepository;
import org.sopt.domain.Member;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class MemberServiceImpl implements MemberService {

    private MemoryMemberRepository memberRepository;
    private static long sequence = 1L;

    public Long join(String name, String email, Timestamp birthDate, Gender gender) {
        Member member = new Member(sequence++, name, email, birthDate, gender);
        memberRepository.save(member);
        return member.getId();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }
}