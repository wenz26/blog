### ControllerAdvice
使用@ControllerAdvice注解，全局捕获异常类，只要作用在@RequestMapping上，所有的异常都会被捕获。

@ControllerAdvice是在类上声明的注解，其用法主要有三点：
 
 - 结合方法型注解@ExceptionHandler，用于捕获Controller中抛出的指定类型的异常，
   从而达到不同类型的异常区别处理的目的；
 - 结合方法型注解@InitBinder，用于request中自定义参数解析方式进行注册，
   从而达到自定义指定格式参数的目的；
 - 结合方法型注解@ModelAttribute，表示其标注的方法将会在目标Controller方法执行之前执行。

