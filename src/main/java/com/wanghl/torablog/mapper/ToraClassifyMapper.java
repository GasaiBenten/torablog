package com.wanghl.torablog.mapper;

import com.wanghl.torablog.entity.ToraClassify;
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
public interface ToraClassifyMapper extends BaseMapper<ToraClassify> {


    List<ToraClassify> selectTop4Classify();

    List<ToraClassify> selectClassify();

}
