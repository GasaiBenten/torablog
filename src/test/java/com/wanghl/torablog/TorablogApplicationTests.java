package com.wanghl.torablog;

import com.wanghl.torablog.entity.ToraBlog;
import com.wanghl.torablog.service.ToraBlogService;
import jdk.nashorn.internal.objects.NativeReferenceError;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TorablogApplicationTests {

    @Autowired
    ToraBlogService blogService;

    @Test
    void contextLoads() {
        ToraBlog blog = new ToraBlog();
        blog.setTitle("ceshi ");
        blogService.save(blog);
    }

}
