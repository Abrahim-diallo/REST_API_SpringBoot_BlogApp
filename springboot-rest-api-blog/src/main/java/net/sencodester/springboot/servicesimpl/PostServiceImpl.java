package net.sencodester.springboot.servicesimpl;

import net.sencodester.springboot.entites.Post;
import net.sencodester.springboot.payload.PostDto;
import net.sencodester.springboot.repositories.PostRipository;
import net.sencodester.springboot.services.PostService;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
@Service
public class PostServiceImpl implements PostService {

    private final PostRipository postRepository;
    public PostServiceImpl(PostRipository postRepository) {
        this.postRepository = postRepository;
    }
    @Override
    public PostDto createPost(PostDto postDto) {
        // Convertir DTO en ENTITÉ
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setAuthor(postDto.getAuthor());
        post.setDescription(postDto.getDescription());

        // Vérifier la date de publication
        Date postDate = postDto.getPostDate();
        if (postDate == null) {
            postDate = new Date(); // Utiliser la date/heure actuelle si postDate est nulle
        }
        post.setPostDate(postDate);

        // Enregistrer l'ENTITÉ
        Post newPost = postRepository.save(post);

        // Convertir l'ENTITÉ en DTO
        PostDto postResponse = new PostDto();
        postResponse.setId(newPost.getId());
        postResponse.setTitle(newPost.getTitle());
        postResponse.setContent(newPost.getContent());
        postResponse.setAuthor(newPost.getAuthor());
        postResponse.setPostDate(newPost.getPostDate());
        postResponse.setDescription(newPost.getDescription());

        return postResponse;
    }



}
