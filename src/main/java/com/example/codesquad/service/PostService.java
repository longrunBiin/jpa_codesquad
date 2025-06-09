package com.example.codesquad.service;

import com.example.codesquad.dto.postDto.PostListResponseDto;
import com.example.codesquad.dto.postDto.PostRequestDto.UpdatePostRequestDto;
import com.example.codesquad.dto.postDto.PostRequestDto.WritePostRequestDto;
import com.example.codesquad.entity.Member;
import com.example.codesquad.entity.Post;
import com.example.codesquad.repository.MemberRepository;
import com.example.codesquad.repository.PostRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Post createPost(WritePostRequestDto request, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));

        Post post = Post.createPostByRequest(request, member);
        return postRepository.save(post);
    }

    @Transactional
    public void deletePostAndComment(Long postId, Long memberId) {
        memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));

        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("게시글 없음"));
        if (!memberId.equals(post.getMember().getMemberId())){
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다");
        }
        postRepository.delete(post);
    }

    @Transactional
    public Optional<Post> updatePost(UpdatePostRequestDto request, Long memberId, Long postId) {
        memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다"));

        if (!memberId.equals(post.getMember().getMemberId())){
            return Optional.empty();
        }
        post.updatePost(request.title(), request.content());
        return Optional.of(postRepository.save(post));
    }

    public Page<PostListResponseDto> getPostByPage(Pageable pageable) {
        return postRepository.findPagedPosts(pageable);

    }
}
