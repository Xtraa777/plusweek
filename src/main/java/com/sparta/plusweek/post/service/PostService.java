package com.sparta.plusweek.post.service;

import com.sparta.plusweek.post.dto.PostRequestDto;
import com.sparta.plusweek.post.dto.PostResponseDto;
import com.sparta.plusweek.post.entity.Post;
import com.sparta.plusweek.post.repository.PostRepository;
import com.sparta.plusweek.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    //게시글 생성
    public PostResponseDto createPosts(PostRequestDto postRequestDto, User user) {
        Post post = new Post(postRequestDto, user.getUsername());
        post.setUser(user);
        postRepository.save(post);
        return new PostResponseDto(post);
    }

    //단건 게시글 조회
    public PostResponseDto getPostDto(Long postId) {
        Post post = getPost(postId);
        return new PostResponseDto(post);
    }

    //전체 게시글 조회
    public List<PostResponseDto> getPosts() {
        List<PostResponseDto> responseDtoList = new ArrayList<>();
        List<Post> postList = postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));

        for (Post post : postList) {
            responseDtoList.add(new PostResponseDto(post));
        }

        return responseDtoList;
    }

    //게시글 수정
    @Transactional
    public PostResponseDto updatePost(Long postId, PostRequestDto postRequestDto, User user) {
        Post post = getUserPost(postId, user);

        post.setTitle(postRequestDto.getTitle());
        post.setContent(postRequestDto.getContent());

        return new PostResponseDto(post);
    }

    //게시글 삭제
    public void deletePost(Long postId, User user) {
        Post post = getUserPost(postId, user);
        postRepository.delete(post);
    }

    //게시글 예외처리
    private Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
    }

    //게시글 수정시 예외처리
    public Post getUserPost(Long postId, User user) {
        Post post = getPost(postId);

        if (!user.getId().equals(post.getUser().getId())) {
            throw new RejectedExecutionException("수정 권한이 없습니다.");
        }

        return post;
    }
}
