package com.cwz.blog.blogback.repository;

import com.cwz.blog.blogback.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

//继承JpaRepository来完成对数据库的操作
//JpaRepository<User, Integer>泛型的第一个参数是实体类名，第二个参数是实体类对应表的主键的数据类型
public interface ArticleRepository extends JpaRepository<Article, Integer> {

}
