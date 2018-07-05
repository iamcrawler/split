package cn.crawler.split.controller;

import cn.crawler.split.domain.User;
import cn.crawler.split.utils.CurrentUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liang.liu04@hand-china.com
 * on 2018/7/4
 */
@RestController
@RequestMapping("/api/test")
public class MyTestController {


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get/currentUser")
    public void testCurrentUser(){
        User currentUser = CurrentUtil.getCurrentUser();

    }

}
