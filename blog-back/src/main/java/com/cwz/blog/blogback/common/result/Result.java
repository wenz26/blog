package com.cwz.blog.blogback.common.result;


import com.cwz.blog.blogback.common.constant.ResultCode;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/*
* api接口数据返回封装
*/

/*
* Serializable主要作用将类的实例持久化保存，序列化就是保存，反序列化就是读取。
* 保存也不一定保存在本地，也可以保存到远方。类一定要实现Serializable才可以
*/
public class Result implements Serializable {

    private static final long serialVersionUID = -4762928619495260423L;

    private Integer code;
    private String msg;
    private Object data;

    public Result(){
    }

    public static Result success(){
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }

    public static Result success(Object data){
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }

    public static Result error(ResultCode resultCode){
        Result result = new Result();
        result.setResultCode(resultCode);
        return result;
    }

    public static Result error(ResultCode resultCode, Object data){
        Result result = new Result();
        result.setResultCode(resultCode);
        result.setData(data);
        return result;
    }

    public void setResultCode(ResultCode code){
        this.code = code.code();
        this.msg = code.message();
    }

    public Map<String, Object> simple(){
        Map<String, Object> simple = new HashMap<>();
        this.data = simple;
        return simple;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
