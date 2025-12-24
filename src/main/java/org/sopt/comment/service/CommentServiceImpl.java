package org.sopt.comment.service;

import lombok.RequiredArgsConstructor;
import org.sopt.comment.domain.Comment;
import org.sopt.comment.domain.CommentStatus;
import org.sopt.comment.dto.request.PostCommentRequestDto;
import org.sopt.article.repository.ArticleRepository;
import org.sopt.comment.repository.CommentRepository;
import org.sopt.common.exception.CustomException;
import org.sopt.common.exception.ErrorCode;
import org.sopt.common.validator.Validator;
import org.sopt.member.repository.MemoryMemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final MemoryMemberRepository memoryMemberRepository;
    private static long sequence = 1L;

    @Override
    public Comment createComment(PostCommentRequestDto requestDto) {
        validateCommentCreation(requestDto.postId(), requestDto.authorId(), requestDto.content());


        Comment comment = Comment.builder()
                .id(sequence++)
                .commentStatus(CommentStatus.POSTED)
                .postId(requestDto.postId())
                .content(requestDto.content())
                .authorId(requestDto.authorId())
                .created(LocalDate.from(LocalDateTime.now()))
                .build();

        commentRepository.save(comment);
        return comment;
    }


    @Override
    public Comment findCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
    }

    @Override
    public List<Comment> findAllCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId).stream().toList();
    }

    @Override
    public Comment updateComment(Long commentId, String content, Long requestUserId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        Validator.validateCommentLength(content);
        validateAuthorizedUser(requestUserId, comment);

        comment.setContent(content);
        return comment;
    }

    private static void validateAuthorizedUser(Long requestUserId, Comment comment) {
        if (!Objects.equals(comment.getAuthorId(), requestUserId)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER_REQUEST);
        }
    }

    @Override
    public void deleteComment(Long commentId, Long requestUserId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        validateAuthorizedUser(requestUserId, comment);

        comment.setStatusDeleted();
    }


    private void validateCommentCreation(Long postId, Long userId, String content) {
        validateCommentRequest(postId, userId);
        Validator.validateCommentLength(content);

    }

    private void validateCommentRequest(Long postId, Long userId) {
        articleRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        memoryMemberRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    }

}