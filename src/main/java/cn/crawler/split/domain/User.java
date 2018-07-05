package cn.crawler.split.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * Created by liang.liu04@hand-china.com
 * on 2018/6/29
 */
@Data
@TableName("ll_user")
public class User implements UserDetails {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @TableField("user_name")
    private String userName;

    private String password;

    private String phone;

    private String mail;


//    @JoinTable
    @TableField(exist = false)
    private List<Role> roles;

    public User(){

    }


    public User(User user) {
        this.id
                = user.getId();
        this.userName = user.getUsername();
        this.password =user.getPassword();
    }




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //将用户角色作为权限
        List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
        List<Role> roles = this.roles;
        for(Role role:roles){
            auths.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        return auths;
//        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
