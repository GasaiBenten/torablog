package com.wanghl.torablog.controller;

import com.wanghl.torablog.entity.SearchParam;
import com.wanghl.torablog.entity.SearchResult;
import com.wanghl.torablog.service.ToraBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class SearchController {

    @Autowired
    private ToraBlogService toraBlogService;

    @GetMapping("/search")
    public String search(SearchParam searchParam, Model model) throws IOException {
        SearchResult searchResult = toraBlogService.search(searchParam);
        model.addAttribute("result",searchResult);
        return "search";
    }
}
