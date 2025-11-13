package org.sopt.article.service;

import lombok.RequiredArgsConstructor;
import org.sopt.article.domain.Article;
import org.sopt.article.domain.Tag;
import org.sopt.article.dto.request.PostArticleRequestDto;
import org.sopt.article.repository.ArticleRepository;
import org.sopt.common.exception.CustomException;
import org.sopt.common.exception.ErrorCode;
import org.sopt.member.service.MemberServiceImpl;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberServiceImpl memberServiceImpl;
    private static long sequence = 1L;

    @Override
    public Article createArticle(String title, String content, String tagStr, Long authorId) {
        validateTitle(title);
        validateTitleDuplicated(title);
    public Article createArticle(PostArticleRequestDto requestDto) {
        Validator.validateTitle(requestDto.title());
        validateTitleDuplicated(requestDto.title());

        // 작성자 유효 검증
        memberServiceImpl.findOneById(requestDto.authorId());

        Tag tag = parseTag(requestDto.tag());

        Article article = Article.builder()
                .id(sequence++)
                .title(requestDto.title())
                .content(requestDto.content())
                .tag(tag)
                .created(Timestamp.valueOf(LocalDateTime.now()))
                .authorId(requestDto.authorId())
                .build();

        articleRepository.save(article);
        return article;
    }

    @Override
    public Article findArticleById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
    }

    @Override
    public List<Article> findAllArticles() {
        return articleRepository.findAll();
    }

    private void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }
    }

    private void validateTitleDuplicated(String title) {
        if (articleRepository.findByTitle(title).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_TITLE);
        }
    }

    private Tag parseTag(String tagStr) {
        try {
            return Tag.valueOf(tagStr.toUpperCase());
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }
    }
}
