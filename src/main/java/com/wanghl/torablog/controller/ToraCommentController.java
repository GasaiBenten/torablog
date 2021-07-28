package com.wanghl.torablog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanghl.torablog.entity.ToraBlog;
import com.wanghl.torablog.entity.ToraComment;
import com.wanghl.torablog.service.ToraCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.swing.plaf.synth.SynthToggleButtonUI;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wanghl
 * @since 2021-04-12
 */
@Controller
public class ToraCommentController {

    @Autowired
    private ToraCommentService toraCommentService;

    //根据博客id查询评论并分页
    @GetMapping("/commentPage/{blogId}/{current}/{limit}")
    public String commentPage(@PathVariable String blogId,
                              @PathVariable long current,
                              @PathVariable long limit,
                              Model model){
        Page<ToraComment> page = new Page<>(current,limit);
        List<ToraComment> commentPage = toraCommentService.getCommentPage(blogId, page);

        QueryWrapper<ToraComment> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete",0);
        wrapper.eq("t_publish",1);
        wrapper.eq("blog_id",blogId);
        wrapper.eq("parent_id",0);
        IPage<ToraComment> iPage = toraCommentService.page(page, wrapper);
        long pageCurrent = iPage.getCurrent();
        long pagePages = iPage.getPages();
        long pageTotal = iPage.getTotal();
        System.out.println(pageCurrent);
        System.out.println(pagePages);
        System.out.println(pageTotal);

        model.addAttribute("page",page);
        model.addAttribute("pageCurrent",pageCurrent);
        model.addAttribute("pagePages",pagePages);
        model.addAttribute("pageTotal",pageTotal);
        model.addAttribute("commentPage",commentPage);
        model.addAttribute("blogId",blogId);
        return "blogdetail::commentList";
    }


    //发表评论
    @PostMapping("/addComment")
    public void addComment(ToraComment toraComment){
        Integer blogId = toraComment.getBlogId();
        toraCommentService.save(toraComment);
    }

}

