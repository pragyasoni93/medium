package com.demo.dev.controller;

import com.demo.dev.model.Comment;
import com.demo.dev.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("v1/posts/{postId}/comments")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @PostMapping(consumes = {"application/json", "application/json;charset=UTF-8"}, produces = "application/json")
    public ResponseEntity<Comment> createComment(@PathVariable Long postId, @RequestBody Comment comment,
                                                 @RequestParam Long userId){
        return ResponseEntity.ok(commentService.createComment(postId, userId, comment));
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<Comment> getComment(@PathVariable Long postId, @PathVariable Long commentId){
        Optional<Comment> comment = commentService.getComment(postId, commentId);
        return comment.map(ResponseEntity :: ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
