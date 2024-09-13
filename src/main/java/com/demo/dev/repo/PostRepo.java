package com.demo.dev.repo;

import com.demo.dev.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepo extends JpaRepository<Post, Long> {

    Optional<Post> findByIdAndUserId(Long postId, Long userId);
}
