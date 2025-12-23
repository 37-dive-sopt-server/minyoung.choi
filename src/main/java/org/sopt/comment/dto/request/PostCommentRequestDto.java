package org.sopt.comment.dto.request;

public record PostCommentRequestDto(
        Long postId,
        Long authorId,
        String content
) {
}
