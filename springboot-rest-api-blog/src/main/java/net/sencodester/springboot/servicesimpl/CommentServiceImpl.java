package net.sencodester.springboot.servicesimpl;

import net.sencodester.springboot.entites.Comment;
import net.sencodester.springboot.entites.Post;
import net.sencodester.springboot.exceptions.BlogapiException;
import net.sencodester.springboot.exceptions.ResourceNotFoundException;
import net.sencodester.springboot.payload.CommentDto;
import net.sencodester.springboot.repositories.CommentRepository;
import net.sencodester.springboot.repositories.PostRepository;
import net.sencodester.springboot.services.CommentService;
import net.sencodester.springboot.services.PostService;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostService postService, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
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
            throw new BlogapiException(HttpStatus.NOT_FOUND, "Comment not found");
        }
        return mapToDto(comment);

    }


    private CommentDto mapToDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setBody(comment.getBody());
        commentDto.setEmail(comment.getEmail());
        return commentDto;

    }
    // Convertir DTO en ENTITÉ

    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());
        return comment;
    }
}
