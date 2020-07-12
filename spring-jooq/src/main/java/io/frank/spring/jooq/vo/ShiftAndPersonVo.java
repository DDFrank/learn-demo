package io.frank.spring.jooq.vo;

import com.delicloud.platform.v2.common.lang.bo.JsonBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author jinjunliang
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class ShiftAndPersonVo extends JsonBase {
    private Long id;
    private String startTime;
    private String endTime;
    private Integer times;
    private String totalTime;
    private List<UserVo> personList;
}
