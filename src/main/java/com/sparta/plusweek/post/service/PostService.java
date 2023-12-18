package com.sparta.plusweek.post.service;

import com.sparta.plusweek.post.dto.PostRequestDto;
import com.sparta.plusweek.post.dto.PostResponseDto;
import com.sparta.plusweek.post.entity.Post;
import com.sparta.plusweek.post.repository.PostRepository;
import com.sparta.plusweek.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public PostResponseDto getPost(Long postId) {
         Post post = postRepository.findById(postId)
                 .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ID 입니다."));
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
}
