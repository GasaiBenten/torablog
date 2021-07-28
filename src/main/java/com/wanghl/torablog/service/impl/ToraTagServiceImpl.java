package com.wanghl.torablog.service.impl;

import com.wanghl.torablog.entity.ToraTag;
import com.wanghl.torablog.mapper.ToraTagMapper;
import com.wanghl.torablog.service.ToraTagService;
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
public class ToraTagServiceImpl extends ServiceImpl<ToraTagMapper, ToraTag> implements ToraTagService {

    @Override
    public List<ToraTag> getTop6Tag() {
        List<ToraTag> tagList = baseMapper.selectTop6Tag();
        return tagList;
    }
}
