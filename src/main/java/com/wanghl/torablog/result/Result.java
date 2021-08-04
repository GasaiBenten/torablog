package com.wanghl.torablog.result;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Result {

    private Integer code;
    private String msg;
    private Map<String,Object> data = new HashMap<>();

    private Result(){}

    public static Result ok(){
        Result result = new Result();
        result.setCode(20000);
        result.setMsg("ok");
        return result;
    }

    public static Result error(){
        Result result = new Result();
        result.setCode(20001);
        result.setMsg("error");
        return result;
    }

    public Result data(String key, Object val){
        this.data.put(key, val);
        return this;
    }
    public Result data(Map<String, Object> map){
        this.setData(map);
        return this;
    }
}
