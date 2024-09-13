package com.demo.dev.service;

import com.demo.dev.model.Comment;
import com.demo.dev.model.Post;
import com.demo.dev.model.User;
import com.demo.dev.repo.CommentRepo;
import com.demo.dev.repo.PostRepo;
import com.demo.dev.repo.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Service
public class CommentService {

    private CommentRepo commentRepo;
    private PostRepo postRepo;
    private UserRepo userRepo;

    public CommentService(CommentRepo commentRepo, PostRepo postRepo, UserRepo userRepo){
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
        this.userRepo = userRepo;
    }

    public Comment createComment(Long postId, Long userId, Comment comment) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        comment.setPost(post);
        comment.setUser(user);
        return commentRepo.save(comment);
    }

    public Optional<Comment> getComment(Long postId, Long commentId) {
        return commentRepo.findByIdAndPostId(commentId, postId);
    }
}
