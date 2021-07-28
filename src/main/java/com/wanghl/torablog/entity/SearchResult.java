package com.wanghl.torablog.entity;

import lombok.Data;

import java.util.List;

@Data
public class SearchResult {

    private List<BlogInfo> blogInfoList;
    private Integer currentPage;
    private Integer totalPage;
    private Integer pageSize;
    private Integer totalCount;

}
