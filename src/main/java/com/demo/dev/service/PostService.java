package com.demo.dev.service;

import com.demo.dev.model.Post;
import com.demo.dev.model.User;
import com.demo.dev.repo.PostRepo;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private PostRepo postRepo;

    public PostService(PostRepo postRepo){
        this.postRepo = postRepo;
    }

    public Post createPost(User user, Post post) {
        return postRepo.save(post);
    }
}
