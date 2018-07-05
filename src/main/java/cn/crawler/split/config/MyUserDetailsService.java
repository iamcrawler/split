package cn.crawler.split.config;

import cn.crawler.split.domain.Role;
import cn.crawler.split.domain.User;
import cn.crawler.split.domain.UserRole;
import cn.crawler.split.mapper.RoleMapper;
import cn.crawler.split.mapper.UserMapper;
import cn.crawler.split.mapper.UserRoleMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by liang.liu04@hand-china.com
 * on 2018/7/4
 */
@Component
public class MyUserDetailsService implements UserDetailsService {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMapper roleMapper;



    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

            // 通过用户名或邮箱获取用户信息
            User account = userMapper.selectList(
                    new EntityWrapper<User>()
                            .eq("user_name",name)
                            .or()
                            .eq("mail",name))
                    .get(0) ;
            if(account!=null){
                List<UserRole> userRoleList = userRoleMapper.selectList(
                        new EntityWrapper<UserRole>()
                                .eq("user_id", account.getId())
                );
                List<Role> roles = new ArrayList<>();
                if(!CollectionUtils.isEmpty(userRoleList)){
                     roles = roleMapper.selectList(
                            new EntityWrapper<Role>()
                                    .in("id", userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toSet()))
                    );
                }
                account.setRoles(roles);
                return account;
            } else {
                throw new UsernameNotFoundException("用户[" + name + "]不存在");
            }

    }
}
