package io.frank.spring.jooq.vo;


import com.delicloud.platform.v2.common.lang.bo.JsonBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * .Description: 用于前端展示的排班计划 Author: 金君良 Date: 2018/11/7 0007 上午 9:51
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormattedScheduleVo extends JsonBase {

    private String id;
    private String name;
    private String startDate;
    private String endDate;
    private String status;
    private String allShift;
    private List<String> allPersonList;
    private List<String> allLocationList;
    private List<String> allShiftList;
    private List<ShiftAndPersonVo> shiftTimeAndPersonList;
    private Integer ifAnyTime;

}
