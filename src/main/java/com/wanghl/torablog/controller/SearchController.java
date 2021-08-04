package com.wanghl.torablog.controller;

import com.wanghl.torablog.entity.*;
import com.wanghl.torablog.service.ToraBlogService;
import com.wanghl.torablog.service.ToraClassifyService;
import com.wanghl.torablog.service.ToraTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private ToraBlogService toraBlogService;
    @Autowired
    private ToraClassifyService toraClassifyService;
    @Autowired
    private ToraTagService toraTagService;

    @GetMapping("/search")
    public String search(SearchParam searchParam, Model model) throws IOException {
        SearchResult searchResult = toraBlogService.search(searchParam);
        List<ToraBlog> newList = toraBlogService.getNew4Blog();
        List<ToraClassify> classifyList = toraClassifyService.getClassify();
        List<ToraTag> tagList = toraTagService.getTag();
        model.addAttribute("result",searchResult);
        model.addAttribute("newList",newList);
        model.addAttribute("classifyList",classifyList);
        model.addAttribute("tagList",tagList);
        return "search";
    }
}
