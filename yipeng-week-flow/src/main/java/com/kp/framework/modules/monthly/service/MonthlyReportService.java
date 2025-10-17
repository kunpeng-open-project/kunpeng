package com.kp.framework.modules.monthly.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.kp.framework.constant.ReturnFinishedMessageConstant;
import com.kp.framework.enums.DeleteFalgEnum;
import com.kp.framework.enums.YesNoEnum;
import com.kp.framework.exception.KPServiceException;
import com.kp.framework.microservices.auth.po.DeptFeignPO;
import com.kp.framework.microservices.auth.po.PostFeignPO;
import com.kp.framework.microservices.auth.po.UserFeignPO;
import com.kp.framework.microservices.auth.util.FeignAuthUtil;
import com.kp.framework.modules.monthly.enums.MonthlyReportStatusEnum;
import com.kp.framework.modules.monthly.mapper.MonthlyReportMapper;
import com.kp.framework.modules.monthly.mapper.MonthlyReportUserMapper;
import com.kp.framework.modules.monthly.po.MonthlyReportPO;
import com.kp.framework.modules.monthly.po.MonthlyReportUserPO;
import com.kp.framework.modules.monthly.po.customer.MonthlyReportDetailsCustomerPO;
import com.kp.framework.modules.monthly.po.customer.MonthlyReportListCustomerPO;
import com.kp.framework.modules.monthly.po.customer.MonthlyReportReviewListCustomerPO;
import com.kp.framework.modules.monthly.po.param.MonthlyReportEditParamPO;
import com.kp.framework.modules.monthly.po.param.MonthlyReportListParamPO;
import com.kp.framework.modules.monthly.po.param.MonthlyReportReviewParamPO;
import com.kp.framework.modules.user.po.customer.LoginUserBO;
import com.kp.framework.utils.kptool.KPDatabaseUtil;
import com.kp.framework.utils.kptool.KPJSONFactoryUtil;
import com.kp.framework.utils.kptool.KPJsonUtil;
import com.kp.framework.utils.kptool.KPLocalDateTimeUtil;
import com.kp.framework.utils.kptool.KPLocalDateUtil;
import com.kp.framework.utils.kptool.KPStringUtil;
import com.kp.framework.utils.kptool.KPVerifyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author lipeng
 * @Description 月度计划表 服务实现类
 * @Date 2025-07-25
 **/
@Service
public class MonthlyReportService extends ServiceImpl<MonthlyReportMapper, MonthlyReportPO> {

    @Autowired
    private FeignAuthUtil feignAuthUtil;

    @Autowired
    private MonthlyReportUserMapper monthlyReportUserMapper;

