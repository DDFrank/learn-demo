package io.frank.spring.jooq.dto;

import com.delicloud.platform.v2.common.lang.bo.JsonBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jinjunliang
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class UserDto extends JsonBase {
    private Long id;
    private Long userId;
    private Long orgId;
    private String userEmpno;
    private String userName;
    private String userAvatar;
    private Integer memberId;
}
