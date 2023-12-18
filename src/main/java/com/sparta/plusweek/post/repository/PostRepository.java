package com.sparta.plusweek.post.repository;

import com.sparta.plusweek.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
