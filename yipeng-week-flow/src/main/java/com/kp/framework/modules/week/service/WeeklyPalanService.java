package com.kp.framework.modules.week.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.kp.framework.constant.ReturnFinishedMessageConstant;
import com.kp.framework.entity.bo.PageBO;
import com.kp.framework.enums.DeleteFalgEnum;
import com.kp.framework.exception.KPServiceException;
import com.kp.framework.microservices.auth.po.UserFeignPO;
import com.kp.framework.microservices.auth.util.FeignAuthUtil;
import com.kp.framework.modules.monthly.enums.MonthlyReportStatusEnum;
import com.kp.framework.modules.monthly.mapper.MonthlyReportMapper;
import com.kp.framework.modules.monthly.po.MonthlyReportPO;
import com.kp.framework.modules.user.po.customer.LoginUserBO;
import com.kp.framework.modules.week.enums.WeeklyPalanStatusEnum;
import com.kp.framework.modules.week.mapper.WeeklyPalanMapper;
import com.kp.framework.modules.week.mapper.customer.WeeklyPalanCustomerMapper;
import com.kp.framework.modules.week.po.WeeklyPalanPO;
import com.kp.framework.modules.week.po.customer.WeeklyPalanCustomerCustomerPO;
import com.kp.framework.modules.week.po.customer.WeeklyPalanListCustomerPO;
import com.kp.framework.modules.week.po.customer.WeellyTaskSummaryCustomerPO;
import com.kp.framework.modules.week.po.param.WeeklyPalanEditParamPO;
import com.kp.framework.modules.week.po.param.WeeklyPalanListParamPO;
import com.kp.framework.modules.week.util.WeeklyUtil;
import com.kp.framework.utils.kptool.KPJsonUtil;
import com.kp.framework.utils.kptool.KPLocalDateUtil;
import com.kp.framework.utils.kptool.KPStringUtil;
import com.kp.framework.utils.kptool.KPVerifyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author lipeng
 * @Description 周计划表 服务实现类
 * @Date 2025-09-20 22:11:23
 **/
@Service
public class WeeklyPalanService extends ServiceImpl<WeeklyPalanMapper, WeeklyPalanPO> {

    @Autowired
    private MonthlyReportMapper monthlyReportMapper;

    @Autowired
    private WeeklyPalanCustomerMapper weeklyPalanCustomerMapper;

    @Autowired
    private WeeklyUtil weeklyUtil;

    @Autowired
    private FeignAuthUtil feignAuthUtil;


//    /**
//     * @Author lipeng
//     * @Description 查询周计划列表
//     * @Date 2025-09-20 22:11:23
//     * @param weeklyPalanListParamPO
//     * @return java.util.List<WeeklyPalanPO>
//    **/
//    public List<WeeklyPalanPO> queryPageList(WeeklyPalanListParamPO weeklyPalanListParamPO){
//        //搜索条件
//        LambdaQueryWrapper<WeeklyPalanPO> queryWrapper = Wrappers.lambdaQuery(WeeklyPalanPO.class)
//                .like(KPStringUtil.isNotEmpty(weeklyPalanListParamPO.getWeeklyId()), WeeklyPalanPO::getWeeklyId, weeklyPalanListParamPO.getWeeklyId())
//                .like(KPStringUtil.isNotEmpty(weeklyPalanListParamPO.getMonthlyId()), WeeklyPalanPO::getMonthlyId, weeklyPalanListParamPO.getMonthlyId())
//                .like(KPStringUtil.isNotEmpty(weeklyPalanListParamPO.getTaskTitle()), WeeklyPalanPO::getTaskTitle, weeklyPalanListParamPO.getTaskTitle())
//                .like(KPStringUtil.isNotEmpty(weeklyPalanListParamPO.getTaskDescribe()), WeeklyPalanPO::getTaskDescribe, weeklyPalanListParamPO.getTaskDescribe())
//                .like(KPStringUtil.isNotEmpty(weeklyPalanListParamPO.getTaskUserId()), WeeklyPalanPO::getTaskUserId, weeklyPalanListParamPO.getTaskUserId())
//                .eq(KPStringUtil.isNotEmpty(weeklyPalanListParamPO.getTaskWeek()), WeeklyPalanPO::getTaskWeek, weeklyPalanListParamPO.getTaskWeek())
//                .eq(KPStringUtil.isNotEmpty(weeklyPalanListParamPO.getTaskPriority()), WeeklyPalanPO::getTaskPriority, weeklyPalanListParamPO.getTaskPriority())
//                .eq(KPStringUtil.isNotEmpty(weeklyPalanListParamPO.getTaskStatus()), WeeklyPalanPO::getTaskStatus, weeklyPalanListParamPO.getTaskStatus())
//                .like(KPStringUtil.isNotEmpty(weeklyPalanListParamPO.getRemark()), WeeklyPalanPO::getRemark, weeklyPalanListParamPO.getRemark());
//
//        //分页和排序
//        PageHelper.startPage(weeklyPalanListParamPO.getPageNum(), weeklyPalanListParamPO.getPageSize(), weeklyPalanListParamPO.getOrderBy(WeeklyPalanPO.class));
//        return this.baseMapper.selectList(queryWrapper);
//    }


