package com.example.codesquad.repository;

import com.example.codesquad.entity.Comment;
import com.example.codesquad.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
