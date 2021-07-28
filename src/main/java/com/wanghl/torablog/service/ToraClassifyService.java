package com.wanghl.torablog.service;

import com.wanghl.torablog.entity.ToraClassify;
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
public interface ToraClassifyService extends IService<ToraClassify> {

    List<ToraClassify> getTop4Classify();

    List<ToraClassify> getClassify();

}
