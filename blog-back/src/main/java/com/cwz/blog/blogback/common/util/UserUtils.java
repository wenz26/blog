package com.cwz.blog.blogback.common.util;


import com.cwz.blog.blogback.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;

/*
* UserUtils
*
* -------以后修改，从session中获取user的值------
*/
public class UserUtils {

    public static String getCurrentUserId(HttpServletRequest request){

        SecurityContextImpl context =
                (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");

        Authentication authentication =  context.getAuthentication();

        if (authentication != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        }

        return null;
    }
}
