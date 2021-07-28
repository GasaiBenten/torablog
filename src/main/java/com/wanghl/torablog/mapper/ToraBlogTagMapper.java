package com.wanghl.torablog.mapper;

import com.wanghl.torablog.entity.ToraBlogTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wanghl
 * @since 2021-04-12
 */
public interface ToraBlogTagMapper extends BaseMapper<ToraBlogTag> {

    List<ToraBlogTag> selectByBlogId(@Param("id") int id);
}
