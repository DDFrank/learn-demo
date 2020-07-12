package io.frank.spring.jooq.service.impl;

import com.delicloud.platform.v2.common.lang.bo.page.RespPage;
import io.frank.spring.jooq.common.util.DateUtils;
import io.frank.spring.jooq.dao.ScheduleDao;
import io.frank.spring.jooq.dto.PageReqDto;
import io.frank.spring.jooq.dto.ScheduleShiftBo;
import io.frank.spring.jooq.entity.tables.records.TPatrolSystemScheduleRecord;
import io.frank.spring.jooq.service.ScheduleRuleService;
import io.frank.spring.jooq.vo.FormattedScheduleVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static io.frank.spring.jooq.entity.tables.TPatrolSystemSchedule.T_PATROL_SYSTEM_SCHEDULE;

/**
 * @author jinjunliang
 **/
@Service
public class ScheduleRuleServiceImpl implements ScheduleRuleService {

    private ScheduleDao scheduleDao;

    public ScheduleRuleServiceImpl(ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
    }

    @Override
    public RespPage<FormattedScheduleVo> getAllSchedules(PageReqDto pageReqDto, Long orgId, String name) {
        RespPage<TPatrolSystemScheduleRecord> page = scheduleDao.findSchedulesNeedToShow(pageReqDto, orgId, name);
        List<FormattedScheduleVo> list = page.getRows()
                .stream()
                .map(record -> {
                    FormattedScheduleVo vo = new FormattedScheduleVo();
                    vo.setId(String.valueOf(record.getValue(T_PATROL_SYSTEM_SCHEDULE.ID)));
                    vo.setStartDate(DateUtils.formatDate(record.getValue(T_PATROL_SYSTEM_SCHEDULE.START_DATE)));
                    vo.setEndDate(DateUtils.formatDate(record.getValue(T_PATROL_SYSTEM_SCHEDULE.END_DATE)));
                    vo.setIfAnyTime(record.getValue(T_PATROL_SYSTEM_SCHEDULE.IF_ANY_TIME));
                    vo.setName(record.getValue(T_PATROL_SYSTEM_SCHEDULE.NAME));
                    vo.setStatus(String.valueOf(record.getValue(T_PATROL_SYSTEM_SCHEDULE.STATUS)));
                    vo.setAllShift(String.valueOf(record.getValue(T_PATROL_SYSTEM_SCHEDULE.ALL_SHIFT_DAYS)));
                    return vo;
                })
                .collect(Collectors.toList());
        RespPage<FormattedScheduleVo> resultPage = new RespPage<>();
        resultPage.setRows(list);
        resultPage.setTotal(page.getTotal());
        resultPage.setSize(page.getSize());
        resultPage.setPage(page.getPage());
        return resultPage;
    }

    @Override
    public List<ScheduleShiftBo> getAllSchedules(Long scheduleId) {
        return scheduleDao.findAllScheduleShiftPerson(scheduleId);
    }
}
