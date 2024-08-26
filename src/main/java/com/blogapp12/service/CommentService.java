package com.blogapp12.service;

import com.blogapp12.payload.CommentDto;
import com.blogapp12.payload.PostWithCommentDto;

public interface CommentService {

    CommentDto createComment(CommentDto commentDto,long postId);

    public PostWithCommentDto getAllCommentsByPostId(long id);
}
