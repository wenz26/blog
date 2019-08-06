package com.cwz.blog.blogback.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;

//使用JPA注解配置映射关系
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
@Entity//告诉JPA这是一个实体类（和数据表映射的类）
@Table(name = "me_article_body")
public class ArticleBody {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //自增主键
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "content_html")
    private String contentHtml;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml == null ? null : contentHtml.trim();
    }
}
