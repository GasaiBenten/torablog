package com.wanghl.torablog.service;

import com.wanghl.torablog.entity.ToraTag;
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
public interface ToraTagService extends IService<ToraTag> {

    List<ToraTag> getTop6Tag();

    List<ToraTag> getTag();
}
