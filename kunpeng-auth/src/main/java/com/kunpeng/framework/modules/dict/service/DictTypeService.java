package com.kunpeng.framework.modules.dict.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.kunpeng.framework.common.cache.DictCache;
import com.kunpeng.framework.constant.ReturnFinishedMessageConstant;
import com.kunpeng.framework.enums.YesNoEnum;
import com.kunpeng.framework.exception.KPServiceException;
import com.kunpeng.framework.modules.dict.mapper.DictDataMapper;
import com.kunpeng.framework.modules.dict.mapper.DictTypeMapper;
import com.kunpeng.framework.modules.dict.po.DictDataPO;
import com.kunpeng.framework.modules.dict.po.DictTypePO;
import com.kunpeng.framework.modules.dict.po.param.DictTypeEditParamPO;
import com.kunpeng.framework.modules.dict.po.param.DictTypeListParamPO;
import com.kunpeng.framework.utils.kptool.KPJsonUtil;
import com.kunpeng.framework.utils.kptool.KPStringUtil;
import com.kunpeng.framework.utils.kptool.KPVerifyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author lipeng
 * @Description 字典类型表 服务实现类
 * @Date 2025-07-03
 **/
@Service
public class DictTypeService extends ServiceImpl<DictTypeMapper, DictTypePO> {

    @Autowired
    private DictDataMapper dictDataMapper;

    /**
     * @Author lipeng
     * @Description 查询字典类型列表
     * @Date 2025-07-03
     * @param dictTypeListParamPO
     * @return java.util.List<DictTypePO>
     **/
    public List<DictTypePO> queryPageList(DictTypeListParamPO dictTypeListParamPO) {
        //搜索条件
        LambdaQueryWrapper<DictTypePO> queryWrapper = Wrappers.lambdaQuery(DictTypePO.class)
                .like(KPStringUtil.isNotEmpty(dictTypeListParamPO.getDictName()), DictTypePO::getDictName, dictTypeListParamPO.getDictName())
                .like(KPStringUtil.isNotEmpty(dictTypeListParamPO.getDictType()), DictTypePO::getDictType, dictTypeListParamPO.getDictType())
                .eq(KPStringUtil.isNotEmpty(dictTypeListParamPO.getStatus()), DictTypePO::getStatus, dictTypeListParamPO.getStatus());
        //分页和排序
        PageHelper.startPage(dictTypeListParamPO.getPageNum(), dictTypeListParamPO.getPageSize(), dictTypeListParamPO.getOrderBy(DictTypePO.class));
        return this.baseMapper.selectList(queryWrapper);
    }


    /**
     * @Author lipeng
     * @Description 根据字典类型ID查询详情
     * @Date 2025-07-03
     * @param parameter
     * @return DictTypePO
     **/
    public DictTypePO queryDetailsById(JSONObject parameter) {
        DictTypePO dictTypePO = KPJsonUtil.toJavaObject(parameter, DictTypePO.class);
        KPVerifyUtil.notNull(dictTypePO.getDictTypeId(), "请输入dictTypeId");
        return this.baseMapper.selectById(dictTypePO.getDictTypeId());
    }


    /**
     * @Author lipeng
     * @Description 新增字典类型
     * @Date 2025-07-03
     * @param dictTypeEditParamPO
     * @return void
     **/
    public void saveDictType(DictTypeEditParamPO dictTypeEditParamPO) {
        DictTypePO dictTypePO = KPJsonUtil.toJavaObjectNotEmpty(dictTypeEditParamPO, DictTypePO.class);

        if (this.baseMapper.selectList(Wrappers.lambdaQuery(DictTypePO.class)
                .eq(DictTypePO::getDictType, dictTypePO.getDictType())
        ).size() > 0)
            throw new KPServiceException("字典类型已存在，请勿重复添加");


        if (this.baseMapper.insert(dictTypePO) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);

        DictCache.clearAll();
    }


    /**
     * @Author lipeng
     * @Description 修改字典类型
     * @Date 2025-07-03
     * @param dictTypeEditParamPO
     * @return void
     **/
    public void updateDictType(DictTypeEditParamPO dictTypeEditParamPO) {
        DictTypePO dictTypePO = KPJsonUtil.toJavaObjectNotEmpty(dictTypeEditParamPO, DictTypePO.class);

        if (this.baseMapper.selectList(Wrappers.lambdaQuery(DictTypePO.class)
                .eq(DictTypePO::getDictType, dictTypePO.getDictType())
                .ne(DictTypePO::getDictTypeId, dictTypePO.getDictTypeId())
        ).size() > 0)
            throw new KPServiceException("字典类型已存在，请修改字典类型");

        if (this.baseMapper.updateById(dictTypePO) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);

        DictCache.clearAll();
    }


    /**
     * @Author lipeng
     * @Description 批量删除字典类型
     * @Date 2025-07-03
     * @param ids
     * @return String
     **/
    public String batchRemove(List<String> ids) {
        if (KPStringUtil.isEmpty(ids)) throw new KPServiceException("请选择要删除的内容！");

        //查询角色下是否有用户
        List<DictDataPO> dictDataList = dictDataMapper.selectList(Wrappers.lambdaQuery(DictDataPO.class).in(DictDataPO::getDictTypeId, ids));
        if (dictDataList.size() != 0) throw new KPServiceException(KPStringUtil.format("{0} 下存在字典数据, 不允许删除",  this.baseMapper.selectById(dictDataList.get(0).getDictTypeId()).getDictName()));

        Integer row = this.baseMapper.deleteBatchIds(ids);
        if (row == 0) throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);

        DictCache.clearAll();
        return KPStringUtil.format("删除成功{0}条数据", row);
    }


    /**
     * @Author lipeng
     * @Description 设置字典类型状态
     * @Date 2025/7/3
     * @param parameter
     * @return void
     **/
    public void doStatus(JSONObject parameter) {
        DictTypePO dictTypeParameter = KPJsonUtil.toJavaObject(parameter, DictTypePO.class);
        KPVerifyUtil.notNull(dictTypeParameter.getDictTypeId(), "请输入字典类型Id");

        DictTypePO dictTypePO = this.baseMapper.selectById(dictTypeParameter.getDictTypeId());
        if (dictTypePO == null) throw new KPServiceException("字典类型不存在");

        dictTypePO.setStatus(dictTypePO.getStatus().equals(YesNoEnum.YES.code())? YesNoEnum.NO.code():YesNoEnum.YES.code());

        if (this.baseMapper.updateById(dictTypePO) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);
    }
}
