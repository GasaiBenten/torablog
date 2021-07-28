package com.wanghl.torablog.service.impl;

import com.wanghl.torablog.entity.ToraBlogTag;
import com.wanghl.torablog.mapper.ToraBlogTagMapper;
import com.wanghl.torablog.service.ToraBlogTagService;
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
public class ToraBlogTagServiceImpl extends ServiceImpl<ToraBlogTagMapper, ToraBlogTag> implements ToraBlogTagService {

    @Override
    public List<ToraBlogTag> getByBlogId(int id) {
        return baseMapper.selectByBlogId(id);
    }
}
