package com.wanghl.torablog.mapper;

import com.wanghl.torablog.entity.ToraUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wanghl
 * @since 2021-04-12
 */
public interface ToraUserMapper extends BaseMapper<ToraUser> {

    void refreshSign(@Param("user") ToraUser user);
}
