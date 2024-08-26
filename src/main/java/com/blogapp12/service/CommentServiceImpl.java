package com.blogapp12.service;


import com.blogapp12.entity.Comment;
import com.blogapp12.entity.Post;
import com.blogapp12.payload.CommentDto;
import com.blogapp12.payload.PostDto;
import com.blogapp12.payload.PostWithCommentDto;
import com.blogapp12.repository.CommentRepository;
import com.blogapp12.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService{

    private CommentRepository commentRepository;
    private ModelMapper modelMapper;
    private PostRepository postRepository;

    @Override
    public CommentDto createComment(CommentDto commentDto,long postId) {
        Optional<Post> byId = postRepository.findById(postId);
        Post post = byId.get();
        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);
        Comment saveComment = commentRepository.save(comment);
        CommentDto dto = mapToDto(saveComment);
        return dto;
    }
    @Override
    public PostWithCommentDto getAllCommentsByPostId(long id){
        Post post = postRepository.findById(id).get();
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getDescription());
        dto.setContent(post.getContent());
        dto.setDescription(post.getDescription());

        List<Comment> comments = commentRepository.findByPostId(id);
        List<CommentDto> dtos = comments.stream().map(c->mapToDto(c)).collect(Collectors.toList());

        PostWithCommentDto postWithCommentDto = new PostWithCommentDto();
        postWithCommentDto.setCommentDto(dtos);
        postWithCommentDto.setPost(dto);

        return postWithCommentDto;
    }

    Comment mapToEntity(CommentDto dto){
        Comment comment = modelMapper.map(dto, Comment.class);
        return comment;
    }

    CommentDto mapToDto(Comment comment){
        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
        return commentDto;
    }
}
