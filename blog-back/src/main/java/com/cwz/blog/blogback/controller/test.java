package com.cwz.blog.blogback.controller;

import com.cwz.blog.blogback.common.annotation.LogAnnotation;
import com.cwz.blog.blogback.common.util.UserUtils;
import com.cwz.blog.blogback.entity.Article;
import com.cwz.blog.blogback.entity.User;
import com.cwz.blog.blogback.mapper.ArticleMapper;
import com.cwz.blog.blogback.mapper.UserMapper;
import com.cwz.blog.blogback.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class test {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LogService logService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/findArticle/{id}")
    public Article test(@PathVariable("id") Integer id){
        logger.info("该 articleMapper 类为：" + articleMapper.getClass());
        logger.info("该 logService 类为：" + logService.getClass());
        return articleMapper.findById(id);
    }

    @GetMapping("/findUser")
    public List<User> test03(){
        logger.info("该userRepository类为：" + userMapper.getClass());
        return userMapper.selectAll();
    }

    @GetMapping("/getUser")
    @LogAnnotation(module = "Test", operation = "GET")
    public User getUser(HttpServletRequest request){
        String userId = UserUtils.getCurrentUserId(request);

        if (userId != null){
            logger.info("获取的 userId 为：" + userId);

            Long userIdToLong = Long.parseLong(userId);
            logger.info("转化的 userIdToLong 为：" + userIdToLong);

            User user = userMapper.findById(userIdToLong);

            logger.info("获取的 user 对象为：" + user);
            return user;
        }
        return null;
    }
}
