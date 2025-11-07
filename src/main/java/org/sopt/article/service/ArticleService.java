package org.sopt.article.service;

import org.sopt.article.domain.Article;

import java.util.List;

public interface ArticleService {
    Article createArticle(String title, String content, String tag, Long authorId);
    Article findArticleById(Long id);
    List<Article> findAllArticles();
}
