package org.sopt.comment.service;

import org.sopt.comment.domain.Comment;
import org.sopt.comment.dto.request.PostCommentRequestDto;

import java.util.List;

public interface CommentService {
    Comment createComment(PostCommentRequestDto requestDto);

    Comment findCommentById(Long id);

    List<Comment> findAllCommentsByPostId(Long postId);

    Comment updateComment(Long commentId, String content, Long requestUserId);

    void deleteComment(Long commentId, Long requestUserId);
}
