package net.sencodester.springboot.repositories;

import net.sencodester.springboot.entites.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRipository extends JpaRepository<Post, Long> {
}
