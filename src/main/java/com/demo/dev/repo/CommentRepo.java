package com.demo.dev.repo;

import com.demo.dev.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepo extends JpaRepository<Comment, Long> {

    Optional<Comment> findByIdAndPostId(Long CommentId, Long PostId);
}
