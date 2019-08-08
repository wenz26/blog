package com.cwz.blog.blogback.common.controlleradvice;

import com.cwz.blog.blogback.common.cache.RedisManager;
import com.cwz.blog.blogback.common.result.Result;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

/*
* 清除token
* 拦截处理器
*/

/*
* 使用@ControllerAdvice注解，全局捕获异常类，只要作用在@RequestMapping上，所有的异常都会被捕获
*
* @ControllerAdvice是在类上声明的注解，其用法主要有三点：
* 1.结合方法型注解@ExceptionHandler，用于捕获Controller中抛出的指定类型的异常，
* 从而达到不同类型的异常区别处理的目的；
* 2.结合方法型注解@InitBinder，用于request中自定义参数解析方式进行注册，从而达到自定义指定格式参数的目的；
* 3.结合方法型注解@ModelAttribute，表示其标注的方法将会在目标Controller方法执行之前执行。
*/
//@ControllerAdvice

/*
* 若某类或某方法加上该注解之后，表示此方法或类不再建议使用，调用时也会出现删除线，
* 但并不代表不能用，只是说，不推荐使用，因为还有更好的方法可以调用。
*/
@Deprecated

/*
* 使用ResponseBodyAdvice拦截Controller方法默认返回参数，统一处理返回值/响应体
* 其作用是在响应体写出之前做一些处理；比如，修改返回值、加密等。
* 拦截了 接口 的返回数据，对返回数据进行封装，这样可以保持一个统一的模板格式给前端，
* 易于前端处理，同时，简化后端每个接口都需要手动构建返回格式的维护等
*/
public class ClearTokenResponseBodyAdvice implements ResponseBodyAdvice {

    private RedisManager redisManager;


    /*
     *  判断哪些需要拦截
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {

        /*
         * getGenericParameterType():获取泛型参数类型
         * 拦截当前处理请求的controller方法的返回值参数是Result类型的
         */
        return methodParameter.getGenericParameterType().equals(Result.class);
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType,
            Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        HttpServletRequest request = ((ServletServerHttpRequest)serverHttpRequest).getServletRequest();


        return null;
    }
}
