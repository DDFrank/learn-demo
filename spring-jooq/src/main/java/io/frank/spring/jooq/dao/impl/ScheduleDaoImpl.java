package io.frank.spring.jooq.dao.impl;

import com.delicloud.platform.v2.common.lang.bo.page.RespPage;
import io.frank.spring.jooq.common.constants.DBConstants;
import io.frank.spring.jooq.common.util.DateUtils;
import io.frank.spring.jooq.dao.ScheduleDao;
import io.frank.spring.jooq.dto.PageReqDto;
import io.frank.spring.jooq.dto.ScheduleShiftBo;
import io.frank.spring.jooq.dto.UserDto;
import io.frank.spring.jooq.entity.tables.records.TPatrolSystemScheduleRecord;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.frank.spring.jooq.entity.Tables.T_PATROL_SYSTEM_SCHEDULE;
import static io.frank.spring.jooq.entity.Tables.T_PATROL_SYSTEM_USER;
import static io.frank.spring.jooq.entity.tables.TPatrolSystemMappingUserShift.T_PATROL_SYSTEM_MAPPING_USER_SHIFT;
import static io.frank.spring.jooq.entity.tables.TPatrolSystemScheduleShift.T_PATROL_SYSTEM_SCHEDULE_SHIFT;

/**
 * @author jinjunliang
 **/
@Component
public class ScheduleDaoImpl implements ScheduleDao {

    @Autowired
    private DSLContext dsl;

    @Override
    public RespPage<TPatrolSystemScheduleRecord> findSchedulesNeedToShow(PageReqDto pageReqDto, Long orgId, String name) {
        Condition whereCondition = T_PATROL_SYSTEM_SCHEDULE.ORG_ID.equal(orgId)
                .and(T_PATROL_SYSTEM_SCHEDULE.STATUS.in(
                        DBConstants.Schedule.ACTIVE.getValue(), DBConstants.Schedule.INACTIVE.getValue()))
                .and(T_PATROL_SYSTEM_SCHEDULE.NAME.like("%" + name + "%"));

        long totalCount = dsl.selectCount().from(T_PATROL_SYSTEM_SCHEDULE).where(whereCondition).fetchSingle().value1();
        List<TPatrolSystemScheduleRecord> result = dsl.selectFrom(T_PATROL_SYSTEM_SCHEDULE)
                .where(whereCondition)
                .orderBy(T_PATROL_SYSTEM_SCHEDULE.UPDATE_TIME)
                .limit(pageReqDto.limit(), pageReqDto.getSize())
                .fetchStream()
                .collect(Collectors.toList());

        RespPage<TPatrolSystemScheduleRecord> pageResult = new RespPage<>();
        pageResult.setTotal(totalCount);
        pageResult.setRows(result);
        return pageResult;
    }

    @Override
    public List<ScheduleShiftBo> findAllScheduleShiftPerson(Long scheduleId) {
        Map<Long, ScheduleShiftBo> map = dsl.select(
                T_PATROL_SYSTEM_SCHEDULE_SHIFT.ID,
                T_PATROL_SYSTEM_SCHEDULE_SHIFT.START_TIME,
                T_PATROL_SYSTEM_SCHEDULE_SHIFT.END_TIME,
                T_PATROL_SYSTEM_USER.USER_NAME
        )
                .from(T_PATROL_SYSTEM_SCHEDULE_SHIFT)
                .leftJoin(T_PATROL_SYSTEM_MAPPING_USER_SHIFT)
                .on(T_PATROL_SYSTEM_SCHEDULE_SHIFT.ID.eq(T_PATROL_SYSTEM_MAPPING_USER_SHIFT.SHIFT_ID))
                .leftJoin(T_PATROL_SYSTEM_USER)
                .on(T_PATROL_SYSTEM_MAPPING_USER_SHIFT.USER_ID.eq(T_PATROL_SYSTEM_USER.ID))
                .where(
                        T_PATROL_SYSTEM_SCHEDULE_SHIFT.SCHEDULE_ID.eq(scheduleId)
                                .and(T_PATROL_SYSTEM_SCHEDULE_SHIFT.STATUS.eq(DBConstants.Status.ACTIVE.isValue()))
                                .and(T_PATROL_SYSTEM_MAPPING_USER_SHIFT.STATUS.eq(DBConstants.Status.ACTIVE.isValue()))
                )
                .orderBy(T_PATROL_SYSTEM_SCHEDULE_SHIFT.START_TIME)
                .fetchStream()
                // 将扁平的 排班-人员结构 reduce 为 排班 -> 多个人员的嵌套结构
                .collect(
                        Collectors.groupingBy(
                                (Record4<Long, LocalTime, LocalTime, String> record) -> record.getValue(T_PATROL_SYSTEM_SCHEDULE_SHIFT.ID),
                                Collectors.reducing(new ScheduleShiftBo(), record -> {
                                    ScheduleShiftBo bo = new ScheduleShiftBo();
                                    bo.setId(record.getValue(T_PATROL_SYSTEM_SCHEDULE_SHIFT.ID));
                                    bo.setStartTime(DateUtils.formatTime(record.getValue(T_PATROL_SYSTEM_SCHEDULE_SHIFT.START_TIME)));
                                    bo.setEndTime(DateUtils.formatTime(record.getValue(T_PATROL_SYSTEM_SCHEDULE_SHIFT.END_TIME)));
                                    UserDto user = new UserDto();
                                    user.setUserName(record.getValue(T_PATROL_SYSTEM_USER.USER_NAME));
                                    bo.getUserDtoList().add(user);
                                    return bo;
                                }, ScheduleShiftBo::add)
                        )
                );
        return new ArrayList<>(map.values());
    }

    @Override
    public boolean ifSameNameScheduleExist(Long orgId, String name) {
        int count = dsl.selectCount()
                .from(T_PATROL_SYSTEM_SCHEDULE)
                .where(T_PATROL_SYSTEM_SCHEDULE.ORG_ID.eq(orgId)
                        .and(T_PATROL_SYSTEM_SCHEDULE.STATUS.eq(DBConstants.Schedule.ACTIVE.getValue()))
                        .and(T_PATROL_SYSTEM_SCHEDULE.NAME.eq(name))
                )
                .fetchSingle()
                .value1();

        return count > 0;
    }
}
