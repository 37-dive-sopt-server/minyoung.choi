package org.sopt.article.dto.request;

public record PostArticleRequestDto(
        String title,
        String content,
        String tag,
        Long authorId
) {
}
