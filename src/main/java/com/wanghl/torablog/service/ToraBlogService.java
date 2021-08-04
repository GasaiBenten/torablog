package com.wanghl.torablog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanghl.torablog.entity.SearchParam;
import com.wanghl.torablog.entity.SearchResult;
import com.wanghl.torablog.entity.ToraBlog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wanghl
 * @since 2021-04-12
 */
public interface ToraBlogService extends IService<ToraBlog> {

    IPage<ToraBlog> getBlogPage(Page<ToraBlog> page);

    List<ToraBlog> getTop4Recommend();

    List<ToraBlog> getNew4Blog();

    ToraBlog getBlogById(int id);

    List<ToraBlog> getBlogPageByClassifyId(Page<ToraBlog> page, String classifyId);

    SearchResult search(SearchParam searchParam) throws IOException;

}
