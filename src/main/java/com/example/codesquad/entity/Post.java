package com.example.codesquad.entity;

import com.example.codesquad.dto.postDto.PostRequestDto.WritePostRequestDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue
    private Long postId;
    private String title;
    private String content;
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
    }
    public static Post createPostByRequest(WritePostRequestDto request, Member member) {
        return Post.builder()
                .title(request.title())
                .content(request.content())
                .member(member)
                .build();
    }
    public static Post createPostByRequest(WritePostRequestDto request) {
        return Post.builder()
                .title(request.title())
                .content(request.content())
                .build();
    }
}
