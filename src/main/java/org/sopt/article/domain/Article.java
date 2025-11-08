package org.sopt.article.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Builder
@AllArgsConstructor
public class Article {

    private Long id;
    private Tag tag;
    private String title;
    private String content;
    private Timestamp created;
    private Long authorId;

}