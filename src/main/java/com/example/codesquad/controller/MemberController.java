package com.example.codesquad.controller;

import com.example.codesquad.dto.memberDto.MemberRequestDto.SignUpRequestDto;
import com.example.codesquad.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<String> signUp(@RequestBody SignUpRequestDto request) {
        if (memberService.validateDuplicateEmail(request.email()))
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("이미 존재하는 이메일입니다");

        memberService.createMember(request);
        return ResponseEntity.ok("회원 생성");
    }
}
