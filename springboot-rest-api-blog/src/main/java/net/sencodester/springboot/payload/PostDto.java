package net.sencodester.springboot.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class PostDto {
    private Long id;

    @NotEmpty
    @Size(min = 2, message = "Post title should have at last 2 characters.")
    private String title;

    @NotEmpty
    @Size(min= 10, message = "Post title should have at last 10 characters.")
    private String content;

    @NotEmpty
    private String author;

    private Date postDate;
    @NotEmpty
    private String description;
    private Set<CommentDto> comments;
}
