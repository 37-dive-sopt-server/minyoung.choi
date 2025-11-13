package org.sopt.member.service;

import org.sopt.common.exception.CustomException;
import org.sopt.common.exception.ErrorCode;
import org.sopt.common.validator.Validator;
import org.sopt.member.domain.Gender;
import org.sopt.member.domain.Member;
import org.sopt.member.dto.request.MemberRequestDto;
import org.sopt.member.repository.MemoryMemberRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemoryMemberRepository memberRepository;
    private static long sequence = 1L;

    public MemberServiceImpl(MemoryMemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Member join(MemberRequestDto memberRequestDto) {
        Validator.validateName(memberRequestDto.getName());
        Validator.validateEmailFormat(memberRequestDto.getEmail());
        validateEmailDuplicated(memberRequestDto.getEmail());

        LocalDate birthDate = parseBirthDate(memberRequestDto.getBirthDate());
        Gender gender = parseGender(memberRequestDto.getGender());

        Member member = new Member(sequence++, memberRequestDto.getName(), memberRequestDto.getEmail(), birthDate, gender);
        Validator.validateAge(member);

        memberRepository.save(member);
        return member;
    }

    @Override
    public void deleteByEmail(String email) {
        Validator.validateEmailFormat(email);

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

    private void validateEmailDuplicated(String email) {
        if (memberRepository.findAllByEmail(email).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }
    }

    private LocalDate parseBirthDate(String birthDateStr) {
        try {
            return LocalDate.parse(birthDateStr);
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.INVALID_BIRTH_INPUT);
        }
    }

    private Gender parseGender(String genderStr) {
        try {
            return Gender.valueOf(genderStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.INVALID_GENDER_INPUT);
        }
    }

}
