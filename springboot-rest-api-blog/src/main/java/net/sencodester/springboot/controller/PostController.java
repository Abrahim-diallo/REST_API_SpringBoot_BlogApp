package net.sencodester.springboot.controller;

import jakarta.validation.Valid;
import net.sencodester.springboot.payload.PostDto;
import net.sencodester.springboot.payload.PostResponse;
import net.sencodester.springboot.services.PostService;
import net.sencodester.springboot.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }
    //--------------------------------creer un post blog--------------------------------
    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }
    //----------------------get all posts rest api------
    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", required = false, defaultValue = Constants.DEFAULT_PAGE_NUMBER) int pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = Constants.DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(value = "sortBy", required = false, defaultValue = Constants.DEFAULT_SORT_BY) String sortBy,
            @RequestParam(value = "isAscending", required = false, defaultValue = Constants.DEFAULT_SORT_DIRECTION) String isAscending) {

        return postService.getAllPosts(pageNo, pageSize, sortBy, isAscending);
    }
    //--------------------------get post by id--------------------------
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable long id) {
        PostDto postDto = postService.getPostById(id);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }
    //--------------------------update post by id--------------------------
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable long id) {
        PostDto postResponse = postService.updatePost(postDto, id);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }
    //--------------------------delete post by id--------------------------
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable long id) {
        postService.deletePostById(id);
        return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
    }


}
