package com.cwz.blog.blogback.service.impl;

import com.cwz.blog.blogback.entity.Log;
import com.cwz.blog.blogback.mapper.LogMapper;
import com.cwz.blog.blogback.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("logService")
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;

    @Override
    public Integer saveLog(Log log) {
        return logMapper.insert(log);
    }
}
