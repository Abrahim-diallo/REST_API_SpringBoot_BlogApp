package net.sencodester.springboot.services;

import net.sencodester.springboot.entites.Comment;
import net.sencodester.springboot.payload.CommentDto;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
    Iterable<CommentDto> findAllComments();
    CommentDto findById(long id);
    CommentDto updateComment(CommentDto commentDto);

    void deleteComment(long id);

}
