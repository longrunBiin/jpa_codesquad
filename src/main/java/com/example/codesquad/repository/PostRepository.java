package com.example.codesquad.repository;

import com.example.codesquad.dto.postDto.PostListResponseDto;
import com.example.codesquad.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select new com.example.codesquad.dto.postDto.PostListResponseDto(p.title, p.member.nickname) from Post p")
    Page<PostListResponseDto> findPagedPosts(Pageable pageable);
}
