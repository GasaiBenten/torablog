package com.wanghl.torablog.entity;

import io.swagger.models.auth.In;
import lombok.Data;

import java.util.List;

@Data
public class SearchParam {

    private String keyword;
    private String classify;
    private List<String> tagList;
    private Integer currentPage = 1;

    @Data
    public static class ToraTagVo {
        private Integer id;
        private String name;
    }
}
