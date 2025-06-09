package com.example.codesquad.dto.postDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostListResponseDto {
    private String title;
    private String authorNickname;
}
