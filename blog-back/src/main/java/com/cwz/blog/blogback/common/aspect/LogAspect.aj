package com.cwz.blog.blogback.common.aspect;

import com.cwz.blog.blogback.service.LogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
* 日志切面
*/

/*
* @Aspect:作用是把当前类标识为一个切面供容器读取
*/
@Aspect

/*
* 把普通pojo实例化到spring容器中，相当于配置文件中的<bean id="" class=""/>
*/
@Component
public aspect LogAspect {

    @Autowired
    private LogService logService;

    /*
    * @Pointcut：Pointcut是植入Advice的触发条件。每个Pointcut的定义包括2部分，
    * 一是表达式，二是方法签名。方法签名必须是 public及void型。可以将Pointcut中的
    * 方法看作是一个被Advice引用的助记符，因为表达式不直观，因此我们可以通过方法签名
    * 的方式为 此表达式命名。因此Pointcut中的方法只需要方法签名，而不需要在方法体内编写实际代码。
    *
    * 在所有标记了@LogAnnotation的方法中切入
    */
    @Pointcut("@annotation(com.cwz.blog.blogback.common.annotation.LogAnnotation)")
    public void logPointCut(){}

    /*
    * @Around：环绕增强，相当于MethodInterceptor
    * Proceedingjoinpoint(程序连接点)继承了JoinPoint。是在JoinPoint的基础上暴露出 proceed 这个方法。
    * proceed很重要，这个是aop代理链执行的方法。
    * 环绕通知=前置+目标方法执行+后置通知，proceed方法就是用于启动目标方法执行的。
    */
    @Around("logPointCut()")
    public Object logAround(ProceedingJoinPoint point) throws Throwable{
        long beginTime = System.currentTimeMillis();

        // 执行方法
        Object result = point.proceed();

        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        //保存日志
        saveLog(point, time);
        return result;
    }

    private void saveLog(ProceedingJoinPoint joinPoint, long time){

    }

}
