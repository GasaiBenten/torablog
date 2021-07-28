package com.wanghl.torablog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanghl.torablog.entity.ToraComment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wanghl
 * @since 2021-04-12
 */
public interface ToraCommentService extends IService<ToraComment> {

    List<ToraComment> getCommentPage(String blogId, Page<ToraComment> page);
}
