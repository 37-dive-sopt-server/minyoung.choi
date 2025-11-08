package org.sopt.article.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.article.domain.Article;
import org.sopt.article.dto.request.PostArticleRequestDto;
import org.sopt.article.dto.response.GetArticleResponseDto;
import org.sopt.article.service.ArticleServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/article")
public class ArticleController {

    private final ArticleServiceImpl articleServiceImpl;

    // 아티클 생성
    @PostMapping
    public ResponseEntity<GetArticleResponseDto> createArticle(@RequestBody PostArticleRequestDto requestDto) {
        Article article = articleServiceImpl.createArticle(
                requestDto.title(),
                requestDto.content(),
                requestDto.tag(),
                requestDto.authorId()
        );
        return ResponseEntity.ok(GetArticleResponseDto.from(article));
    }

    // 단일 조회
    @GetMapping("/{id}")
    public ResponseEntity<GetArticleResponseDto> getArticle(@PathVariable Long id) {
        Article article = articleServiceImpl.findArticleById(id);
        return ResponseEntity.ok(GetArticleResponseDto.from(article));
    }

    // 전체 조회
    @GetMapping
    public ResponseEntity<List<GetArticleResponseDto>> getAllArticles() {
        List<GetArticleResponseDto> articles = articleServiceImpl.findAllArticles()
                .stream()
                .map(GetArticleResponseDto::from)
                .toList();
        return ResponseEntity.ok(articles);
    }
}
