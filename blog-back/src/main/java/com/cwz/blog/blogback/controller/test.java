package com.cwz.blog.blogback.controller;

import com.cwz.blog.blogback.entity.Article;
import com.cwz.blog.blogback.mapper.ArticleMapper;
import com.cwz.blog.blogback.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;


@RestController
public class test {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleMapper articleMapper;

    @RequestMapping("/findjpa/{id}")
    public Optional<Article> test(@PathVariable("id") Integer id){
        return articleRepository.findById(id);
    }

    @GetMapping("/findmybatis/{id}")
    public Article test02(@PathVariable("id") Integer id){
        return articleMapper.findById(id);
    }
}
