package io.frank.spring.jooq.service;

import com.delicloud.platform.v2.common.lang.bo.page.RespPage;
import io.frank.spring.jooq.dto.PageReqDto;
import io.frank.spring.jooq.dto.ScheduleShiftBo;
import io.frank.spring.jooq.vo.FormattedScheduleVo;

import java.util.List;

/**
 * @author jinjunliang
 **/
public interface ScheduleRuleService {
    /**
     *
     *
     * @param pageReqDto
     * @param orgId
     * @param name
     * @return
     */
    RespPage<FormattedScheduleVo> getAllSchedules(PageReqDto pageReqDto, Long orgId, String name);
    List<ScheduleShiftBo> getAllSchedules(Long scheduleId);
}
