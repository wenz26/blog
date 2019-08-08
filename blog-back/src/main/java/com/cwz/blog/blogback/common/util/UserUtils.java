package com.cwz.blog.blogback.common.util;


import com.cwz.blog.blogback.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

/*
* UserUtils
*
* -------以后修改，从session中获取user的值------
*/
public class UserUtils {

    public static User getCurrentUser(){
        User user = new User();
        return user;
    }
}
