package com.sparta.plusweek.post.service;

import com.sparta.plusweek.post.dto.PostRequestDto;
import com.sparta.plusweek.post.dto.PostResponseDto;
import com.sparta.plusweek.post.entity.Post;
import com.sparta.plusweek.post.repository.PostRepository;
import com.sparta.plusweek.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostResponseDto createPosts(PostRequestDto postRequestDto, User user) {
        Post post = new Post(postRequestDto, user.getUsername());
        post.setUser(user);
        postRepository.save(post);
        return new PostResponseDto(post);
    }
}
