package net.sencodester.springboot.payload;

import lombok.Data;

import java.util.Date;

@Data
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private String author;
    private Date postDate;
    private String description;


}
