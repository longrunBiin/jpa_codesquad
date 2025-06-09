package com.example.codesquad.service;

import com.example.codesquad.entity.Post;
import com.example.codesquad.entity.PostImage;
import com.example.codesquad.repository.PostImageRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PostImageService {

    private final PostImageRepository postImageRepository;

    public void uploadImage(Post post, List<MultipartFile> images) {
        try {
            // 이미지 파일 저장을 위한 경로 설정
            String uploadsDir = "src/main/resources/static/uploads/thumbnails/";
            // 이미지 파일 경로를 저장
            for (MultipartFile image : images) {

                String dbFilePath = saveImage(image, uploadsDir);

                // ProductThumbnail 엔티티 생성 및 저장
                PostImage postImage = PostImage.createPostImageByRequest(dbFilePath, post);
                postImageRepository.save(postImage);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String saveImage(MultipartFile image, String uploadsDir) throws IOException {
        // 파일 이름 생성
        String fileName = UUID.randomUUID().toString().replace("-", "") + "_" + image.getOriginalFilename();
        // 실제 파일이 저장될 경로
        String filePath = uploadsDir + fileName;
        // DB에 저장할 경로 문자열
        String dbFilePath = "/uploads/thumbnails/" + fileName;

        Path path = Paths.get(filePath); // Path 객체 생성
        Files.createDirectories(path.getParent()); // 디렉토리 생성
        Files.write(path, image.getBytes()); // 디렉토리에 파일 저장

        return dbFilePath;
    }
}
