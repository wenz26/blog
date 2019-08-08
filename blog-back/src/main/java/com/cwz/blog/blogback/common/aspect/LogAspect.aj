package com.cwz.blog.blogback.common.aspect;

import com.alibaba.fastjson.JSON;
import com.cwz.blog.blogback.common.annotation.LogAnnotation;
import com.cwz.blog.blogback.common.util.HttpContextUtils;
import com.cwz.blog.blogback.common.util.IpUtils;
import com.cwz.blog.blogback.common.util.UserUtils;
import com.cwz.blog.blogback.entity.Log;
import com.cwz.blog.blogback.entity.User;
import com.cwz.blog.blogback.service.LogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

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

    private void saveLog(ProceedingJoinPoint joinPoint, long time) throws NoSuchMethodException {
        // 获取抽象方法 (获取切入点的方法签名)
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        Log log = new Log();

        // 获取当前类的对象, getTarget():获取目标对象
        Class<?> clazz = joinPoint.getTarget().getClass();

        // 获取当前类有LogAnnotation注解的方法,获取切入点方法的LogAnnotation注解类，根据该注解类的内置对象进行下一步操作
        method = clazz.getMethod(method.getName(), method.getParameterTypes());
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
        if(log != null){
            log.setModule(logAnnotation.module());
            log.setOperation(logAnnotation.operation());
        }

        // 切入点的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        log.setModule(className + "." + methodName + "()");

        // 切入点的参数
        Object[] args = joinPoint.getArgs();
        String params = JSON.toJSONString(args);
        log.setParams(params);

        //获取request 设置IP地址
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        log.setIp(IpUtils.getIpAddr(request));

        //用户名
        User user = UserUtils.getCurrentUser();
        if(user != null){
            log.setUserid(user.getId());
            log.setNickname(user.getNickname());
        } else {
            // 常量后面跟这个一般是指类型，1L表示1是长整型，如果是1f 表示是float型
            log.setUserid(-1L);
            log.setNickname("获取用户信息为空");
        }

        log.setTime(time);
        log.setCreateDate(new Date());

        logService.saveLog(log);
    }

}
