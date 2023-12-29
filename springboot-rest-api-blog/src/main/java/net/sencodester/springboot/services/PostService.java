package net.sencodester.springboot.services;


import net.sencodester.springboot.payload.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    List<PostDto> getAllPosts();
    PostDto getPostById(long id);


}
