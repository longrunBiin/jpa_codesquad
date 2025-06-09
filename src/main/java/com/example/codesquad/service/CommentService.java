package com.example.codesquad.service;

import com.example.codesquad.dto.commentDto.CommentListResponseDto;
import com.example.codesquad.dto.commentDto.CommentRequestDto.WriteCommentRequestDto;
import com.example.codesquad.entity.Comment;
import com.example.codesquad.entity.Post;
import com.example.codesquad.repository.CommentRepository;
import com.example.codesquad.repository.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public Comment createComment(WriteCommentRequestDto request, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("게시글 없음"));
        Comment comment = Comment.createCommentByRequest(request, post);

        return commentRepository.save(comment);
    }

    public Slice<CommentListResponseDto> getComments(Pageable pageable, Long lastId) {
        if (lastId == null)
            return commentRepository.findFirstComments(pageable);
        return commentRepository.findNextComments(lastId, pageable);
    }
}
