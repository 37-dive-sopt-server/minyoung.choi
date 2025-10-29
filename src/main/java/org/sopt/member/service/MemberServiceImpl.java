package org.sopt.member.service;

import org.sopt.member.domain.Gender;
import org.sopt.member.domain.Member;
import org.sopt.member.repository.MemoryMemberRepository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

public class MemberServiceImpl implements MemberService {

    private final MemoryMemberRepository memberRepository;
    private static long sequence = 1L;

    public MemberServiceImpl(MemoryMemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Long join(String name, String email, String birthDateStr, String genderStr) {
        // 입력 검증
        validateName(name);
        validateEmailFormat(email);
        validateEmailDuplicated(email);


        Timestamp birthDate = validateBirthDate(birthDateStr);

        Gender gender = validateGender(gender);

        Member member = new Member(sequence++, name, email, birthDate, gender);
        validateAge(member);

        memberRepository.save(member);
        return member.getId();
    }

    @Override
    public boolean deleteByEmail(String email) {
        validateEmailFormat(email);
        Optional<Member> memberOpt = memberRepository.findAllByEmail(email);
        if (memberOpt.isEmpty()) {
            throw new IllegalArgumentException("해당 이메일의 회원을 찾을 수 없습니다.");
        }
        return memberRepository.deleteById(memberOpt.getId());
    }

    @Override
    public boolean delete(Long memberId) {
        return memberRepository.deleteById(memberId);
    }

    @Override
    public Optional<Member> findOneById(String memberIdStr) {
        Long memberId;
        try {
            memberId = Long.parseLong(memberIdStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("⚠️ 유효하지 않은 ID 형식입니다. 숫자를 입력해주세요.");
        }
        return memberRepository.findById(memberId);
    }


    @Override
    public Optional<Member> findAllMembersByEmail(String email) {
        return memberRepository.findAllByEmail(email);
    }

    @Override
    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("⚠️ 이름을 입력해주세요.");
        }
    }

    private void validateEmailFormat(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("⚠️ 이메일을 입력해주세요.");
        }
        if (!email.contains("@")) {  // @을 기준으로 단순 형식 검증
            throw new IllegalArgumentException("⚠️ 올바른 이메일 형식이 아닙니다.");
        }
    }

    private Timestamp validateBirthDate(String birthDateStr) {
        try {
            return Timestamp.valueOf(birthDateStr + " 00:00:00");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("⚠️ 올바른 날짜 형식이 아닙니다. 예: 2000-01-01");
        }
    }

    private Gender validateGender(String genderStr) {
        try {
            return Gender.valueOf(genderStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("⚠️ 올바른 성별을 입력해주세요 (MALE/FEMALE)");
        }
    }

    private void validateAge(Member member) {
        if (member.getAge() < 20) {
            throw new IllegalArgumentException("20세 미만은 가입할 수 없습니다.");
        }
    }
}
