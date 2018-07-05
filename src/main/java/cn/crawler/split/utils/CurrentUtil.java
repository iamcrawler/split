package cn.crawler.split.utils;

import cn.crawler.split.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by liang.liu04@hand-china.com
 * on 2018/7/4
 */
public class CurrentUtil {

    public static User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if(principal instanceof  User){
            User user = (User)principal;
            return user;
        }else {
            throw new RuntimeException("没有找到用户");
        }
    }

}
