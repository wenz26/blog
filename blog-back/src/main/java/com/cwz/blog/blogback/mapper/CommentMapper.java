package com.cwz.blog.blogback.mapper;

import com.cwz.blog.blogback.entity.Comment;
import com.cwz.blog.blogback.mapper.bean.BeanMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BeanMapper<Comment> {

}
