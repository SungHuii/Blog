package me.sunghui.springbootdeveloper.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.sunghui.springbootdeveloper.domain.Article;
import me.sunghui.springbootdeveloper.domain.Comment;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
public class ArticleViewResponse {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    // 글쓴이 정보 추가
    private String author;
    private List<Comment> comments;

    public ArticleViewResponse(Article article){
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.createdAt = article.getCreatedAt();
        this.author = article.getAuthor();
        this.comments = article.getComments();
    }
}
