package cn.crawler.split.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * Created by liang.liu04@hand-china.com
 * on 2018/7/4
 */
@TableName("ll_user_role")
@Data
public class UserRole {
    private Long id;

    @TableField("user_id")
    private Long userId;

    @TableField("role_id")
    private Long roleId;
}
