package org.sopt.comment.dto.response;

import org.sopt.comment.domain.Comment;

public record GetCommentResponseDto (
        Long commentId,
        Long postId,
        Long authorId,
        String content,
        String created
){
    public static GetCommentResponseDto from(Comment comment) {
        return new GetCommentResponseDto(
                comment.getId(),
                comment.getPostId(),
                comment.getAuthorId(),
                comment.getContent(),
                comment.getCreated().toString()
        );
    }

}
