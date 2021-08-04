package com.wanghl.torablog.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.wanghl.torablog.entity.ToraClassify;
import com.wanghl.torablog.mapper.ToraClassifyMapper;
import com.wanghl.torablog.service.ToraClassifyService;
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
public class ToraClassifyServiceImpl extends ServiceImpl<ToraClassifyMapper, ToraClassify> implements ToraClassifyService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public List<ToraClassify> getTop4Classify() {
        String top4Classify = redisTemplate.opsForValue().get("Top4Classify");
        List<ToraClassify> toraClassifies = JSON.parseObject(top4Classify, new TypeReference<List<ToraClassify>>() {});
        if (toraClassifies==null){
            List<ToraClassify> classifyList = baseMapper.selectTop4Classify();
            redisTemplate.opsForValue().set("Top4Classify",JSON.toJSONString(classifyList),1, TimeUnit.HOURS);
            return classifyList;
        } else {
            return toraClassifies;
        }
    }

    @Override
    public List<ToraClassify> getClassify() {
        return baseMapper.selectClassify();
    }
}
