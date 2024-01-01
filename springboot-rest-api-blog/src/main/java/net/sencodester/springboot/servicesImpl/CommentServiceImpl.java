package net.sencodester.springboot.servicesImpl;

import net.sencodester.springboot.entites.Comment;
import net.sencodester.springboot.entites.Post;
import net.sencodester.springboot.exceptions.BlogApiException;
import net.sencodester.springboot.exceptions.ResourceNotFoundException;
import net.sencodester.springboot.payload.CommentDto;
import net.sencodester.springboot.payload.CommentMapper;
import net.sencodester.springboot.repositories.CommentRepository;
import net.sencodester.springboot.repositories.PostRepository;
import net.sencodester.springboot.services.CommentService;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    private final CommentMapper commentMapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.commentMapper = commentMapper;
    }
    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment newComment = mapToEntity(commentDto);
        Post post = postRepository.findById(postId).
                orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        // set the comment
        newComment.setPost(post);

        // Enregistrer l'ENTITÉ
        Comment newComment1 = commentRepository.save(newComment);
        CommentDto commentResponse = mapToDto(newComment1);
        return commentResponse;

    }

    @Override
    public  List<CommentDto> getCommentsByPostById(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        //convertir les entités en dtos
        return comments.stream().map(this::mapToDto).toList();
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        // Retrieve the comment by postId
        Post post = postRepository.findById(postId).
                orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentId).
                orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        //
        if (!Objects.equals(comment.getPost().getId(), postId)) {
            throw new BlogApiException(HttpStatus.NOT_FOUND, "Comment not found");
        }
        return mapToDto(comment);

    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentResquest) {
        // Retrieve the comment by postId
        Post post = postRepository.findById(postId).
                orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentResquest.getId()).
                orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentResquest.getId()));
        if (!Objects.equals(comment.getPost().getId(), postId)) {
            throw new BlogApiException(HttpStatus.NOT_FOUND, "Comment not found");
        }
        // Update the comment
        comment.setName(commentResquest.getName());
        comment.setBody(commentResquest.getBody());
        comment.setEmail(commentResquest.getEmail());
        // Enregistrer l'ENTITÉ
        Comment newComment = commentRepository.save(comment);
        CommentDto commentResponse = mapToDto(newComment);
        return commentResponse;
    }

    @Override
    public void deleteCommentById(Long postId, Long commentId) {
        // Retrieve the comment by postId
        Post post = postRepository.findById(postId).
                orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentId).
                orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        if (!Objects.equals(comment.getPost().getId(), postId)) {
            throw new BlogApiException(HttpStatus.NOT_FOUND, "Comment not found");
        }
        commentRepository.delete(comment);
    }
    private CommentDto mapToDto(Comment comment) {
        return commentMapper.mapToDto(comment);
    }

    private Comment mapToEntity(CommentDto commentDto) {
        return commentMapper.mapToEntity(commentDto);
    }
}
