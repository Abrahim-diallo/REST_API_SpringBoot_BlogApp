package net.sencodester.springboot.payload;
import net.sencodester.springboot.entites.Post;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    @Autowired
    private ModelMapper modelMapper;

    public PostDto mapToDto(Post post) {
        return modelMapper.map(post, PostDto.class);
    }

    public Post mapToEntity(PostDto postDto) {
        return modelMapper.map(postDto, Post.class);
    }
}
