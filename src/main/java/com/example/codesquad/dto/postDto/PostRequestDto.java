package com.example.codesquad.dto.postDto;

public class PostRequestDto {
    public record WritePostRequestDto(String title, String content){}
    public record UpdatePostRequestDto(String title, String content){}
}
