package com.example.codesquad.controller;

import com.example.codesquad.dto.memberDto.MemberRequestDto.LoginRequestDto;
import com.example.codesquad.dto.memberDto.MemberRequestDto.SignUpRequestDto;
import com.example.codesquad.entity.Member;
import com.example.codesquad.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
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

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequestDto request) {
        if (memberService.validateDuplicateEmail(request.email()))
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("이미 존재하는 이메일입니다");

        memberService.createMember(request);
        return ResponseEntity.ok("회원 생성");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto request, HttpSession session, HttpServletResponse response) {
        Member loginMember;

        try {
            loginMember = memberService.authenticateMember(request);
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(e.getMessage());
        }

        session.setAttribute("loginMemberId", loginMember.getMemberId());

        ResponseCookie cookie = ResponseCookie.from("JSESSIONID", session.getId())
                .httpOnly(true)
                .sameSite("Strict")
                .path("/")
                .maxAge(60 * 60 * 24)
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok("로그인 성공");
    }
}
