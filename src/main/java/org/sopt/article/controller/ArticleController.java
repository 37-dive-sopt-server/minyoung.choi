package org.sopt.article.controller;

import org.sopt.article.domain.Article;
import org.sopt.article.dto.request.PostArticleRequestDto;
import org.sopt.article.dto.response.GetArticleDetailResponseDto;
import org.sopt.article.dto.response.GetArticleResponseDto;
import org.sopt.article.service.ArticleService;
import org.sopt.comment.domain.Comment;
import org.sopt.comment.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;
    private final CommentService commentService;

    public ArticleController(ArticleService articleService, CommentService commentService) {
        this.articleService = articleService;
        this.commentService = commentService;
    }

    // 아티클 생성
    @PostMapping
    public ResponseEntity<GetArticleResponseDto> createArticle(@RequestBody PostArticleRequestDto requestDto) {
        Article article = articleService.createArticle(requestDto);
        return ResponseEntity.ok(GetArticleResponseDto.from(article));
    }

    // 단일 조회
    @GetMapping("/{id}")
    public ResponseEntity<GetArticleDetailResponseDto> getArticle(@PathVariable Long id) {
        Article article = articleService.findArticleById(id);
        List<Comment> commentList = commentService.findAllCommentsByPostId(id);
        return ResponseEntity.ok(GetArticleDetailResponseDto.from(article, commentList));
    }

    // 전체 조회
    @GetMapping
    public ResponseEntity<List<GetArticleResponseDto>> getAllArticles() {
        List<GetArticleResponseDto> articles = articleService.findAllArticles()
                .stream()
                .map(GetArticleResponseDto::from)
                .toList();
        return ResponseEntity.ok(articles);
    }
}
