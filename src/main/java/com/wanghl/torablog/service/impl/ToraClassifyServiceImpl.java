package com.wanghl.torablog.service.impl;

import com.wanghl.torablog.entity.ToraClassify;
import com.wanghl.torablog.mapper.ToraClassifyMapper;
import com.wanghl.torablog.service.ToraClassifyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wanghl
 * @since 2021-04-12
 */
@Service
public class ToraClassifyServiceImpl extends ServiceImpl<ToraClassifyMapper, ToraClassify> implements ToraClassifyService {

    @Override
    public List<ToraClassify> getTop4Classify() {
        List<ToraClassify> classifyList = baseMapper.selectTop4Classify();
        return classifyList;
    }

    @Override
    public List<ToraClassify> getClassify() {
        return baseMapper.selectClassify();
    }
}
