package com.wanghl.torablog.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.wanghl.torablog.entity.ToraTag;
import com.wanghl.torablog.mapper.ToraTagMapper;
import com.wanghl.torablog.service.ToraTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public List<ToraTag> getTop6Tag() {
        String top6Tag = redisTemplate.opsForValue().get("Top6Tag");
        List<ToraTag> toraTags = JSON.parseObject(top6Tag, new TypeReference<List<ToraTag>>(){});
        if (toraTags==null){
            List<ToraTag> tagList = baseMapper.selectTop6Tag();
            redisTemplate.opsForValue().set("Top6Tag", JSON.toJSONString(tagList), 1, TimeUnit.HOURS);
            return tagList;
        } else {
            return toraTags;
        }
    }

    @Override
    public List<ToraTag> getTag() {
        return baseMapper.selectTag();
    }
}
