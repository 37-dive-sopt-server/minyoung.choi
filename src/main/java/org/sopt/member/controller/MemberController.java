package org.sopt.member.controller;

import org.sopt.member.dto.request.MemberRequestDto;
import org.sopt.member.dto.response.MemberResponseDto;
import org.sopt.member.service.MemberServiceImpl;
import org.sopt.member.domain.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class MemberController {

    private final MemberServiceImpl memberServiceImpl;

    public MemberController(MemberServiceImpl memberServiceImpl) {
        this.memberServiceImpl = memberServiceImpl;
    }

    @PostMapping
    public ResponseEntity<MemberResponseDto> createMember(@RequestBody MemberRequestDto requestDto) {
        Member member = memberServiceImpl.join(
                requestDto.getName(),
                requestDto.getEmail(),
                requestDto.getBirthDate(),
                requestDto.getGender()
        );
        return ResponseEntity.ok(MemberResponseDto.from(member));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDto> findMemberById(@PathVariable Long id) {
        Member member = memberServiceImpl.findOneById(id);
        return ResponseEntity.ok(MemberResponseDto.from(member));
    }

    @GetMapping
    public ResponseEntity<List<MemberResponseDto>> getAllMembers() {
        List<MemberResponseDto> members = memberServiceImpl.findAllMembers()
                .stream()
                .map(MemberResponseDto::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(members);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMemberByEmail(@RequestParam String email) {
        memberServiceImpl.deleteByEmail(email);
        return ResponseEntity.ok().build();
    }
}
