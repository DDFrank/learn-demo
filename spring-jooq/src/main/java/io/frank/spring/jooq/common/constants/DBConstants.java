package io.frank.spring.jooq.common.constants;

/**
 * .Description: 存储数据库字段相关的常量 Author: 金君良 Date: 2018/11/8 0008 上午 10:30
 */
public enum DBConstants {
    /*通用主键字段名*/
    GENERAL_PRIMARY_KEY("id"),
    /*通用分割字符串数组的符号*/
    SPLIT_REGEX_COMMA(","),
    /*时间之间的分隔符*/
    SPLIT_REGEX_HYPHEN("-"),
    /*用于，之外的第二分割符选择，比如巡检排班中的 all_shift_times 字符串*/
    SPLIT_REGEX_VERTICAL(";"),
    /*检查内容单选多选选项的分隔符, 使用不易输入的希腊字母分隔*/
    SPLIT_REGEX_OPTIONS("α"),
    /*检查内容其他选项占位符*/
    PLACE_HOLDER_OTHER("γ"),
    /*小时分钟*/
    HH_MM("HH:mm");
    private String value;

    DBConstants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /*
     * 通用的状态类枚举
     * */
    public enum Status {
        ACTIVE(true),
        INACTIVE(false);

        private boolean value;

        Status(boolean value) {
            this.value = value;
        }

        public boolean isValue() {
            return value;
        }

    }

    /*
     * 巡检排班的类型
     * */
    public enum ScheduleType {
        /*普通*/
        NORMAL(1),
        /*不限时间，按周巡检*/
        NO_TIME_LIMIT_WEEK(2),
        /*不限时间，按月巡检*/
        NO_TIME_LIMIT_MONTH(3);
        private Integer value;

        ScheduleType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /*
     * 是否替人巡检
     * */
    public enum ShiftInstead {
        /*没有替人巡检*/
        NOT_INSTEAD(false),
        /*已经替人巡检*/
        IS_INSTRAD(true);
        private Boolean value;

        ShiftInstead(Boolean value) {
            this.value = value;
        }

        public Boolean getValue() {
            return value;
        }
    }

    /*
     * 巡检排班纪录的状态
     * */
    public enum ScheduleRecord {
        /*未完成*/
        UNCOMPLETED(0),
        /*已完成*/
        COMPLETED(1),
        /*可以开始*/
        COULD_START(2),
        /*执行中*/
        IN_EXECUTION(3),
        /*超时结束*/
        EXPIRED(4),
        /*异常*/
        EXCEPTION(5),

        CANTSTART(7);
        private Integer value;

        ScheduleRecord(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /*
     * 巡检排班地点纪录的状态纪录
     * */
    public enum ShiftRecord {
        /*未开始*/
        NOT_START(0),
        /*已完成*/
        COMPLETE(1),
        /*执行中*/
        IN_EXECUTION(2),
        /*失败*/
        FAILED(3),
        /*异常*/
        EXCEPTION(4);
        private Integer value;

        ShiftRecord(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

    }

    /*
     * 表示星期的数字与逻辑名对应
     * */
    public enum WeekDays {
        MONDAY("1", "周一"),
        TUESDAY("2", "周二"),
        WEDNESDAY("3", "周三"),
        THURSDAY("4", "周四"),
        FRIDAY("5", "周五"),
        SATURDAY("6", "周六"),
        SUNDAY("7", "周日"),
        EVERYDAY("1234567", "每天");
        private String weekDayNumber;
        private String weekDayName;

        WeekDays(String weekDayNumber, String weekDayName) {
            this.weekDayNumber = weekDayNumber;
            this.weekDayName = weekDayName;
        }

        public String getWeekDayNumber() {
            return weekDayNumber;
        }


        public String getWeekDayName() {
            return weekDayName;
        }

    }

    /*
     * 巡检排班实体的状态枚举
     * */
    public enum Schedule {
        /*巡检排班_激活*/
        ACTIVE(1),
        /*巡检排班_未激活*/
        INACTIVE(2),
        /*巡检排班_已删除*/
        DELETED(3),
        /*初始状态， 专用于新建的状态，标记为该状态的数据除非激活否则永远不应该被查询*/
        INITIAL(4);
        private Integer value;

        Schedule(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

    }

    /*
     * 检查内容的类型枚举
     * */
    public enum CheckContentType {
        /*单选*/
        SINGLE_CHECK(1),
        /*多选*/
        MULTI_CHECK(2),
        /*填空*/
        TEXTAREA(3),
        /*图片*/
        IMAGE(4),
        /*异常*/
        EXCEPTION(5),
        ;
        private Integer value;

        CheckContentType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 检查内容和检查内容记录的子项，同时适用于AnswerContent 和 QuestionContent 表的type字段
     */
    public enum CheckContentItem {
        /*单选*/
        SINGLE_CHECK(1),
        /*多选*/
        MULTI_CHECK(2),
        /*填空*/
        TEXTAREA(3),
        /*图片*/
        IMAGE(4),
        /*异常*/
        EXCEPTION(5),
        /*其它*/
        OTHER(6),
        /*异常反馈的图片*/
        EXCEPTION_IMAGE(7)
        ;
        private Integer value;

        CheckContentItem(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /*
     * 用户的是否是管理员
     * */
    public enum UserAdmin {
        /*是管理员*/
        ADMIN(true),
        /*不是管理员*/
        NORMAL_USER(false);
        private Boolean value;

        UserAdmin(Boolean value) {
            this.value = value;
        }

        public Boolean getValue() {
            return value;
        }
    }

    /*
     * 设备的状态
     * */
    public enum Device {
        /*已解绑*/
        UN_BIND(0),
        /*已绑定*/
        BIND(1),
        /*未绑定*/
        BIND_RELIEVE(2);
        private Integer status;

        Device(Integer status) {
            this.status = status;
        }

        public Integer getStatus() {
            return status;
        }
    }

}
