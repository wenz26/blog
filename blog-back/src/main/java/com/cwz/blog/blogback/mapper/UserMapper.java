package com.cwz.blog.blogback.mapper;

import com.cwz.blog.blogback.entity.User;
import com.cwz.blog.blogback.mapper.bean.BeanMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BeanMapper<User> {

    @Select("select * from sys_user where id = #{object}")
    @Override
    User findById(@Param("object") Object object);
}
