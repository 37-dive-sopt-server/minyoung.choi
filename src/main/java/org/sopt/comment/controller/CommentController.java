package org.sopt.comment.controller;

import org.sopt.comment.domain.Comment;
import org.sopt.comment.dto.request.PostCommentRequestDto;
import org.sopt.comment.dto.request.UpdateCommentRequestDto;
import org.sopt.comment.dto.response.GetCommentResponseDto;
import org.sopt.comment.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 댓글 생성
    @PostMapping
    public ResponseEntity<GetCommentResponseDto> createComment(@RequestBody PostCommentRequestDto requestDto) {
        Comment comment = commentService.createComment(requestDto);
        return ResponseEntity.ok(GetCommentResponseDto.from(comment));
    }

    // 단일 조회
    @GetMapping("/{id}")
    public ResponseEntity<GetCommentResponseDto> getComment(@PathVariable Long id) {
        Comment comment = commentService.findCommentById(id);
        return ResponseEntity.ok(GetCommentResponseDto.from(comment));
    }

    // 글 아이디 기준으로 전체 조회
    @GetMapping("/all")
    public ResponseEntity<List<GetCommentResponseDto>> getAllComments(@RequestParam Long postId) {
        List<GetCommentResponseDto> comments = commentService.findAllCommentsByPostId(postId)
                .stream()
                .map(GetCommentResponseDto::from)
                .toList();
        return ResponseEntity.ok(comments);
    }

    // 단일 수정
    @PatchMapping("/{id}")
    public ResponseEntity<GetCommentResponseDto> updateComment(@RequestBody UpdateCommentRequestDto requestDto,
                                                               @PathVariable Long id) {
        Comment comment = commentService.updateComment(id, requestDto.content(),requestDto.requestUserId());
        return ResponseEntity.ok(GetCommentResponseDto.from(comment));
    }

    // 단일 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long id,
            @RequestParam Long userId
    ) {
        commentService.deleteComment(id, userId);
        return ResponseEntity.ok().build();
    }

}
