package com.cwz.blog.blogback.mapper;

import com.cwz.blog.blogback.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

public interface ArticleMapper {
    public Article findById(@Param("id") Integer id);
}
