package com.example.codesquad.controller;

import com.example.codesquad.dto.commentDto.CommentListResponseDto;
import com.example.codesquad.dto.commentDto.CommentRequestDto.WriteCommentRequestDto;
import com.example.codesquad.entity.Comment;
import com.example.codesquad.service.CommentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        log.info("댓글 생성 id : {}, content : {} ", savedComment.getCommentId(), savedComment.getContent());
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Slice<CommentListResponseDto>> showComments(@PageableDefault(size = 5) Pageable pageable,
                                                                      @RequestParam(required = false) Long lastId) {
        Slice<CommentListResponseDto> comments = commentService.getComments(pageable, lastId);

        return ResponseEntity.ok()
                .body(comments);
    }
}
