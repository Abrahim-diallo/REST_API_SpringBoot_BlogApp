package net.sencodester.springboot.servicesImpl;

import net.sencodester.springboot.entites.Post;
import net.sencodester.springboot.exceptions.ResourceNotFoundException;
import net.sencodester.springboot.payload.PostDto;
import net.sencodester.springboot.payload.PostMapper;
import net.sencodester.springboot.payload.PostResponse;
import net.sencodester.springboot.repositories.PostRepository;
import net.sencodester.springboot.services.PostService;
import net.sencodester.springboot.utils.Constants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public PostServiceImpl(PostRepository postRepository,PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;

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
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDirection) {
        boolean isAscending = Constants.DEFAULT_SORT_DIRECTION.equalsIgnoreCase(sortDirection);

        Page<Post> pageable = postRepository.findAll(
                PageRequest.of(
                        pageNo,
                        pageSize,
                        Sort.by(isAscending ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy)
                )
        );

        List<PostDto> content = pageable.getContent().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(pageable.getNumber());
        postResponse.setPageSize(pageable.getSize());
        postResponse.setTotalPages(pageable.getTotalPages());
        postResponse.setTotalElements((int) pageable.getTotalElements());
        postResponse.setLast(pageable.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found with", "id", id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        // get the post by id
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found with", "id", id));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setAuthor(postDto.getAuthor());
        post.setPostDate(postDto.getPostDate());
        post.setDescription(postDto.getDescription());
        // Enregistrer l'ENTITÉ
        Post updatePost = postRepository.save(post);
        PostDto postResponse = mapToDto(updatePost);
        return postResponse;
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }
    private PostDto mapToDto(Post post) {
        return postMapper.mapToDto(post);
    }

    private Post mapToEntity(PostDto postDto) {
        return postMapper.mapToEntity(postDto);
    }
}