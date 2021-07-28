package com.wanghl.torablog.service;

import com.wanghl.torablog.entity.ToraBlogTag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wanghl
 * @since 2021-04-12
 */
public interface ToraBlogTagService extends IService<ToraBlogTag> {

    List<ToraBlogTag> getByBlogId(int id);
}