    /**
     * @Author lipeng
     * @Description 查询月度计划列表
     * @Date 2025-07-25
     * @param monthlyReportListParamPO
     * @return java.util.List<MonthlyReportPO>
     **/
    public List<MonthlyReportListCustomerPO> queryPageList(MonthlyReportListParamPO monthlyReportListParamPO) {
        MPJLambdaWrapper<MonthlyReportPO> wrapper = new MPJLambdaWrapper<MonthlyReportPO>("monthlyReport")
                .selectAll(MonthlyReportPO.class, "monthlyReport")
                .select(KPDatabaseUtil.groupDistinctConcat("monthlyReportUser.user_name", "userNamss"))
                .leftJoin(MonthlyReportUserPO.class, "monthlyReportUser", on -> on
                        .eq(MonthlyReportUserPO::getMonthlyId, MonthlyReportPO::getMonthlyId)
                        .eq(MonthlyReportUserPO::getDeleteFlag, DeleteFalgEnum.NORMAL.code())
                )
                .disableSubLogicDel()
//                .eq(KPStringUtil.isNotEmpty(monthlyReportListParamPO.getYear()), MonthlyReportPO::getYear, monthlyReportListParamPO.getYear())
//                .eq(KPStringUtil.isNotEmpty(monthlyReportListParamPO.getMonth()), MonthlyReportPO::getMonth, monthlyReportListParamPO.getMonth())
                // 开始时间条件（直接调用getter方法获取解析结果）
                .between(KPStringUtil.isNotEmpty(monthlyReportListParamPO.getPlanTimes()), MonthlyReportPO::getPlanDate, monthlyReportListParamPO.getPlanTimes() == null ? LocalDate.now() : KPLocalDateUtil.getEffectiveDate(monthlyReportListParamPO.getPlanTimes().get(0)), monthlyReportListParamPO.getPlanTimes() == null ? LocalDate.now() : KPLocalDateUtil.getEffectiveDate(monthlyReportListParamPO.getPlanTimes().get(1)))
                .like(KPStringUtil.isNotEmpty(monthlyReportListParamPO.getProjectName()), MonthlyReportPO::getProjectName, monthlyReportListParamPO.getProjectName())
                .like(KPStringUtil.isNotEmpty(monthlyReportListParamPO.getTaskName()), MonthlyReportPO::getTaskName, monthlyReportListParamPO.getTaskName())
                .eq(KPStringUtil.isNotEmpty(monthlyReportListParamPO.getPriority()), MonthlyReportPO::getPriority, monthlyReportListParamPO.getPriority())
                .eq(KPStringUtil.isNotEmpty(monthlyReportListParamPO.getStatus()), MonthlyReportPO::getStatus, monthlyReportListParamPO.getStatus())
                .eq(KPStringUtil.isNotEmpty(monthlyReportListParamPO.getSource()), MonthlyReportPO::getSource, monthlyReportListParamPO.getSource())
                .eq(KPStringUtil.isNotEmpty(monthlyReportListParamPO.getPostId()), MonthlyReportPO::getPostId, monthlyReportListParamPO.getPostId())
                .eq(KPStringUtil.isNotEmpty(monthlyReportListParamPO.getDeptId()), MonthlyReportPO::getDeptId, monthlyReportListParamPO.getDeptId());

        KPDatabaseUtil.groupFieldsBy(wrapper, "monthlyReport", MonthlyReportPO.class);
        Page page = PageHelper.startPage(monthlyReportListParamPO.getPageNum(), monthlyReportListParamPO.getPageSize(), monthlyReportListParamPO.getOrderBy(MonthlyReportPO.class));
        page.setCountColumn("distinct monthly_id");
        List<MonthlyReportListCustomerPO> body = this.baseMapper.selectJoinList(MonthlyReportListCustomerPO.class, wrapper);

        body.forEach(po -> po.setPlanTime(KPLocalDateUtil.toString(po.getPlanDate(), KPLocalDateTimeUtil.MONTH_PATTERN)));

        return body;
    }


    /**
     * @Author lipeng
     * @Description 根据月度计划Id查询详情
     * @Date 2025-07-25
     * @param parameter
     * @return MonthlyReportPO
     **/
    public MonthlyReportDetailsCustomerPO queryDetailsById(JSONObject parameter) {
        MonthlyReportPO monthlyReportPO = KPJsonUtil.toJavaObject(parameter, MonthlyReportPO.class);
        KPVerifyUtil.notNull(monthlyReportPO.getMonthlyId(), "请输入monthlyId");

        MPJLambdaWrapper<MonthlyReportPO> wrapper = new MPJLambdaWrapper<MonthlyReportPO>("monthlyReport")
                .selectAll(MonthlyReportPO.class, "monthlyReport")
                .selectCollection(MonthlyReportUserPO.class, MonthlyReportDetailsCustomerPO::getMonthlyReportUserList)
                .leftJoin(MonthlyReportUserPO.class, on -> on
                        .eq(MonthlyReportUserPO::getMonthlyId, MonthlyReportPO::getMonthlyId)
                        .eq(MonthlyReportUserPO::getDeleteFlag, DeleteFalgEnum.NORMAL.code())
                )
                .disableSubLogicDel()
                .eq(MonthlyReportPO::getMonthlyId, monthlyReportPO.getMonthlyId());

        List<MonthlyReportDetailsCustomerPO> customerPOList = this.baseMapper.selectJoinList(MonthlyReportDetailsCustomerPO.class, wrapper);

        if (KPStringUtil.isEmpty(customerPOList)) return new MonthlyReportDetailsCustomerPO();
        if (customerPOList.size() > 1) throw new KPServiceException("查询结果异常");

        MonthlyReportDetailsCustomerPO row = customerPOList.get(0);
        DeptFeignPO dept = feignAuthUtil.queryDeptById(row.getDeptId());
        PostFeignPO post = feignAuthUtil.queryPostById(row.getPostId());

        row.setPlanTime(KPLocalDateUtil.toString(row.getPlanDate(), KPLocalDateTimeUtil.MONTH_PATTERN))
                .setCompleteDate(Arrays.asList(row.getStartDate(), row.getEndDate()))
                .setDeptName(dept.getDeptName())
                .setPostName(post.getPostName());

        if (KPStringUtil.isNotEmpty(row.getMonthlyReportUserList())) {
            row.setUserIds(row.getMonthlyReportUserList().stream().map(MonthlyReportUserPO::getUserId).collect(Collectors.toList()))
                    .setUserNames(row.getMonthlyReportUserList().stream().map(MonthlyReportUserPO::getUserName).collect(Collectors.toList()));
        }
        return row;
    }


