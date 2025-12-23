package org.sopt.article.dto.response;

import org.sopt.article.domain.Article;
import org.sopt.comment.domain.Comment;

import java.util.List;

public record GetArticlePreviewResponseDto(
        Long id,
        String title,
        String tag,
        Long authorId,
        String created
) {
    public static GetArticlePreviewResponseDto from(Article article) {
        return new GetArticlePreviewResponseDto(
                article.getId(),
                article.getTitle(),
                article.getTag().name(),
                article.getAuthorId(),
                article.getCreated().toString()
        );
    }
}
