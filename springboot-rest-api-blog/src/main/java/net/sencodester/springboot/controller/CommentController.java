package net.sencodester.springboot.controller;

import net.sencodester.springboot.payload.CommentDto;
import net.sencodester.springboot.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping(value = "/posts/{postId}/comments", consumes = "application/json")
    public ResponseEntity<CommentDto> createComment(@PathVariable long postId,
                                                    @RequestBody CommentDto commentDto) {
        CommentDto createdComment = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(createdComment, HttpStatus.OK);
    }
    @GetMapping(value = "/posts/{postId}/comments", produces = "application/json")
    public ResponseEntity<List<CommentDto>> getCommentsByPost(@PathVariable long postId) {
        List<CommentDto> comments = commentService.getCommentsByPostById(postId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
    @GetMapping(value = "/posts/{postId}/comments/{commentId}", produces = "application/json")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable long postId, @PathVariable long commentId) {
        CommentDto comment = commentService.getCommentById(postId, commentId);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }
}
