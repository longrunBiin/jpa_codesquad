package com.example.codesquad.dto.memberDto;

public class MemberRequestDto {
    public record SignUpRequestDto(String email, String password){}
}
