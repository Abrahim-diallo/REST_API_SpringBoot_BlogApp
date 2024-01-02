package net.sencodester.springboot.controller;

import jakarta.validation.Valid;
import net.sencodester.springboot.payload.CommentDto;
import net.sencodester.springboot.services.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@PathVariable long postId,
                                                    @RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentService.createComment(postId, commentDto));
    }
    @GetMapping
    public ResponseEntity<List<CommentDto>> getCommentsByPost(@PathVariable long postId) {
        return ResponseEntity.ok(commentService.getCommentsByPostById(postId));
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable long postId,
                                                     @PathVariable long commentId) {
        return ResponseEntity.ok(commentService.getCommentById(postId, commentId));
    }
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable long postId,
                                                     @PathVariable long commentId,
                                                     @Valid @RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentService.updateComment(postId, commentId, commentDto));

    }
    @DeleteMapping("/{commentId}")
    public void deleteCommentById(@PathVariable long postId,
                                  @PathVariable long commentId) {
        commentService.deleteCommentById(postId, commentId);
    }
}
