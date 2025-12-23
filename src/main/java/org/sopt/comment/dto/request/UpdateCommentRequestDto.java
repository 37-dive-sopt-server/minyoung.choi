package org.sopt.comment.dto.request;

public record UpdateCommentRequestDto (
        Long requestUserId,
        String content
){
}
