package com.sparta.plusweek.post.entity;

import com.sparta.plusweek.Timestamped;
import com.sparta.plusweek.post.dto.PostRequestDto;
import com.sparta.plusweek.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "posts")
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(length = 5000, nullable = false)
    private String content;

    @Column
    private String username;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Post(PostRequestDto postResponseDto, String username) {
        this.title = postResponseDto.getTitle();
        this.content = postResponseDto.getContent();
        this.username = username;
    }
}
