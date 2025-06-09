package com.example.codesquad.entity;

import com.example.codesquad.dto.postDto.PostRequestDto.WritePostRequestDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue
    private Long memberId;
    private String email;
    private String password;


    public static Member createMemberByRequest(String email, String password) {
        return Member.builder()
                .email(email)
                .password(password)
                .build();
    }
}
