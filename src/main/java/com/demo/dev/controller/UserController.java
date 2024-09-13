package com.demo.dev.controller;

import com.demo.dev.model.Comment;
import com.demo.dev.model.Post;
import com.demo.dev.model.User;
import com.demo.dev.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id){
        return userService.getUser(id);
    }

    @GetMapping()
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user){
        Optional<User> updatedUser = userService.updateUser(id, user);
        if(updatedUser.isPresent())
            return ResponseEntity.ok(updatedUser.get());
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<User> createUserWithPosts(@RequestBody User user){
        List<Post> posts = user.getPosts();
        return ResponseEntity.ok(userService.createUserWithPosts(user,posts));
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<List<Post>> getUserPosts(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserPosts(id));
    }

    @PostMapping("/{id}/posts")
    public ResponseEntity<Post> addPostToUser(@PathVariable Long id, @RequestBody Post post){
        return ResponseEntity.ok(userService.addPostToUser(id, post));
    }

    @DeleteMapping("{userId}/posts/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable("userId") Long userId
            , @PathVariable("postId") Long postId){
        boolean isPostDeleted = userService.deletePost(userId, postId);
        if(isPostDeleted)
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.notFound().build();
    }

    @PutMapping("/{userId}/posts/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable Long userId, @PathVariable Long postId,
                                           @RequestBody Post post){
        return ResponseEntity.ok(userService.updatePost(userId, postId, post));
    }
}
