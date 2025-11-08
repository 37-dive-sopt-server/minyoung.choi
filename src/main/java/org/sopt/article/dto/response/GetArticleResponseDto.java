package org.sopt.article.dto.response;

import org.sopt.article.domain.Article;

public record GetArticleResponseDto(
        Long id,
        String title,
        String content,
        String tag,
        Long authorId,
        String created
) {
    public static GetArticleResponseDto from(Article article) {
        return new GetArticleResponseDto(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getTag().name(),
                article.getAuthorId(),
                article.getCreated().toString()
        );
    }
}
