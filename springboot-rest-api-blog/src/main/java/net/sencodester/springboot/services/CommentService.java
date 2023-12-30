package net.sencodester.springboot.services;

import net.sencodester.springboot.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> getCommentsByPostById(Long postId);

    CommentDto getCommentById(Long postId, Long commentId);

    CommentDto updateComment(long postId, long commentId, CommentDto commentResquest);

    void deleteCommentById(Long postId, Long commentId);


}
