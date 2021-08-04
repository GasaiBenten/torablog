package com.wanghl.torablog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanghl.torablog.entity.ToraBlog;
import com.wanghl.torablog.entity.ToraClassify;
import com.wanghl.torablog.entity.ToraTag;
import com.wanghl.torablog.service.ToraBlogService;
import com.wanghl.torablog.service.ToraClassifyService;
import com.wanghl.torablog.service.ToraTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.thymeleaf.util.StringUtils;

import java.util.List;

/**
 * @author wanghl
 * @date 2021/4/12 - 13:13
 */
@Controller
public class IndexController {

    @Autowired
    private ToraBlogService toraBlogService;

    @Autowired
    private ToraTagService toraTagService;

    @Autowired
    private ToraClassifyService toraClassifyService;

    @GetMapping({"/","/{current}"})
    public String toIndexPage(@PathVariable(required = false) String current,
                              Model model){

        if (StringUtils.isEmpty(current)){ current = "1"; }
        IPage<ToraBlog> page = toraBlogService.getBlogPage(new Page<>(Long.valueOf(current),1L));
        List<ToraTag> tagList = toraTagService.getTop6Tag();
        List<ToraClassify> classifyList = toraClassifyService.getTop4Classify();
        List<ToraBlog> recommendList = toraBlogService.getTop4Recommend();
        List<ToraBlog> newList = toraBlogService.getNew4Blog();


        model.addAttribute("tagList",tagList);
        model.addAttribute("classifyList",classifyList);
        model.addAttribute("recommendList",recommendList);
        model.addAttribute("newList",newList);
        model.addAttribute("page",page);

        return "index";
    }

    @GetMapping("/login")
    public String toLoginPage(){return "login";}

    @GetMapping("/register")
    public String toRegisterPage(){return "register";}

    @GetMapping("/about")
    public String toAboutPage(){return "about";}

}
