package com.cwz.blog.blogback.entity;

public enum UserStatus {
    normal("正常状态"), blocker("封禁状态");

    private final String info;

    private UserStatus(String info){
        this.info = info;
    }

    public String getInfo(){
        return info;
    }
}
