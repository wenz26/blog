package com.cwz.blog.blogback.security.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.info("表单登录用户名：" + username);

        return buildUser(username);
    }

    private UserDetails buildUser(String userId) {
        // 根据用户名查找用户信息
        // 根据查找到的用户信息判断用户是否被冻结

        // 密码加密
        String password = passwordEncoder.encode("123456");

        // User(username, 密码, 可用, 没过期, 密码没过期, 没被锁定, 集合(用户的权限集合))
        return new User(userId, password, true, true,
                true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"));
    }
}
