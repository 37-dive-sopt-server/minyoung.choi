package org.sopt.comment.repository;

import org.sopt.comment.domain.Comment;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CommentRepository {

    private static final Map<Long, Comment> store = new HashMap<>();

    public Comment save(Comment comment) {
        store.put(comment.getId(), comment);
        return comment;
    }

    public Optional<Comment> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Comment> findByPostId(Long postId) {
        return store.values().stream()
                .filter(comment -> comment.getPostId().equals(postId))
                .toList();
    }
}
