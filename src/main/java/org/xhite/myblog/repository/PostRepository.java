package org.xhite.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.xhite.myblog.model.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUserId(Long userId);
}
