package com.sparta.plusweek.comment.service;

import com.sparta.plusweek.comment.dto.CommentRequestDto;
import com.sparta.plusweek.comment.dto.CommentResponseDto;
import com.sparta.plusweek.comment.entity.Comment;
import com.sparta.plusweek.comment.repository.CommentRepository;
import com.sparta.plusweek.post.entity.Post;
import com.sparta.plusweek.post.service.PostService;
import com.sparta.plusweek.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final PostService postService;

    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, User user) {
        Post post = postService.getPost(commentRequestDto.getPostId());

        Comment comment = new Comment(commentRequestDto);
        comment.setUser(user);
        comment.setPost(post);

        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto commentRequestDTO, User user) {
        Comment comment = getUserComment(commentId, user);

        comment.setContent(commentRequestDTO.getContent());

        return new CommentResponseDto(comment);
    }

    public void deleteComment(Long commentId, User user) {
        Comment comment = getUserComment(commentId, user);

        commentRepository.delete(comment);
    }

    private Comment getUserComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글 ID 입니다."));

        if(!user.getId().equals(comment.getUser().getId())) {
            throw new RejectedExecutionException("작성자만 수정할 수 있습니다.");
        }
        return comment;
    }
}
