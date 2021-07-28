package com.wanghl.torablog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanghl.torablog.entity.ToraBlog;
import com.wanghl.torablog.entity.ToraClassify;
import com.wanghl.torablog.service.ToraBlogService;
import com.wanghl.torablog.service.ToraClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wanghl
 * @since 2021-04-12
 */
@Controller
public class ToraClassifyController {


    @Autowired
    private ToraClassifyService toraClassifyService;
    @Autowired
    private ToraBlogService toraBlogService;

    @GetMapping("/classify")
    public String getClassify(Model model){
        List<ToraClassify> classifyList = toraClassifyService.getClassify();
        Page<ToraBlog> page = new Page<>(1,5);
        List<ToraBlog> blogList = toraBlogService.getBlogPage(page);

        QueryWrapper<ToraBlog> wrapper = new QueryWrapper<>();
        wrapper.eq("is_deleted",0);
        wrapper.eq("t_publish",1);
        toraBlogService.page(page,wrapper);

        model.addAttribute("classifyList",classifyList);
        model.addAttribute("blogList",blogList);
        model.addAttribute("classifyId",0);
        model.addAttribute("page",page);
        return "classify";
    }

    @GetMapping("/classify/{current}")
    public String getClassifyPage(@PathVariable long current,
                                  Model model){
        List<ToraClassify> classifyList = toraClassifyService.getClassify();
        Page<ToraBlog> page = new Page<>(current,5);
        List<ToraBlog> blogList = toraBlogService.getBlogPage(page);

        QueryWrapper<ToraBlog> wrapper = new QueryWrapper<>();
        wrapper.eq("is_deleted",0);
        wrapper.eq("t_publish",1);
        toraBlogService.page(page,wrapper);

        model.addAttribute("classifyList",classifyList);
        model.addAttribute("blogList",blogList);
        model.addAttribute("classifyId",0);
        model.addAttribute("page",page);
        return "classify";
    }

    @GetMapping("/classify/{classifyId}/{current}")
    public String getClassifyById(@PathVariable String classifyId,
                                  @PathVariable long current,
                                  Model model){
        List<ToraClassify> classifyList = toraClassifyService.getClassify();
        Page<ToraBlog> page = new Page<>(current,5);
        List<ToraBlog> blogList = toraBlogService.getBlogPageByClassifyId(page,classifyId);
        ToraClassify classify = toraClassifyService.getById(classifyId);
        System.out.println("classify======== "+ classify.getName());
        QueryWrapper<ToraBlog> wrapper = new QueryWrapper<>();
        wrapper.eq("is_deleted",0);
        wrapper.eq("t_publish",1);
        wrapper.eq("classify",classify.getName());
        toraBlogService.page(page,wrapper);
        System.out.println("getTotal "+page.getTotal());
        System.out.println("getSize "+page.getSize());
        System.out.println("getCurrent "+page.getCurrent());
        System.out.println("getPages "+page.getPages());

        model.addAttribute("classifyList",classifyList);
        model.addAttribute("blogList",blogList);
        model.addAttribute("classifyId",Integer.parseInt(classifyId));
        model.addAttribute("page",page);
        return "classify";
    }
}