    /**
     * @Author lipeng
     * @Description 根据周计划id查询详情
     * @Date 2025-09-20 22:11:23
     * @param parameter
     * @return JSONObject
     **/
    public JSONObject queryDetailsById(JSONObject parameter) {
        WeeklyPalanPO weeklyPalanPO = KPJsonUtil.toJavaObject(parameter, WeeklyPalanPO.class);
        KPVerifyUtil.notNull(weeklyPalanPO.getWeeklyId(), "请输入weeklyId");
        WeeklyPalanPO body = this.baseMapper.selectById(weeklyPalanPO.getWeeklyId());
        JSONObject row = KPJsonUtil.toJavaObject(body, JSONObject.class);

        row.put("completeDate", Arrays.asList(body.getTaskStartDate(), body.getTaskEndDate()));
        return row;
    }


    /**
     * @Author lipeng
     * @Description 新增周计划
     * @Date 2025-09-20 22:11:23
     * @param weeklyPalanEditParamPO
     * @return void
     **/
    public void saveWeeklyPalan(WeeklyPalanEditParamPO weeklyPalanEditParamPO) {
        WeeklyPalanPO weeklyPalanPO = KPJsonUtil.toJavaObjectNotEmpty(weeklyPalanEditParamPO, WeeklyPalanPO.class);
        String myUserId = LoginUserBO.getLoginUser().getIdentification();

        MonthlyReportPO monthlyReportPO = monthlyReportMapper.selectById(weeklyPalanPO.getMonthlyId());
        if (monthlyReportPO == null) throw new KPServiceException("月度计划不存在");
        MonthlyReportStatusEnum.validateAllowedByEnum(monthlyReportPO.getStatus(),
                Arrays.asList(
                        MonthlyReportStatusEnum.DRAFT,
                        MonthlyReportStatusEnum.SUBMIT_FOR_REVIEW,
                        MonthlyReportStatusEnum.REVIEW_REJECTED,
                        MonthlyReportStatusEnum.COMPLETED
                ), "状态下不允许拆分");

        List<WeeklyPalanPO> userPOList = this.baseMapper.selectList(Wrappers.lambdaQuery(WeeklyPalanPO.class)
                .eq(WeeklyPalanPO::getTaskTitle, weeklyPalanEditParamPO.getTaskTitle())
                .eq(WeeklyPalanPO::getTaskUserId, myUserId)
                .eq(WeeklyPalanPO::getMonthlyPlanDate, monthlyReportPO.getPlanDate()));
        if (KPStringUtil.isNotEmpty(userPOList)) throw new KPServiceException("拆分标题已存在，请勿重复添加");

        weeklyPalanPO.setMonthlyPlanDate(monthlyReportPO.getPlanDate())
                .setTaskUserId(LoginUserBO.getLoginUser().getIdentification())
                .setTaskStatus(WeeklyPalanStatusEnum.NOT_STARTED.code())
                .setTaskStartDate(weeklyPalanEditParamPO.getCompleteDate().get(0))
                .setTaskEndDate(weeklyPalanEditParamPO.getCompleteDate().get(1));
        if (this.baseMapper.insert(weeklyPalanPO) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);

        //如果是待拆分状态 就改成拆分中
        if (monthlyReportPO.getStatus().equals(MonthlyReportStatusEnum.REVIEW_PASSED_WAITING_FOR_SPLIT.code())) {
            monthlyReportMapper.updateById(new MonthlyReportPO()
                    .setMonthlyId(monthlyReportPO.getMonthlyId())
                    .setStatus(MonthlyReportStatusEnum.SPLITTED_IN_PROGRESS.code())
            );
        }

        weeklyUtil.calculateMonthlyPlanprogress(weeklyPalanPO.getMonthlyId());
    }


