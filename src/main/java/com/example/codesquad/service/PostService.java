package com.example.codesquad.service;

import com.example.codesquad.dto.postDto.PostRequestDto;
import com.example.codesquad.dto.postDto.PostRequestDto.WritePostRequestDto;
import com.example.codesquad.entity.Post;
import com.example.codesquad.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post createPost(WritePostRequestDto request) {
        Post post = Post.createPostByRequest(request);
        return postRepository.save(post);
    }

    public void deletePostAndComment(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("게시글 없음"));
        postRepository.delete(post);
    }
}
