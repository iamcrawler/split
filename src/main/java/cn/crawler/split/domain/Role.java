package cn.crawler.split.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by liang.liu04@hand-china.com
 * on 2018/7/4
 */
@TableName("ll_role")
@Data
public class Role implements Serializable{


    private static final long serialVersionUID = 2973610098344131500L;
    private Long id;

    @TableField("role_name")
    private String roleName;

}
