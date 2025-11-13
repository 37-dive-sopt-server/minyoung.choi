package org.sopt.article.service;

import org.sopt.article.domain.Article;
import org.sopt.article.dto.request.PostArticleRequestDto;

import java.util.List;

public interface ArticleService {
    Article createArticle(PostArticleRequestDto requestDto);
    Article findArticleById(Long id);
    List<Article> findAllArticles();
}
