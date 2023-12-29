package net.sencodester.springboot.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "post")
public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name ="title", nullable = false)
    private String title;
    @Column(name ="content", nullable = false)
    private String content;
    @Column(name ="author", nullable = false)
    private String author;
    @Column(name ="post_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date postDate; // Utilisation d'un nom de variable en "camelCase"
    @Column(name ="description", nullable = false)
    private String description;
}
