package org.sopt.article.repository;

import org.sopt.article.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ArticleRepository {

    private static final Map<Long, Article> store = new HashMap<>();

    public Article save(Article article) {
        store.put(article.getId(), article);
        return article;
    }

    public Optional<Article> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public Optional<Article> findByTitle(String title) {
        return store.values().stream()
                .filter(article -> article.getTitle().equals(title))
                .findFirst();
    }

    public List<Article> findAll() {
        return new ArrayList<>(store.values());
    }
}

