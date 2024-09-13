package com.demo.dev.service;

import com.demo.dev.model.Post;
import com.demo.dev.model.User;
import com.demo.dev.repo.PostRepo;
import com.demo.dev.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepo userRepo;
    private PostRepo postRepo;

    public UserService(UserRepo userRepo, PostRepo postRepo){
        this.userRepo = userRepo;
        this.postRepo = postRepo;
    }

    public User addUser(User user) {
        return userRepo.save(user);
    }

    public User getUser(Long id) {
        return userRepo.findById(id).orElse(new User());
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public Optional<User> updateUser(Long id, User user) {
        return userRepo.findById(id).map(newUser -> {
            newUser.setFirstName(user.getFirstName());
            newUser.setLastName(user.getLastName());
            newUser.setEmail(user.getEmail());
            newUser.setPassword(user.getPassword());
            return userRepo.save(newUser);
        });
    }

    @Transactional
    public List<Post> getUserPosts(Long userId) {
        Optional<User> user = userRepo.findById(userId);
        return user.map(User :: getPosts).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public User createUserWithPosts(User user, List<Post> posts) {
        posts.forEach(post -> post.setUser(user));
        user.setPosts(posts);
        return userRepo.save(user);
    }

    @Transactional
    public Post addPostToUser(Long id, Post post) {
        User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        post.setUser(user);
        return postRepo.save(post);
    }

    @Transactional
    public boolean deletePost(Long userId, Long postId) {
        Optional<User> optionalUser = userRepo.findById(userId);
        if(optionalUser.isPresent()){
            User user  = optionalUser.get();
            Optional<Post> optionalPost = postRepo.findByIdAndUserId(postId, userId);
            if(optionalPost.isPresent()){
                postRepo.deleteById(postId);
                return true;
            }
        }
        return false;
    }

    @Transactional
    public Post updatePost(Long userId, Long postId, Post post) {
        Optional<User> optionalUser = userRepo.findById(userId);
        if(optionalUser.isPresent()){
            User user  = optionalUser.get();
            Optional<Post> posts = user.getPosts().stream().filter(
                    p -> p.getId().equals(postId)).findFirst();
            if(posts.isPresent()){
                Post postToUpdate = posts.get();
                postToUpdate.setTitle(post.getTitle());
                postToUpdate.setContent(post.getContent());
                return postRepo.save(postToUpdate);
            }
        }
        return post;
    }
}
