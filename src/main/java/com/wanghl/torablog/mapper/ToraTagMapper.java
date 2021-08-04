package com.wanghl.torablog.mapper;

import com.wanghl.torablog.entity.ToraTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface ToraTagMapper extends BaseMapper<ToraTag> {

    List<ToraTag> selectTop6Tag();

    List<ToraTag> selectTag();

}