    /**
     * @Author lipeng
     * @Description 新增月度计划
     * @Date 2025-07-25
     * @param monthlyReportEditParamPO
     * @return void
     **/
    public void saveMonthlyReport(MonthlyReportEditParamPO monthlyReportEditParamPO) {
        MonthlyReportPO monthlyReportPO = KPJsonUtil.toJavaObjectNotEmpty(monthlyReportEditParamPO, MonthlyReportPO.class);
        monthlyReportPO
                .setPlanDate(KPLocalDateUtil.parse(monthlyReportEditParamPO.getPlanTime() + "-01", KPLocalDateUtil.DATE_PATTERN))
                .setStartDate(monthlyReportEditParamPO.getCompleteDate().get(0))
                .setEndDate(monthlyReportEditParamPO.getCompleteDate().get(1))
                .setProgress(0);

        if (this.baseMapper.insert(monthlyReportPO) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);

        List<UserFeignPO> userList = feignAuthUtil.queryUserListByIds(monthlyReportEditParamPO.getUserIds());
        Map<String, UserFeignPO> userListMap = userList.stream().collect(Collectors.toMap(UserFeignPO::getUserId, Function.identity()));

        List<MonthlyReportUserPO> monthlyReportUserPOList = new ArrayList<>();
        monthlyReportEditParamPO.getUserIds().forEach(userId -> {
            monthlyReportUserPOList.add(new MonthlyReportUserPO()
                    .setMonthlyId(monthlyReportPO.getMonthlyId())
                    .setUserId(userId)
                    .setUserName(userListMap.get(userId).getRealName())
            );
        });

        monthlyReportUserMapper.insertBatchSomeColumn(monthlyReportUserPOList);
    }


    /**
     * @Author lipeng
     * @Description 修改月度计划
     * @Date 2025-07-25
     * @param monthlyReportEditParamPO
     * @return void
     **/
    public void updateMonthlyReport(MonthlyReportEditParamPO monthlyReportEditParamPO) {
        MonthlyReportPO monthlyReportPO = KPJsonUtil.toJavaObjectNotEmpty(monthlyReportEditParamPO, MonthlyReportPO.class);

        MonthlyReportPO oldMonthlyReportPO = this.baseMapper.selectById(monthlyReportPO.getMonthlyId());
        if (KPStringUtil.isEmpty(oldMonthlyReportPO)) throw new KPServiceException("月度计划不存在");
        if (!oldMonthlyReportPO.getStatus().equals(MonthlyReportStatusEnum.DRAFT.code()))
            throw new KPServiceException("计划已锁定，只能修改草稿状态下的计划");


        monthlyReportPO
                .setPlanDate(KPLocalDateUtil.parse(monthlyReportEditParamPO.getPlanTime() + "-01", KPLocalDateUtil.DATE_PATTERN))
                .setStartDate(monthlyReportEditParamPO.getCompleteDate().get(0))
                .setEndDate(monthlyReportEditParamPO.getCompleteDate().get(1))
                .setProgress(null);

        if (this.baseMapper.updateById(monthlyReportPO) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);


        List<String> delIds = monthlyReportUserMapper.selectList(Wrappers.lambdaQuery(MonthlyReportUserPO.class).eq(MonthlyReportUserPO::getMonthlyId, monthlyReportPO.getMonthlyId())).stream().map(MonthlyReportUserPO::getMruId).collect(Collectors.toList());
        if (KPStringUtil.isNotEmpty(delIds)) monthlyReportUserMapper.deleteAllByIds(delIds);

        List<UserFeignPO> userList = feignAuthUtil.queryUserListByIds(monthlyReportEditParamPO.getUserIds());
        Map<String, UserFeignPO> userListMap = userList.stream().collect(Collectors.toMap(UserFeignPO::getUserId, Function.identity()));

        List<MonthlyReportUserPO> monthlyReportUserPOList = new ArrayList<>();
        monthlyReportEditParamPO.getUserIds().forEach(userId -> {
            monthlyReportUserPOList.add(new MonthlyReportUserPO()
                    .setMonthlyId(monthlyReportPO.getMonthlyId())
                    .setUserId(userId)
                    .setUserName(userListMap.get(userId).getRealName())
            );
        });

        monthlyReportUserMapper.insertBatchSomeColumn(monthlyReportUserPOList);
    }


