package net.sencodester.springboot.services;

import net.sencodester.springboot.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> getCommentsByPostById(Long postId);


}
