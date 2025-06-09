package com.example.codesquad.repository;

import com.example.codesquad.dto.commentDto.CommentListResponseDto;
import com.example.codesquad.dto.postDto.PostListResponseDto;
import com.example.codesquad.entity.Comment;
import com.example.codesquad.entity.Post;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT new com.example.codesquad.dto.commentDto.CommentListResponseDto(c.commentId, c.content, c.imageUrl)"
            + " FROM Comment c WHERE c.commentId < :lastId ORDER BY c.commentId DESC")
    Slice<CommentListResponseDto> findNextComments(@Param("lastId") Long lastId, Pageable pageable);

    @Query("select new com.example.codesquad.dto.commentDto.CommentListResponseDto(c.commentId, c.content, c.imageUrl) "
            + "from Comment c ORDER BY c.commentId DESC")
    Slice<CommentListResponseDto> findFirstComments(Pageable pageable);
}
