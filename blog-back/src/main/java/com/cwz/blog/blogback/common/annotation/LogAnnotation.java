package com.cwz.blog.blogback.common.annotation;

import java.lang.annotation.*;

/*
* 日志注解
* 声明LogAnnotation注解
*/

/*
* @Target说明了Annotation所修饰的对象范围
* @Target注解传入ElementType.METHOD参数来标明@LogAnnotation只能用于方法上
*/
@Target(ElementType.METHOD)

/*
* @Retention用来约束注解的生命周期，分别有三个值，源码级别（source），类文件级别（class）或者运行时级别（runtime）
* RUNTIME：注解信息将在运行期(JVM)也保留，因此可以通过反射机制读取注解的信息（源码、class文件和执行的时候都
* 有注解的信息），如SpringMvc中的@Controller、@Autowired、@RequestMapping等。
*
* 其实说白了就是该注解保存到运行时
*/
@Retention(RetentionPolicy.RUNTIME)

/*
* @Documented注解表明这个注释是由 javadoc记录的，在默认情况下也有类似的记录工具。 如果一个类型声
* 明被注释了文档化，它的注释成为公共API的一部分。
*/
@Documented
public @interface LogAnnotation {

    /*
    * 我们声明一个String类型的module和operation元素，其默认值为空字符，但是必须注意到对应任何元素的声明应
    * 采用方法的声明方式，同时可选择使用default提供默认值
    */
    String module() default "";

    String operation() default "";
}


/*
* //@LogAnnotation反编译后的代码
* public interface LogAnnotation extends Annotation{
*    public abstract String module();
*    public abstract String operation();
* }
*
* */
