package net.sencodester.springboot.services;


import net.sencodester.springboot.payload.PostDto;

public interface PostService {
    PostDto createPost(PostDto postDto);
}
