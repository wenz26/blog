package com.cwz.blog.blogback.mapper;

import com.cwz.blog.blogback.entity.Article;
import com.cwz.blog.blogback.mapper.bean.BeanMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ArticleMapper extends BeanMapper<Article> {

    @Select("select * from me_article where id = #{object}")
    @Override
    Article findById(@Param("object") Object object);
}
