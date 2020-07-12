package io.frank.spring.jooq.controller;

import com.delicloud.platform.v2.common.lang.bo.RespBase;
import com.delicloud.platform.v2.common.lang.bo.page.RespPage;
import io.frank.spring.jooq.dto.PageReqDto;
import io.frank.spring.jooq.dto.ScheduleShiftBo;
import io.frank.spring.jooq.service.ScheduleRuleService;
import io.frank.spring.jooq.vo.FormattedScheduleVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author jinjunliang
 **/
@RestController
@RequestMapping("/api/schedule")
public class ScheduleCmdController {
    private ScheduleRuleService ruleService;

    public ScheduleCmdController(ScheduleRuleService ruleService) {
        this.ruleService = ruleService;
    }

    @GetMapping("1")
    public RespBase<RespPage<FormattedScheduleVo>> index(@RequestParam(required = false, defaultValue = "0") int page,
                                                         @RequestParam(required = false, defaultValue = "10") int size) {
        PageReqDto reqDto = new PageReqDto();
        reqDto.setPage(page);
        reqDto.setSize(size);

        return new RespBase<>(ruleService.getAllSchedules(reqDto, 373798621794426880L, ""));
    }

    @GetMapping("2")
    public RespBase<List<ScheduleShiftBo>> index1() {
        return new RespBase<>(ruleService.getAllSchedules(652550842733297664L));
    }
}
