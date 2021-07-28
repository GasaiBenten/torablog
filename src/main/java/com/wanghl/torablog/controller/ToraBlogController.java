package com.wanghl.torablog.controller;


import com.wanghl.torablog.entity.ToraBlog;
import com.wanghl.torablog.entity.ToraBlogTag;
import com.wanghl.torablog.service.ToraBlogService;
import com.wanghl.torablog.service.ToraBlogTagService;
import com.wanghl.torablog.service.ToraCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wanghl
 * @since 2021-04-12
 */
@Controller
public class ToraBlogController {

    @Autowired
    private ToraBlogService toraBlogService;

    @Autowired
    private ToraCommentService commentService;

    @Autowired
    private ToraBlogTagService toraBlogTagService;

    //根据id查询博客
    @GetMapping("/blog/{id}")
    public String getBlogById(@PathVariable int id,
                              Model model){
        ToraBlog toraBlog = toraBlogService.getBlogById(id);
        List<ToraBlogTag> blogTagList = toraBlogTagService.getByBlogId(id);
        model.addAttribute("toraBlog",toraBlog);
        model.addAttribute("blogTagList",blogTagList);
        return "blogdetail";
    }

}

