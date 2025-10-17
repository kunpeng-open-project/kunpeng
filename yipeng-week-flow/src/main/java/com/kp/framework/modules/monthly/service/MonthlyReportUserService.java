package com.kp.framework.modules.monthly.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kp.framework.modules.monthly.mapper.MonthlyReportUserMapper;
import com.kp.framework.modules.monthly.po.MonthlyReportUserPO;
import org.springframework.stereotype.Service;

/**
 * @Author lipeng
 * @Description 月度计划责任人信息表 服务实现类
 * @Date 2025-07-25
**/
@Service
public class MonthlyReportUserService extends ServiceImpl<MonthlyReportUserMapper, MonthlyReportUserPO> {


//    /**
//     * @Author lipeng
//     * @Description 查询月度计划责任人信息列表
//     * @Date 2025-07-25
//     * @param monthlyReportUserListParamPO
//     * @return java.util.List<MonthlyReportUserPO>
//    **/
//    public List<MonthlyReportUserPO> queryPageList(MonthlyReportUserListParamPO monthlyReportUserListParamPO){
//        //搜索条件
//        LambdaQueryWrapper<MonthlyReportUserPO> queryWrapper = Wrappers.lambdaQuery(MonthlyReportUserPO.class)
//                .like(KPStringUtil.isNotEmpty(monthlyReportUserListParamPO.getMruId()), MonthlyReportUserPO::getMruId, monthlyReportUserListParamPO.getMruId())
//                .like(KPStringUtil.isNotEmpty(monthlyReportUserListParamPO.getUserId()), MonthlyReportUserPO::getUserId, monthlyReportUserListParamPO.getUserId())
//                .like(KPStringUtil.isNotEmpty(monthlyReportUserListParamPO.getUserName()), MonthlyReportUserPO::getUserName, monthlyReportUserListParamPO.getUserName())
//                .like(KPStringUtil.isNotEmpty(monthlyReportUserListParamPO.getRemark()), MonthlyReportUserPO::getRemark, monthlyReportUserListParamPO.getRemark());
//
//        //分页和排序
//        PageHelper.startPage(monthlyReportUserListParamPO.getPageNum(), monthlyReportUserListParamPO.getPageSize(), monthlyReportUserListParamPO.getOrderBy(MonthlyReportUserPO.class));
//        return this.baseMapper.selectList(queryWrapper);
//    }
//
//
//    /**
//     * @Author lipeng
//     * @Description 根据月度计划责任人Id查询详情
//     * @Date 2025-07-25
//     * @param parameter
//     * @return MonthlyReportUserPO
//    **/
//    public MonthlyReportUserPO queryDetailsById(JSONObject parameter){
//        MonthlyReportUserPO monthlyReportUserPO = KPJsonUtil.toJavaObject(parameter, MonthlyReportUserPO.class);
//        KPVerifyUtil.notNull(monthlyReportUserPO.getMruId(), "请输入mruId");
//        return this.baseMapper.selectById(monthlyReportUserPO.getMruId());
//    }
//
//
//    /**
//     * @Author lipeng
//     * @Description 新增月度计划责任人信息
//     * @Date 2025-07-25
//     * @param monthlyReportUserEditParamPO
//     * @return void
//    **/
//    public void saveMonthlyReportUser(MonthlyReportUserEditParamPO monthlyReportUserEditParamPO){
//        MonthlyReportUserPO monthlyReportUserPO = KPJsonUtil.toJavaObjectNotEmpty(monthlyReportUserEditParamPO, MonthlyReportUserPO.class);
//
//        if (this.baseMapper.insert(monthlyReportUserPO) == 0)
//            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);
//    }
//
//
//    /**
//     * @Author lipeng
//     * @Description 修改月度计划责任人信息
//     * @Date 2025-07-25
//     * @param monthlyReportUserEditParamPO
//     * @return void
//    **/
//    public void updateMonthlyReportUser(MonthlyReportUserEditParamPO monthlyReportUserEditParamPO){
//        MonthlyReportUserPO monthlyReportUserPO = KPJsonUtil.toJavaObjectNotEmpty(monthlyReportUserEditParamPO, MonthlyReportUserPO.class);
//
//        if (this.baseMapper.updateById(monthlyReportUserPO) == 0)
//            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);
//    }
//
//
//    /**
//     * @Author lipeng
//     * @Description 批量删除月度计划责任人信息
//     * @Date 2025-07-25
//     * @param ids
//     * @return String
//    **/
//    public String batchRemove(List<String> ids){
//        if(KPStringUtil.isEmpty(ids)) throw new KPServiceException("请选择要删除的内容！");
//
//        Integer row = this.baseMapper.deleteBatchIds(ids);
//        if(row == 0) throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);
//
//        return KPStringUtil.format("删除成功{0}条数据", row);
//    }
}