    /**
     * @Author lipeng
     * @Description 修改周计划
     * @Date 2025-09-20 22:11:23
     * @param weeklyPalanEditParamPO
     * @return void
     **/
    public void updateWeeklyPalan(WeeklyPalanEditParamPO weeklyPalanEditParamPO) {
        WeeklyPalanPO weeklyPalanPO = KPJsonUtil.toJavaObjectNotEmpty(weeklyPalanEditParamPO, WeeklyPalanPO.class);

        WeeklyPalanPO oldWeeklyPalanPO = this.baseMapper.selectById(weeklyPalanEditParamPO.getWeeklyId());
        if (oldWeeklyPalanPO == null) throw new KPServiceException("周计划不存在");
        if (!oldWeeklyPalanPO.getTaskStatus().equals(WeeklyPalanStatusEnum.NOT_STARTED.code()))
            throw new KPServiceException("周计划已锁定，只能修改未开始状态下的计划");

        List<WeeklyPalanPO> userPOList = this.baseMapper.selectList(Wrappers.lambdaQuery(WeeklyPalanPO.class)
                .eq(WeeklyPalanPO::getTaskTitle, weeklyPalanEditParamPO.getTaskTitle())
                .eq(WeeklyPalanPO::getTaskUserId, oldWeeklyPalanPO.getTaskUserId())
                .eq(WeeklyPalanPO::getMonthlyPlanDate, oldWeeklyPalanPO.getMonthlyPlanDate())
                .ne(WeeklyPalanPO::getWeeklyId, oldWeeklyPalanPO.getWeeklyId()));

        if (KPStringUtil.isNotEmpty(userPOList)) throw new KPServiceException("拆分标题已存在, 请修改后重试");

        weeklyPalanPO.setTaskStartDate(weeklyPalanEditParamPO.getCompleteDate().get(0))
                .setTaskEndDate(weeklyPalanEditParamPO.getCompleteDate().get(1));
        if (this.baseMapper.updateById(weeklyPalanPO) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);
    }


//    /**
//     * @Author lipeng
//     * @Description 批量删除周计划
//     * @Date 2025-09-20 22:11:23
//     * @param ids
//     * @return String
//     **/
//    public String batchRemove(List<String> ids) {
//        if (KPStringUtil.isEmpty(ids)) throw new KPServiceException("请选择要删除的内容！");
//
//        Integer row = this.baseMapper.deleteBatchIds(ids);
//        if (row == 0) throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);
//
//        return KPStringUtil.format("删除成功{0}条数据", row);
//    }


    /**
     * @Author lipeng
     * @Description 查询我的周计划列表
     * @Date 2025/9/20 22:14
     * @param parameter
     * @return java.util.Map<java.lang.String,java.util.List<com.kp.framework.modules.week.po.WeeklyPalanPO>>
     **/
    public Map<String, List<WeeklyPalanListCustomerPO>> queryMyList(JSONObject parameter) {
        KPVerifyUtil.notNull(parameter.getString("planTime"), "请选择计划时间");

        List<WeeklyPalanListCustomerPO> list = this.baseMapper.selectJoinList(WeeklyPalanListCustomerPO.class, new MPJLambdaWrapper<WeeklyPalanPO>()
                .selectAll(WeeklyPalanPO.class)
                .select(MonthlyReportPO::getTaskName, MonthlyReportPO::getProjectName)
                .leftJoin(MonthlyReportPO.class, on -> on
                        .eq(MonthlyReportPO::getMonthlyId, WeeklyPalanPO::getMonthlyId)
                        .eq(MonthlyReportPO::getDeleteFlag, DeleteFalgEnum.NORMAL.code())
                )
                .disableSubLogicDel()
                .eq(WeeklyPalanPO::getTaskUserId, LoginUserBO.getLoginUser().getIdentification())
                .eq(WeeklyPalanPO::getMonthlyPlanDate, KPLocalDateUtil.parse(parameter.getString("planTime") + "-01", KPLocalDateUtil.DATE_PATTERN))
                .orderByAsc(WeeklyPalanPO::getTaskPriority));

        return list.stream().collect(Collectors.groupingBy(WeeklyPalanPO::getTaskWeek));
    }


