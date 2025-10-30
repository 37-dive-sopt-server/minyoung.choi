package org.sopt.member.service;

import lombok.RequiredArgsConstructor;
import org.sopt.common.exception.CustomException;
import org.sopt.common.exception.ErrorCode;
import org.sopt.member.domain.Gender;
import org.sopt.member.domain.Member;
import org.sopt.member.repository.MemoryMemberRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemoryMemberRepository memberRepository;
    private static long sequence = 1L;

    public MemberServiceImpl(MemoryMemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Member join(String name, String email, String birthDateStr, String genderStr) {
        validateName(name);
        validateEmailFormat(email);
        validateEmailDuplicated(email);

        Timestamp birthDate = parseBirthDate(birthDateStr);
        Gender gender = parseGender(genderStr);

        Member member = new Member(sequence++, name, email, birthDate, gender);
        validateAge(member);

        memberRepository.save(member);
        return member;
    }

    @Override
    public void deleteByEmail(String email) {
        validateEmailFormat(email);

        Member member = memberRepository.findAllByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        memberRepository.deleteById(member.getId());
    }

    @Override
    public void delete(Long memberId) {
        memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        memberRepository.deleteById(memberId);
    }

    @Override
    public Member findOneById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }

    @Override
    public Member findAllMembersByEmail(String email) {
        return memberRepository.findAllByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }

    @Override
    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }
    }

    private void validateEmailFormat(String email) {
        if (email == null || email.isBlank() || !email.contains("@")) {
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }
    }

    private void validateEmailDuplicated(String email) {
        if (memberRepository.findAllByEmail(email).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }
    }

    private Timestamp parseBirthDate(String birthDateStr) {
        try {
            return Timestamp.valueOf(birthDateStr + " 00:00:00");
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }
    }

    private Gender parseGender(String genderStr) {
        try {
            return Gender.valueOf(genderStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }
    }

    private void validateAge(Member member) {
        if (member.getAge() < 20) {
            throw new CustomException(ErrorCode.UNDERAGE_MEMBER);
        }
    }
}
