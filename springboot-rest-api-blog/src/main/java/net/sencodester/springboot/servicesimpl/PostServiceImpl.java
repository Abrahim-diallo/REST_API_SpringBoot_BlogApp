package net.sencodester.springboot.servicesimpl;

import net.sencodester.springboot.entites.Post;
import net.sencodester.springboot.payload.PostDto;
import net.sencodester.springboot.repositories.PostRipository;
import net.sencodester.springboot.services.PostService;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRipository postRepository;

    public PostServiceImpl(PostRipository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        // Vérifier la date de publication
        Date postDate = postDto.getPostDate();
        if (postDate == null) {
            postDate = new Date(); // Utiliser la date/heure actuelle si postDate est nulle
        }
        post.setPostDate(postDate);
        // Enregistrer l'ENTITÉ
        Post newPost = postRepository.save(post);
        PostDto postResponse = mapToDto(newPost);
        return postResponse;
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    // Convertir l'ENTITÉ en DTO

    private PostDto mapToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setAuthor(post.getAuthor());
        postDto.setContent(post.getContent());
        postDto.setPostDate(post.getPostDate());
        postDto.setDescription(post.getDescription()); // Ligne corrigée
        return postDto;
    }

    // Convertir DTO en ENTITÉ
    private Post mapToEntity(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setAuthor(postDto.getAuthor());
        post.setDescription(postDto.getDescription());
        return post;
    }
}