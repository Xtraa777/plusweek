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

import java.util.List;
import java.util.concurrent.RejectedExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    //게시글 생성
    @PostMapping
    public ResponseEntity<PostResponseDto> createPosts(
            @RequestBody PostRequestDto postRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        PostResponseDto postResponseDto = postService.createPosts(postRequestDto, userDetails.getUser());

        return ResponseEntity.status(201).body(postResponseDto);
    }

    //단건 게시글 조회
    @GetMapping("/{postId}")
    public ResponseEntity<CommonResponseDto> getPosts(@PathVariable Long postId) {
        try {
            PostResponseDto postResponseDto = postService.getPostDto(postId);
            return ResponseEntity.ok().body(postResponseDto);
        } catch (IllegalArgumentException exception) {
            return  ResponseEntity.badRequest()
                    .body(new CommonResponseDto(exception.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    //전체 게시글 조회
    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getPosts() {
        List<PostResponseDto> responseDtoList = postService.getPosts();
        return ResponseEntity.ok(responseDtoList);
    }

    //게시글 수정
    @PatchMapping("/{postId}")
    public ResponseEntity<CommonResponseDto> updatePost(
            @PathVariable Long postId,
            @RequestBody PostRequestDto postRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        try {
            PostResponseDto responseDto = postService.updatePost(postId, postRequestDto, userDetails.getUser());
            return ResponseEntity.ok(responseDto);
        }catch (RejectedExecutionException | IllegalArgumentException exception){
            return ResponseEntity.badRequest().body(new CommonResponseDto(exception.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    //게시글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<CommonResponseDto> deletePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        try {
            postService.deletePost(postId, userDetails.getUser());
            return ResponseEntity.status(HttpStatus.OK.value()).body(new CommonResponseDto("삭제 성공", HttpStatus.OK.value()));
        } catch (RejectedExecutionException | IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
}
