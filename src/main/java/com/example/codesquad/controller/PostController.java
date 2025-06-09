package com.example.codesquad.controller;

import com.example.codesquad.dto.postDto.PostRequestDto;
import com.example.codesquad.dto.postDto.PostRequestDto.WritePostRequestDto;
import com.example.codesquad.entity.Post;
import com.example.codesquad.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void writePost(@RequestBody WritePostRequestDto request) {
        Post post = postService.createPost(request);
        log.info("게시글 생성 {}, {}", post.getTitle(), post.getContent());
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable Long postId) {
        postService.deletePostAndComment(postId);
    }
}
