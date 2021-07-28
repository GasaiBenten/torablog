package com.wanghl.torablog.mapper;

import com.wanghl.torablog.entity.ToraComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wanghl
 * @since 2021-04-12
 */
public interface ToraCommentMapper extends BaseMapper<ToraComment> {

    List<ToraComment> selectByBlogId(@Param("blogId") String blogId,
                                     @Param("current") long current,
                                     @Param("size") long size);

    List<ToraComment> selectReplyByBlogId(String blogId);
}
