package com.blogapp12.service;

import com.blogapp12.entity.Post;
import com.blogapp12.payload.ListPostDto;
import com.blogapp12.payload.PostDto;

import java.util.List;

public interface PostService {
    public PostDto createPostDto(PostDto postDto);

    void deletePost(long id);

    Post getPost(long id);

    List<Post> getAllPosts();

    ListPostDto fetchAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    public PostDto getPostById(long id);
}
