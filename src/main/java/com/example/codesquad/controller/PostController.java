package com.example.codesquad.controller;

import com.example.codesquad.dto.postDto.PostRequestDto.UpdatePostRequestDto;
import com.example.codesquad.dto.postDto.PostRequestDto.WritePostRequestDto;
import com.example.codesquad.entity.Post;
import com.example.codesquad.service.PostImageService;
import com.example.codesquad.service.PostService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostImageService postImageService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> writePost(@RequestPart(value = "content") WritePostRequestDto requestDto,
                                            @RequestPart(value = "images", required = false) List<MultipartFile> images,
                                            HttpServletRequest request) {

        Optional<String> sessionId = extractTokenFromCookies(request.getCookies());
        HttpSession session = request.getSession();
        if (sessionId.isEmpty() || !sessionId.get().equals(session.getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("로그인이 필요합니다");
        }

        Long loginMemberId = (Long) session.getAttribute("loginMemberId");
        Post post = postService.createPost(requestDto, loginMemberId);

        if (!images.isEmpty()){
            postImageService.uploadImage(post, images);
        }

        log.info("게시글 생성 {}, {}", post.getTitle(), post.getContent());
        return ResponseEntity.ok("글이 생성되었습니다.");
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> deletePost(@PathVariable Long postId, HttpSession session, HttpServletRequest request) {
        Optional<String> sessionId = extractTokenFromCookies(request.getCookies());
        if (sessionId.isEmpty() || !sessionId.get().equals(session.getId())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("로그인이 필요합니다");
        }

        Long loginMemberId = (Long) session.getAttribute("loginMemberId");
        try {
            postService.deletePostAndComment(postId, loginMemberId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }

        return ResponseEntity.ok("글이 삭제되었습니다");
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<String> updatePost(@RequestBody UpdatePostRequestDto requestDto, @PathVariable Long postId,
                                             HttpSession session, HttpServletRequest request) {
        Optional<String> sessionId = extractTokenFromCookies(request.getCookies());
        if (sessionId.isEmpty() || !sessionId.get().equals(session.getId())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("로그인이 필요합니다");
        }

        Long loginMemberId = (Long) session.getAttribute("loginMemberId");
        Optional<Post> post = postService.updatePost(requestDto, loginMemberId, postId);

        if (post.isEmpty()){
            return ResponseEntity.badRequest()
                    .body("글을 수정할 수 없습니다.");
        }

        return ResponseEntity.ok("글이 수정되었습니다");
    }

    private Optional<String> extractTokenFromCookies(Cookie[] cookies) {
        if (cookies == null) return Optional.empty();

        for (Cookie cookie : cookies) {
            if ("JSESSIONID".equals(cookie.getName())) {
                return Optional.of(cookie.getValue());
            }
        }
        return Optional.empty();
    }
}
