package com.cwz.blog.blogback.mapper;

import com.cwz.blog.blogback.entity.Log;
import com.cwz.blog.blogback.mapper.bean.BeanMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogMapper extends BeanMapper<Log> {

}
