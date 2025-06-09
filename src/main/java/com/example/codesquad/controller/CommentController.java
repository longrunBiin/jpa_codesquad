package com.example.codesquad.controller;

import com.example.codesquad.dto.commentRequestDto.CommentRequestDto.WriteCommentRequestDto;
import com.example.codesquad.dto.postDto.PostRequestDto.WritePostRequestDto;
import com.example.codesquad.entity.Comment;
import com.example.codesquad.entity.Post;
import com.example.codesquad.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("{postId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void writeComment(@RequestBody WriteCommentRequestDto request, @PathVariable Long postId) {
        Comment savedComment = commentService.createComment(request, postId);
        log.info("댓글 생성 id : {}, content : {} ");
    }
}
