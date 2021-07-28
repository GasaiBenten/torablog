package com.wanghl.torablog.mapper;

import com.wanghl.torablog.entity.ToraBlog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wanghl
 * @since 2021-04-12
 */
public interface ToraBlogMapper extends BaseMapper<ToraBlog> {

    List<ToraBlog> selectBlogPage(@Param("current") long current,@Param("size") long size);

    @Select("select * from tora_blog where is_deleted = 0 and t_publish = 1 and t_recommend = 1 order by gmt_create desc limit 0,4")
    List<ToraBlog> selectTop4Recommend();

    @Select("select * from tora_blog where is_deleted = 0 and t_publish = 1 order by gmt_create limit 0,4")
    List<ToraBlog> selectNew4Blog();

    ToraBlog selectBlogById(@Param("id") int id);

    List<ToraBlog> selectBlogPageByClassifyId(String classifyId, long current, long size);
}
