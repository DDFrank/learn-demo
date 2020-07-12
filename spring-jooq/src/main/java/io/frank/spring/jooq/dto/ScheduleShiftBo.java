package io.frank.spring.jooq.dto;

import com.delicloud.platform.v2.common.lang.bo.JsonBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jinjunliang
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class ScheduleShiftBo extends JsonBase {
    private Long id;
    private String startTime;
    private String endTime;
    private Integer times;
    private String totalTime;
    private List<UserDto> userDtoList = new ArrayList<>();

    /**
     * 如何聚合两个实体
     * @param other 另外的实体
     * @return 聚合后的结果
     */
    public ScheduleShiftBo add(ScheduleShiftBo other) {
        this.id = other.getId();
        this.startTime = other.getStartTime();
        this.endTime = other.getEndTime();
        this.times = other.getTimes();
        this.totalTime = other.getTotalTime();
        userDtoList.addAll(other.getUserDtoList());
        return this;
    }
}
