package org.sopt.article.dto.response;

import org.sopt.article.domain.Article;
import org.sopt.comment.domain.Comment;

import java.util.List;

public record GetArticleDetailResponseDto(
        Long id,
        String title,
        String content,
        String tag,
        Long authorId,
        String created,
        List<Comment> commentList
) {
    public static GetArticleDetailResponseDto from(Article article, List<Comment> commentList) {
        return new GetArticleDetailResponseDto(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getTag().name(),
                article.getAuthorId(),
                article.getCreated().toString(),
                commentList
        );
    }
}
