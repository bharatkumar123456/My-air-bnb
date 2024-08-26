package com.blogapp12.controller;

import com.blogapp12.entity.Post;
import com.blogapp12.payload.ListPostDto;
import com.blogapp12.payload.PostDto;
import com.blogapp12.service.PostService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    // http://localhost:8080/api/posts
    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto , BindingResult bindingResult){
       if(bindingResult.hasErrors()){
           return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.CREATED);
       }
        PostDto dto = postService.createPostDto(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    //http://localhost:8080/api/posts/1
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") long id){
        postService.deletePost(id);
        return new ResponseEntity<>("Post Deleted",HttpStatus.OK);
    }
    //http://localhost:8080/api/posts?pageNo=0&pageSize=1&sortBy=title&sortDir=desc
    @GetMapping
    public ResponseEntity<ListPostDto> fetchAllPost(
            @RequestParam(name = "pageNo",defaultValue = "0",required = false) int pageNo,
            @RequestParam(name="pageSize",defaultValue = "5",required = false) int pageSize,
            @RequestParam(name="sortBy",defaultValue = "id",required = false) String sortBy,
            @RequestParam(name="sortDir",defaultValue = "id",required = false) String sortDir
    ){
        ListPostDto listpostDtos = postService.fetchAllPosts(pageNo,pageSize,sortBy,sortDir);
       return new ResponseEntity<>(listpostDtos,HttpStatus.OK);
    }
    //http://localhost:8080/api/posts/1
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable long id){
        PostDto dto = postService.getPostById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);

    }

}
