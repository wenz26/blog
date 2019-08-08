package com.cwz.blog.blogback.oauth;


import org.springframework.web.server.session.DefaultWebSessionManager;

/**
* 从请求头获取token
*
* token主要有两个作用：①：防止表单重复提交(防止表单重复提交一般还是使用前后端都限制的方式，
* 比如：在前端点击提交之后，将按钮置为灰色，不可再次点击，然后客户端和服务端的token各自独立
* 存储，客户端存储在Cookie或者Form的隐藏域（放在Form隐藏域中的时候，需要每个表单）中，
* 服务端存储在Session（单机系统中可以使用）或者其他缓存系统（分布式系统可以使用）中。)
* ②：用来作身份验证。
*
* 当用户第一次登录后，服务器生成一个token并将此token返回给客户端，以后客户端只需带
* 上这个token前来请求数据即可，无需再次带上用户名和密码。
*/

/*
* 以后修改
* ------- ------------
*
* */
public class OAuthSessionManager {

    public static final String OAUTH_TOKEN = "Oauth-Token";

    /* 引用的会话ID源 = 无状态请求*/
    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

    public OAuthSessionManager(){
        super();
    }
}