    /**
     * @Author lipeng
     * @Description 删除周计划
     * @Date 2025/9/20 23:10
     * @param parameter
     * @return void
     **/
    public void remove(JSONObject parameter) {
        KPVerifyUtil.notNull(parameter.getString("weeklyId"), "请选择要删除的内容！");

        WeeklyPalanPO oldWeeklyPalanPO = this.baseMapper.selectById(parameter.getString("weeklyId"));
        if (oldWeeklyPalanPO == null) throw new KPServiceException("周计划不存在");
        if (!oldWeeklyPalanPO.getTaskStatus().equals(WeeklyPalanStatusEnum.NOT_STARTED.code()))
            throw new KPServiceException("周计划已锁定，只能删除未开始状态下的计划");

        int row = this.baseMapper.deleteById(parameter.getString("weeklyId"));
        if (row == 0) throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);

        weeklyUtil.calculateMonthlyPlanprogress(oldWeeklyPalanPO.getMonthlyId());
    }


    /**
     * @Author lipeng
     * @Description 废弃周计划
     * @Date 2025/9/20 23:14
     * @param parameter
     * @return void
     **/
    public void discard(JSONObject parameter) {
        KPVerifyUtil.notNull(parameter.getString("weeklyId"), "请选择要废弃的内容！");

        WeeklyPalanPO weeklyPalanPO = this.baseMapper.selectById(parameter.getString("weeklyId"));
        if (weeklyPalanPO == null) throw new KPServiceException("周计划不存在");
        if (weeklyPalanPO.getTaskStatus().equals(WeeklyPalanStatusEnum.COMPLETED.code()))
            throw new KPServiceException("周计划已完成，只能废弃未完成状态下的计划");

        weeklyPalanPO.setTaskStatus(WeeklyPalanStatusEnum.DISCARD.code());

        if (this.baseMapper.updateById(weeklyPalanPO) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);

        weeklyUtil.calculateMonthlyPlanprogress(weeklyPalanPO.getMonthlyId());
    }


    /**
     * @Author lipeng
     * @Description 查询本人月计划拆分完成度
     * @Date 2025/9/23
     * @param parameter
     * @return com.kp.framework.modules.week.po.customer.WeeklyPalanCustomerCustomerPO
     **/
    public List<WeeklyPalanCustomerCustomerPO> queryWeeklyPalanCustomer(JSONObject parameter) {
        KPVerifyUtil.notNull(parameter.getString("planTime"), "请选择计划时间");

        LambdaQueryWrapper<WeeklyPalanPO> wrappers = Wrappers.lambdaQuery(WeeklyPalanPO.class)
                .eq(WeeklyPalanPO::getTaskUserId, LoginUserBO.getLoginUser().getIdentification())
                .eq(WeeklyPalanPO::getMonthlyPlanDate, KPLocalDateUtil.parse(parameter.getString("planTime") + "-01", KPLocalDateUtil.DATE_PATTERN))
                .ne(WeeklyPalanPO::getTaskStatus, WeeklyPalanStatusEnum.DISCARD.code())
                .eq(WeeklyPalanPO::getDeleteFlag, DeleteFalgEnum.NORMAL.code());

        return weeklyPalanCustomerMapper.queryWeeklyPalanCustomer(wrappers);
    }


    /**
     * @Author lipeng
     * @Description 查询本人周计划统计数
     * @Date 2025/9/26
     * @param parameter
     * @return java.util.List<com.kp.framework.modules.week.po.customer.WeellyTaskSummaryCustomerPO>
     **/
    public WeellyTaskSummaryCustomerPO queryWeeklyNumber(JSONObject parameter) {
        KPVerifyUtil.notNull(parameter.getString("planTime"), "请选择计划时间");

        LambdaQueryWrapper<WeeklyPalanPO> queryWrapper = Wrappers.lambdaQuery(WeeklyPalanPO.class)
                .eq(WeeklyPalanPO::getTaskUserId, LoginUserBO.getLoginUser().getIdentification())
                .eq(WeeklyPalanPO::getMonthlyPlanDate, KPLocalDateUtil.parse(parameter.getString("planTime") + "-01", KPLocalDateUtil.DATE_PATTERN))
                .eq(WeeklyPalanPO::getDeleteFlag, DeleteFalgEnum.NORMAL.code());

        return weeklyPalanCustomerMapper.queryWeeklyNumber(queryWrapper);
    }


    /**
     * @Author lipeng
     * @Description 查询我的周计划列表(按状态分组)
     * @Date 2025/9/27
     * @param parameter
     * @return java.util.Map<java.lang.Integer,java.util.List<com.kp.framework.modules.week.po.WeeklyPalanPO>>
     **/
    public Map<Integer, List<WeeklyPalanListCustomerPO>> queryMyListByStatus(JSONObject parameter) {
        KPVerifyUtil.notNull(parameter.getString("planTime"), "请选择计划时间");


        List<WeeklyPalanListCustomerPO> list = this.baseMapper.selectJoinList(WeeklyPalanListCustomerPO.class, new MPJLambdaWrapper<WeeklyPalanPO>()
                .selectAll(WeeklyPalanPO.class)
                .select(MonthlyReportPO::getTaskName, MonthlyReportPO::getProjectName)
                .leftJoin(MonthlyReportPO.class, on -> on
                        .eq(MonthlyReportPO::getMonthlyId, WeeklyPalanPO::getMonthlyId)
                        .eq(MonthlyReportPO::getDeleteFlag, DeleteFalgEnum.NORMAL.code())
                )
                .disableSubLogicDel()
                .eq(WeeklyPalanPO::getTaskUserId, LoginUserBO.getLoginUser().getIdentification())
                .eq(WeeklyPalanPO::getMonthlyPlanDate, KPLocalDateUtil.parse(parameter.getString("planTime") + "-01", KPLocalDateUtil.DATE_PATTERN))
                .ne(WeeklyPalanPO::getTaskStatus, WeeklyPalanStatusEnum.DISCARD.code())
                .orderByAsc(WeeklyPalanPO::getTaskPriority));


//        List<WeeklyPalanPO> list = this.baseMapper.selectList(Wrappers.lambdaQuery(WeeklyPalanPO.class)
//                .eq(WeeklyPalanPO::getTaskUserId, LoginUserBO.getLoginUser().getIdentification())
//                .eq(WeeklyPalanPO::getMonthlyPlanDate, KPLocalDateUtil.parse(parameter.getString("planTime") + "-01", KPLocalDateUtil.DATE_PATTERN))
//                .ne(WeeklyPalanPO::getTaskStatus, WeeklyPalanStatusEnum.DISCARD.code())
//                .orderByAsc(WeeklyPalanPO::getTaskPriority));

        return list.stream().collect(Collectors.groupingBy(WeeklyPalanPO::getTaskStatus));
    }


    /**
     * @Author lipeng
     * @Description 移动设置周计划状态
     * @Date 2025/9/27
     * @param parameter
     * @return void
     **/
    public void updateMoveStatus(JSONObject parameter) {
        WeeklyPalanPO weekParameter = KPJsonUtil.toJavaObjectNotEmpty(parameter, WeeklyPalanPO.class);
        KPVerifyUtil.notNull(weekParameter.getWeeklyId(), "请选择要移动的内容！");
        KPVerifyUtil.notNull(weekParameter.getTaskStatus(), "请输入要移动的周计划状态！");

        WeeklyPalanPO weeklyPalanPO = this.baseMapper.selectById(weekParameter.getWeeklyId());
        if (weeklyPalanPO == null) throw new KPServiceException("周计划不存在");

        if (weekParameter.getTaskStatus().equals(weeklyPalanPO.getTaskStatus()))
            throw new KPServiceException("周计划已处于该状态！");

        //校验是否可以回退状态
        if (weeklyPalanPO.getTaskStatus().equals(WeeklyPalanStatusEnum.COMPLETED.code())
                && !weekParameter.getTaskStatus().equals(WeeklyPalanStatusEnum.COMPLETED.code())) {
            MonthlyReportPO monthlyReportPO = monthlyReportMapper.selectById(weeklyPalanPO.getMonthlyId());
            if (monthlyReportPO == null) throw new KPServiceException("月度计划不存在");
            if (monthlyReportPO.getStatus().equals(MonthlyReportStatusEnum.COMPLETED.code()))
                throw new KPServiceException("月度计划已完成，无法回退周计划状态！");
        }

        if (this.baseMapper.updateById(new WeeklyPalanPO()
                .setWeeklyId(weekParameter.getWeeklyId())
                .setTaskStatus(weekParameter.getTaskStatus())) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);

//        if (weekParameter.getTaskStatus().equals(WeeklyPalanStatusEnum.COMPLETED.code()))
        weeklyUtil.calculateMonthlyPlanprogress(weeklyPalanPO.getMonthlyId());
    }


    /**
     * @Author lipeng
     * @Description 查询周计划列表 不带分页
     * @Date 2025/9/30 17:35
     * @param weeklyPalanListParamPO
     * @return java.util.List<com.kp.framework.modules.week.po.customer.WeeklyPalanListCustomerPO>
     **/
    public List<WeeklyPalanListCustomerPO> queryList(WeeklyPalanListParamPO weeklyPalanListParamPO) {
        LambdaQueryWrapper<WeeklyPalanPO> queryWrapper = Wrappers.lambdaQuery(WeeklyPalanPO.class)
                .eq(KPStringUtil.isNotEmpty(weeklyPalanListParamPO.getMonthlyId()), WeeklyPalanPO::getMonthlyId, weeklyPalanListParamPO.getMonthlyId())
                .eq(KPStringUtil.isNotEmpty(weeklyPalanListParamPO.getPlanTime()), WeeklyPalanPO::getMonthlyPlanDate, KPLocalDateUtil.parse(KPStringUtil.isNotEmpty(weeklyPalanListParamPO.getPlanTime()) ? weeklyPalanListParamPO.getPlanTime() : "1900-01" + "-01", KPLocalDateUtil.DATE_PATTERN))
                .like(KPStringUtil.isNotEmpty(weeklyPalanListParamPO.getTaskTitle()), WeeklyPalanPO::getTaskTitle, weeklyPalanListParamPO.getTaskTitle())
                .eq(KPStringUtil.isNotEmpty(weeklyPalanListParamPO.getTaskUserId()), WeeklyPalanPO::getTaskUserId, weeklyPalanListParamPO.getTaskUserId())
                .eq(KPStringUtil.isNotEmpty(weeklyPalanListParamPO.getTaskWeek()), WeeklyPalanPO::getTaskWeek, weeklyPalanListParamPO.getTaskWeek())
                .eq(KPStringUtil.isNotEmpty(weeklyPalanListParamPO.getTaskPriority()), WeeklyPalanPO::getTaskPriority, weeklyPalanListParamPO.getTaskPriority())
                .eq(KPStringUtil.isNotEmpty(weeklyPalanListParamPO.getTaskStatus()), WeeklyPalanPO::getTaskStatus, weeklyPalanListParamPO.getTaskStatus());

        PageHelper.orderBy(new PageBO().getOrderBy(weeklyPalanListParamPO.getOrderBy(), WeeklyPalanPO.class));
        List<WeeklyPalanPO> weeklyPalanPOList = this.baseMapper.selectList(queryWrapper);
        if (weeklyPalanPOList.size() == 0) return new ArrayList<>();

        List<WeeklyPalanListCustomerPO> body = KPJsonUtil.toJavaObjectList(weeklyPalanPOList, WeeklyPalanListCustomerPO.class);

        List<String> userIdList = body.stream().map(WeeklyPalanListCustomerPO::getTaskUserId).collect(Collectors.toList());

        //查询用户信息
        Map<String, UserFeignPO> userMap = feignAuthUtil.queryUserListByIds(userIdList).stream().collect(Collectors.toMap(UserFeignPO::getUserId, Function.identity()));

        body.forEach(item -> {
            UserFeignPO user = userMap.get(item.getTaskUserId());
            if (user != null) item.setRealName(user.getRealName()).setAvatar(user.getAvatar());
        });

        return body;
    }
}
