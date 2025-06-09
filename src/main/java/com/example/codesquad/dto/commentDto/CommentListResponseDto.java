package com.example.codesquad.dto.commentDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentListResponseDto {
    private Long commentId;
    private String content;
    private String imageUrl;
}
