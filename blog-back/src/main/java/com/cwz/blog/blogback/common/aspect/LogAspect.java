package com.cwz.blog.blogback.common.aspect;

import com.alibaba.fastjson.JSON;
import com.cwz.blog.blogback.common.annotation.LogAnnotation;
import com.cwz.blog.blogback.common.util.HttpContextUtils;
import com.cwz.blog.blogback.common.util.IpUtils;
import com.cwz.blog.blogback.common.util.UserUtils;
import com.cwz.blog.blogback.entity.Log;
import com.cwz.blog.blogback.entity.User;
import com.cwz.blog.blogback.mapper.UserMapper;
import com.cwz.blog.blogback.service.LogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/*
 * 日志切面
 */

//即使用jdk默认代理模式，AspectJ代理模式是CGLIB代理模式
//如果目标对象实现了接口，默认情况下会采用JDK的动态代理实现AOP
//如果目标对象实现了接口，可以强制使用CGLIB实现AOP (此例子我们就是强制使用cglib实现aop)
//如果目标对象没有实现了接口，必须采用CGLIB库，spring会自动在JDK动态代理和CGLIB之间转换


/*
 * @Aspect:作用是把当前类标识为一个切面供容器读取
 */
@Aspect

//开启AspectJ 自动代理模式,如果不填proxyTargetClass=true，默认为false，
@EnableAspectJAutoProxy(proxyTargetClass = true)

/*
 * 把普通pojo实例化到spring容器中，相当于配置文件中的<bean id="" class=""/>
 */
@Component
public class LogAspect {

    @Autowired
    private LogService logService;

    @Autowired
    private UserMapper userMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /*
     * @Pointcut：Pointcut是植入Advice的触发条件。每个Pointcut的定义包括2部分，
     * 一是表达式，二是方法签名。方法签名必须是 public及void型。可以将Pointcut中的
     * 方法看作是一个被Advice引用的助记符，因为表达式不直观，因此我们可以通过方法签名
     * 的方式为 此表达式命名。因此Pointcut中的方法只需要方法签名，而不需要在方法体内编写实际代码。
     *
     * 在所有标记了@LogAnnotation的方法中切入
     */
    @Pointcut("@annotation(com.cwz.blog.blogback.common.annotation.LogAnnotation)")
    //@Pointcut("execution(public * com.cwz.blog.blogback.controller.*.*(..))")
    public void logPointCut(){}

    /*
     * @Around：环绕增强，相当于MethodInterceptor
     * Proceedingjoinpoint(程序连接点)继承了JoinPoint。是在JoinPoint的基础上暴露出 proceed 这个方法。
     * proceed很重要，这个是aop代理链执行的方法。
     * 环绕通知=前置+目标方法执行+后置通知，proceed方法就是用于启动目标方法执行的。
     */
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable{
        long beginTime = System.currentTimeMillis();
        logger.info("日志记录开始" + beginTime);

        // 执行方法
        Object result = point.proceed();

        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        logger.info("日志记录结束" + time);

        //保存日志
        saveLog(point, time);
        return result;
    }

    private void saveLog(ProceedingJoinPoint joinPoint, long time) throws NoSuchMethodException {
        // 获取到当前的request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();

        // 获取抽象方法 (获取切入点的方法签名)
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        logger.info("当前的方法签名为：" + signature);

        Method method = signature.getMethod();

        Log log = new Log();

        // 获取当前类的对象, getTarget():获取目标对象
        Class<?> clazz = joinPoint.getTarget().getClass();

        // 获取当前类有LogAnnotation注解的方法,获取切入点方法的LogAnnotation注解类，根据该注解类的内置对象进行下一步操作
        method = clazz.getMethod(method.getName(), method.getParameterTypes());
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
        logger.info("当前的方法注解为：" + logAnnotation);

        if(log != null){
            log.setModule(logAnnotation.module());
            log.setOperation(logAnnotation.operation());
            logger.info("当前的方法注解的模式和操作为：" + logAnnotation.module() + " _ " + logAnnotation.operation());
        }

        // 切入点的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        log.setMethod(className + "." + methodName + "()");

        // 切入点的参数
        Object[] args = joinPoint.getArgs();
        Object[] arguments = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof ServletRequest || args[i] instanceof ServletResponse
            || args[i] instanceof MultipartFile){

                /*
                * ServletRequest不能序列化，从入参里排除，否则报异常：java.lang.IllegalStateException:
                * It is illegal to call this method if the current request is not in asynchronous
                * mode (i.e. isAsyncStarted() returns false)
                *
                * ServletResponse不能序列化 从入参里排除，否则报异常：java.lang.IllegalStateException:
                * getOutputStream() has already been called for this response
                * */
                continue;
            }
            arguments[i] = args[i];
        }

        String params = JSON.toJSONString(arguments);
        logger.info("当前方法的参数为：" + params);
        log.setParams(params);

        //获取request 设置IP地址
        log.setIp(IpUtils.getIpAddr(request));
        logger.info("当前方法(客户端)的ip地址为：" + IpUtils.getIpAddr(request));

        //用户名
        String userId = UserUtils.getCurrentUserId(request);
        if(userId != null){
            Long userIdToLong = Long.parseLong(userId);
            logger.info("转化的userIdToLong为：" + userIdToLong);

            User user = userMapper.findById(userIdToLong);

            log.setUserid(user.getId());
            log.setNickname(user.getNickname());
        } else {
            // 常量后面跟这个一般是指类型，1L表示1是长整型，如果是1f 表示是float型
            log.setUserid(-1L);
            log.setNickname("获取用户信息为空");
        }

        log.setTime(time);
        log.setCreateDate(new Date());

        //logger.info("获得的log对象为：" + log);

        Integer logId = logService.saveLog(log);
        logger.info("获得的logId为：" + logId);
    }

}
