package com.cwz.blog.blogback.mapper;

import com.cwz.blog.blogback.entity.Tag;
import com.cwz.blog.blogback.mapper.bean.BeanMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TagMapper extends BeanMapper<Tag> {

}
