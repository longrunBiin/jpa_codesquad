package com.example.codesquad.service;


import com.example.codesquad.dto.memberDto.MemberRequestDto.LoginRequestDto;
import com.example.codesquad.dto.memberDto.MemberRequestDto.SignUpRequestDto;
import com.example.codesquad.entity.Member;
import com.example.codesquad.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public boolean validateDuplicateEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    public void createMember(SignUpRequestDto request) {
        //패스워드 해싱
        String hashPassword = BCrypt.hashpw(request.password(), BCrypt.gensalt());

        Member member = Member.createMemberByRequest(request.email(), hashPassword, request.nickname());
        memberRepository.save(member);
    }

    public Member authenticateMember(LoginRequestDto request) {
        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        if (!BCrypt.checkpw(request.password(), member.getPassword())) {
            throw new IllegalArgumentException("패스워드 다름");
        }

        return member;
    }
}
