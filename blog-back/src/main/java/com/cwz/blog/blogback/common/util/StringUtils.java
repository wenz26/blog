package com.cwz.blog.blogback.common.util;

public class StringUtils {

    public static boolean isEmpty(String value){
        if(value == null){
            return true;
        }
        return value.isEmpty();
    }
}
