package io.frank.spring.jooq.vo;

import com.delicloud.platform.v2.common.lang.bo.JsonBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jinjunliang
 **/
@Data
@EqualsAndHashCode(callSuper = false)

public class UserVo extends JsonBase {
    private Long id;
    private Long userId;
    private Long orgId;
    private String userEmpno;
    private String userName;
    // 员工头像
    private String userAvatar;
    // 序列号
    private Integer memberId;
}
