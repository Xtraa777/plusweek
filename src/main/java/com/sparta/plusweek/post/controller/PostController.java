package com.sparta.plusweek.post.controller;

import com.sparta.plusweek.post.dto.PostRequestDto;
import com.sparta.plusweek.post.dto.PostResponseDto;
import com.sparta.plusweek.post.service.PostService;
import com.sparta.plusweek.user.dto.CommonResponseDto;
import com.sparta.plusweek.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDto> createPosts(
            @RequestBody PostRequestDto postRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        PostResponseDto postResponseDto = postService.createPosts(postRequestDto, userDetails.getUser());

        return ResponseEntity.status(201).body(postResponseDto);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<CommonResponseDto> getPosts(@PathVariable Long postId) {
        try {
            PostResponseDto postResponseDto = postService.getPost(postId);
            return ResponseEntity.ok().body(postResponseDto);
        } catch (IllegalArgumentException exception) {
            return  ResponseEntity.badRequest()
                    .body(new CommonResponseDto(exception.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

}
