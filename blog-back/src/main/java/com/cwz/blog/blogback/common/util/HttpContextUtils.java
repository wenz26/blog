package com.cwz.blog.blogback.common.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/*
* HttpServletRequest
* request上下文工具类
*/
public class HttpContextUtils {

    public static HttpServletRequest getHttpServletRequest(){

        /*
        * RequestContextHolder顾名思义,持有上下文的Request容器,RequestContextHolder这个类,
        * 里面有两个ThreadLocal保存当前线程下的request。
        *
        * getRequestAttributes()(获取Request属性)方法,相当于直接获取ThreadLocal里面的值,这样就保证了每一次获
        * 取到的Request是该请求的request.
        *
        * 把新的RequestAttributes设置进LocalThread,实际上保存的类型为ServletRequestAttributes,这也是为
        * 什么在使用的时候可以把RequestAttributes强转为ServletRequestAttributes.
        * */
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    }
}
