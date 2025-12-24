package org.sopt.comment.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class Comment {
    private Long id;
    private Long postId;
    private Long authorId;
    private String content;
    private LocalDate created;
    private CommentStatus commentStatus;

    public void setStatusDeleted(){
        commentStatus = CommentStatus.DELETED;
    }


    public void setContent(String content){
        this.content = content;
    }
}
