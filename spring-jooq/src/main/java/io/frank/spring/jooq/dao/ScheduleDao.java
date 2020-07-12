package io.frank.spring.jooq.dao;

import com.delicloud.platform.v2.common.lang.bo.page.RespPage;
import io.frank.spring.jooq.dto.PageReqDto;
import io.frank.spring.jooq.dto.ScheduleShiftBo;
import io.frank.spring.jooq.entity.tables.records.TPatrolSystemScheduleRecord;

import java.util.List;

/**
 * @author jinjunliang
 **/
public interface ScheduleDao {
    /**
     * 查询激活和未激活的巡检排班(前端只展示这两种)
     *
     * @param pageReqDto 分页依据
     * @param orgId      组织Id
     * @param name       巡检名称
     * @return 分页的巡检排班数据
     */
    RespPage<TPatrolSystemScheduleRecord> findSchedulesNeedToShow(PageReqDto pageReqDto, Long orgId, String name);

    /**
     * 查询某个巡检任务的全部排班及其对应的员工名称
     *
     * @param scheduleId 巡检任务Id
     * @return 数据
     */
    List<ScheduleShiftBo> findAllScheduleShiftPerson(Long scheduleId);

    /**
     * 是否已存在某个名称的巡检任务
     *
     * @param orgId 组织Id
     * @param name 巡检任务名称
     * @return 是否
     */
    boolean ifSameNameScheduleExist(Long orgId, String name);
}
