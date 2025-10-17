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
        //이메일 중복 여부 확인
        Optional<Member> existMemberByEmail = findAllMembersByEmail(email);
        if (existMemberByEmail.isPresent()) {
            //이미 해당 메일 사용하는 유저 있는경우 -> id값으로 null 값 리턴
            return null;
        }

        Member member = new Member(sequence++, name, email, birthDate, gender);
        memberRepository.save(member);
        return member.getId();
    }

    public boolean deleteByEmail(String email) {
        return memberRepository.deleteByEmail(email);
    }

    public Optional<Member> findAllMembersByEmail(String email) {
        return memberRepository.findAllByEmail(email);
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }
    private void validateAge(Member member) {
        if (member.getAge() < 20) {
            throw new IllegalArgumentException("20세 미만은 가입할 수 없습니다.");
        }
    }
}