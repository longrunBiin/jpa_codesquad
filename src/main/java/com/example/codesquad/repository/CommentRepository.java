package com.example.codesquad.repository;

import com.example.codesquad.dto.commentDto.CommentListResponseDto;
import com.example.codesquad.dto.postDto.PostListResponseDto;
import com.example.codesquad.entity.Comment;
import com.example.codesquad.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select new com.example.codesquad.dto.commentDto.CommentListResponseDto(c.content, c.imageUrl) from Comment c")
    Slice<CommentListResponseDto> findSlicedComments(Pageable pageable);
}
