package com.wanghl.torablog.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author wanghl
 * @date 2021/3/27 - 13:48
 */
@Data
public class BlogInfo {
    private Integer id;
    private String title;
    private String content;
    private String username;
    private String cover;
    private Date gmtCreate;
    private Date gmtModified;
    private String classify;
    private List<ToraTagVo> tagList;


    @Data
    public static class ToraTagVo {
        private Integer id;
        private String name;
    }
}