    /**
     * @Author lipeng
     * @Description 批量删除月度计划
     * @Date 2025-07-25
     * @param ids
     * @return String
     **/
    public String batchRemove(List<String> ids) {
        if (KPStringUtil.isEmpty(ids)) throw new KPServiceException("请选择要删除的内容！");

        this.baseMapper.selectBatchIds(ids).forEach(monthlyReportPO -> {
            if (!monthlyReportPO.getStatus().equals(MonthlyReportStatusEnum.DRAFT.code()))
                throw new KPServiceException(KPStringUtil.format("只能删除草稿状态的计划， {0} 不允许删除", monthlyReportPO.getTaskName()));
        });

        Integer row = this.baseMapper.deleteBatchIds(ids);
        if (row == 0) throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);

        return KPStringUtil.format("删除成功{0}条数据", row);
    }


    /**
     * @Author lipeng
     * @Description 提交审核
     * @Date 2025/8/30
     * @param ids
     * @return java.lang.String
     **/
    public String doSubmitReview(List<String> ids) {
        if (KPStringUtil.isEmpty(ids)) throw new KPServiceException("请选择要提交的内容！");

        List<MonthlyReportPO> body = new ArrayList<>();
        this.baseMapper.selectBatchIds(ids).forEach(monthlyReportPO -> {
            if (!monthlyReportPO.getStatus().equals(MonthlyReportStatusEnum.DRAFT.code()))
                throw new KPServiceException(KPStringUtil.format("只有草稿状态的计划才可以提交审核， {0} 不能提交审核", monthlyReportPO.getTaskName()));
            body.add(new MonthlyReportPO()
                    .setMonthlyId(monthlyReportPO.getMonthlyId())
                    .setStatus(MonthlyReportStatusEnum.SUBMIT_FOR_REVIEW.code()));
        });

        if (!this.updateBatchById(body)) throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);

        return KPStringUtil.format("删除提交{0}条数据", body.size());
    }


    /**
     * @Author lipeng
     * @Description 查询月度计划审核分页列表
     * @Date 2025/9/16
     * @param monthlyReportListParamPO
     * @return java.util.List<com.kp.framework.modules.monthly.po.customer.MonthlyReportReviewListCustomerPO>
     **/
    public List<MonthlyReportReviewListCustomerPO> queryReviewPageList(MonthlyReportListParamPO monthlyReportListParamPO) {
        MPJLambdaWrapper<MonthlyReportPO> wrapper = new MPJLambdaWrapper<MonthlyReportPO>("monthlyReport")
                .selectAll(MonthlyReportPO.class, "monthlyReport")
                .select(KPDatabaseUtil.groupDistinctConcat("monthlyReportUser.user_id", "userIds"))
                .leftJoin(MonthlyReportUserPO.class, "monthlyReportUser", on -> on
                        .eq(MonthlyReportUserPO::getMonthlyId, MonthlyReportPO::getMonthlyId)
                        .eq(MonthlyReportUserPO::getDeleteFlag, DeleteFalgEnum.NORMAL.code())
                )
                .disableSubLogicDel()
                .between(KPStringUtil.isNotEmpty(monthlyReportListParamPO.getPlanTimes()), MonthlyReportPO::getPlanDate, monthlyReportListParamPO.getPlanTimes() == null ? LocalDate.now() : KPLocalDateUtil.getEffectiveDate(monthlyReportListParamPO.getPlanTimes().get(0)), monthlyReportListParamPO.getPlanTimes() == null ? LocalDate.now() : KPLocalDateUtil.getEffectiveDate(monthlyReportListParamPO.getPlanTimes().get(1)))
                .like(KPStringUtil.isNotEmpty(monthlyReportListParamPO.getProjectName()), MonthlyReportPO::getProjectName, monthlyReportListParamPO.getProjectName())
                .like(KPStringUtil.isNotEmpty(monthlyReportListParamPO.getTaskName()), MonthlyReportPO::getTaskName, monthlyReportListParamPO.getTaskName())
                .eq(KPStringUtil.isNotEmpty(monthlyReportListParamPO.getPriority()), MonthlyReportPO::getPriority, monthlyReportListParamPO.getPriority())
                .eq(KPStringUtil.isNotEmpty(monthlyReportListParamPO.getStatus()), MonthlyReportPO::getStatus, monthlyReportListParamPO.getStatus())
                .eq(KPStringUtil.isNotEmpty(monthlyReportListParamPO.getSource()), MonthlyReportPO::getSource, monthlyReportListParamPO.getSource())
                .eq(KPStringUtil.isNotEmpty(monthlyReportListParamPO.getPostId()), MonthlyReportPO::getPostId, monthlyReportListParamPO.getPostId())
                .eq(KPStringUtil.isNotEmpty(monthlyReportListParamPO.getDeptId()), MonthlyReportPO::getDeptId, monthlyReportListParamPO.getDeptId());

        KPDatabaseUtil.groupFieldsBy(wrapper, "monthlyReport", MonthlyReportPO.class);
        Page page = PageHelper.startPage(monthlyReportListParamPO.getPageNum(), monthlyReportListParamPO.getPageSize(), monthlyReportListParamPO.getOrderBy(MonthlyReportPO.class));
        page.setCountColumn("distinct monthly_id");
        List<MonthlyReportReviewListCustomerPO> body = this.baseMapper.selectJoinList(MonthlyReportReviewListCustomerPO.class, wrapper);
        if (KPStringUtil.isEmpty(body)) return body;

        //查询部门信息
        Map<String, DeptFeignPO> deptMap = feignAuthUtil.queryDeptIdList(body.stream().map(MonthlyReportReviewListCustomerPO::getDeptId).collect(Collectors.toList()))
                .stream().collect(Collectors.toMap(DeptFeignPO::getDeptId, Function.identity()));
        //查询岗位信息
        Map<String, PostFeignPO> postMap = feignAuthUtil.queryPostIdList(body.stream().map(MonthlyReportReviewListCustomerPO::getPostId).collect(Collectors.toList()))
                .stream().collect(Collectors.toMap(PostFeignPO::getPostId, Function.identity()));
        //查询用户信息
        List<String> userIds = body.stream().map(MonthlyReportReviewListCustomerPO::getUserIds)
                .filter(KPStringUtil::isNotEmpty)
                .flatMap(ids -> Arrays.stream(ids.split(",")).map(String::trim).filter(KPStringUtil::isNotEmpty))
                .distinct()
                .collect(Collectors.toList());
        Map<String, UserFeignPO> userMap = feignAuthUtil.queryUserListByIds(userIds).stream().collect(Collectors.toMap(UserFeignPO::getUserId, Function.identity()));


        body.forEach(item -> {
            item.setDeptName(deptMap.get(item.getDeptId()).getDeptName())
                    .setPostName(postMap.get(item.getPostId()).getPostName());

            // 新增：设置用户信息列表（id、姓名、头像）
            if (KPStringUtil.isNotEmpty(item.getUserIds())) {
                List<JSONObject> userList = Arrays.stream(item.getUserIds().split(","))
                        .map(String::trim).filter(KPStringUtil::isNotEmpty)
                        .map(userId -> {
                            UserFeignPO user = userMap.get(userId);
                            if (user == null) return null;
                            return new KPJSONFactoryUtil()
                                    .put("userId", user.getUserId())
                                    .put("realName", user.getRealName())
                                    .put("avatar", user.getAvatar())
                                    .build();
                        }).filter(Objects::nonNull).collect(Collectors.toList());

                item.setUserList(userList);
            }
        });

        return body;
    }


    /**
     * @Author lipeng
     * @Description 批量审核
     * @Date 2025/9/4
     * @param monthlyReportReviewParamPO
     * @return java.lang.String
     **/
    public String batchReview(MonthlyReportReviewParamPO monthlyReportReviewParamPO) {
        List<MonthlyReportPO> body = new ArrayList<>();
        this.baseMapper.selectBatchIds(monthlyReportReviewParamPO.getIds()).forEach(monthlyReportPO -> {
            if (!monthlyReportPO.getStatus().equals(MonthlyReportStatusEnum.SUBMIT_FOR_REVIEW.code()))
                throw new KPServiceException(KPStringUtil.format("只有提交审核的计划才可以审核, {0} 不是待审核状态", monthlyReportPO.getTaskName()));

            body.add(new MonthlyReportPO()
                    .setMonthlyId(monthlyReportPO.getMonthlyId())
                    .setReviewComments(monthlyReportReviewParamPO.getReviewComments())
                    .setStatus(monthlyReportReviewParamPO.getStatus().equals(YesNoEnum.YES.code()) ? MonthlyReportStatusEnum.REVIEW_PASSED_WAITING_FOR_SPLIT.code() : MonthlyReportStatusEnum.REVIEW_REJECTED.code())
                    .setPriority(monthlyReportReviewParamPO.getPriority()));

        });

        if (!this.updateBatchById(body)) throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);

        return KPStringUtil.format("删除提交{0}条数据", body.size());
    }


    /**
     * @Author lipeng
     * @Description 查询我的月度计划
     * @Date 2025/9/19
     * @param parameter
     * @return java.util.List<com.kp.framework.modules.monthly.po.MonthlyReportPO>
     **/
    public List<MonthlyReportPO> queryMyList(JSONObject parameter) {
        KPVerifyUtil.notNull(parameter.getString("planTime"), "请选择计划时间");

        MPJLambdaWrapper<MonthlyReportPO> wrapper = new MPJLambdaWrapper<MonthlyReportPO>("monthlyReport")
                .selectAll(MonthlyReportPO.class, "monthlyReport")
                .leftJoin(MonthlyReportUserPO.class, "monthlyReportUser", on -> on
                        .eq(MonthlyReportUserPO::getMonthlyId, MonthlyReportPO::getMonthlyId)
                        .eq(MonthlyReportUserPO::getDeleteFlag, DeleteFalgEnum.NORMAL.code())
                )
                .disableSubLogicDel()
                .eq(MonthlyReportPO::getPlanDate, KPLocalDateUtil.parse(parameter.getString("planTime") + "-01", KPLocalDateUtil.DATE_PATTERN))
                .eq(MonthlyReportUserPO::getUserId, LoginUserBO.getLoginUser().getIdentification())
                .in(MonthlyReportPO::getStatus, Arrays.asList(MonthlyReportStatusEnum.REVIEW_PASSED_WAITING_FOR_SPLIT.code(), MonthlyReportStatusEnum.SPLITTED_IN_PROGRESS.code(), MonthlyReportStatusEnum.COMPLETED.code(), MonthlyReportStatusEnum.OVERDUE.code()));

        return this.baseMapper.selectJoinList(MonthlyReportPO.class, wrapper);
    }


    /**
     * @Author lipeng
     * @Description 查询指定月份月计划列表(按状态分组)
     * @Date 2025/9/30
     * @param parameter
     * @return java.util.Map<java.lang.Integer,java.util.List<com.kp.framework.modules.monthly.po.customer.MonthlyReportReviewListCustomerPO>>
     **/
    public Map<Integer, List<MonthlyReportReviewListCustomerPO>> queryListByStatus(JSONObject parameter) {
        KPVerifyUtil.notNull(parameter.getString("planTime"), "请选择计划时间");
        MPJLambdaWrapper<MonthlyReportPO> wrapper = new MPJLambdaWrapper<MonthlyReportPO>("monthlyReport")
                .selectAll(MonthlyReportPO.class, "monthlyReport")
                .select(KPDatabaseUtil.groupDistinctConcat("monthlyReportUser.user_id", "userIds"))
                .leftJoin(MonthlyReportUserPO.class, "monthlyReportUser", on -> on
                        .eq(MonthlyReportUserPO::getMonthlyId, MonthlyReportPO::getMonthlyId)
                        .eq(MonthlyReportUserPO::getDeleteFlag, DeleteFalgEnum.NORMAL.code())
                )
                .disableSubLogicDel()
                .eq(MonthlyReportPO::getPlanDate, KPLocalDateUtil.parse(parameter.getString("planTime") + "-01", KPLocalDateUtil.DATE_PATTERN))
                .in(MonthlyReportPO::getStatus, Arrays.asList(
                        MonthlyReportStatusEnum.REVIEW_PASSED_WAITING_FOR_SPLIT.code(),
                        MonthlyReportStatusEnum.SPLITTED_IN_PROGRESS.code(),
                        MonthlyReportStatusEnum.COMPLETED.code()));

        KPDatabaseUtil.groupFieldsBy(wrapper, "monthlyReport", MonthlyReportPO.class);
        List<MonthlyReportReviewListCustomerPO> body = this.baseMapper.selectJoinList(MonthlyReportReviewListCustomerPO.class, wrapper);
        if (KPStringUtil.isEmpty(body)) return new HashMap<>();

        //查询用户信息
        List<String> userIds = body.stream().map(MonthlyReportReviewListCustomerPO::getUserIds)
                .filter(KPStringUtil::isNotEmpty)
                .flatMap(ids -> Arrays.stream(ids.split(",")).map(String::trim).filter(KPStringUtil::isNotEmpty))
                .distinct()
                .collect(Collectors.toList());
        Map<String, UserFeignPO> userMap = feignAuthUtil.queryUserListByIds(userIds).stream().collect(Collectors.toMap(UserFeignPO::getUserId, Function.identity()));


        body.forEach(item -> {
            // 新增：设置用户信息列表（id、姓名、头像）
            if (KPStringUtil.isNotEmpty(item.getUserIds())) {
                List<JSONObject> userList = Arrays.stream(item.getUserIds().split(","))
                        .map(String::trim).filter(KPStringUtil::isNotEmpty)
                        .map(userId -> {
                            UserFeignPO user = userMap.get(userId);
                            if (user == null) return null;
                            return new KPJSONFactoryUtil()
                                    .put("userId", user.getUserId())
                                    .put("realName", user.getRealName())
                                    .put("avatar", user.getAvatar())
                                    .build();
                        }).filter(Objects::nonNull).collect(Collectors.toList());

                item.setUserList(userList);
            }
        });

        return body.stream().collect(Collectors.groupingBy(MonthlyReportReviewListCustomerPO::getStatus));
    }


    /**
     * @Author lipeng
     * @Description 移动设置月计划状态
     * @Date 2025/10/1
     * @param parameter
     * @return void
     **/
    public void updateMoveStatus(JSONObject parameter) {
        MonthlyReportPO monthlyParameter = KPJsonUtil.toJavaObjectNotEmpty(parameter, MonthlyReportPO.class);
        KPVerifyUtil.notNull(monthlyParameter.getMonthlyId(), "请选择要移动的内容！");
        KPVerifyUtil.notNull(monthlyParameter.getStatus(), "请输入要移动的月计划状态！");

        MonthlyReportPO monthlyReportPO = this.baseMapper.selectById(monthlyParameter.getMonthlyId());
        if (monthlyReportPO == null) throw new KPServiceException("月计划不存在");
        if (monthlyParameter.getStatus().equals(MonthlyReportStatusEnum.COMPLETED.code()) && !monthlyReportPO.getProgress().equals(100)) throw new KPServiceException("月计划完成度未达到100%，请先完成所有周计划！");

        if (monthlyParameter.getStatus().equals(monthlyReportPO.getStatus()))
            throw new KPServiceException("月计划已处于该状态！");

        if (this.baseMapper.updateById(new MonthlyReportPO()
                .setMonthlyId(monthlyParameter.getMonthlyId())
                .setStatus(monthlyParameter.getStatus())) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);
    }
}
