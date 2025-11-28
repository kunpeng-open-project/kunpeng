package com.kp.framework.modules.dict.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.kp.framework.common.cache.DictCache;
import com.kp.framework.common.cache.ProjectCache;
import com.kp.framework.constant.ReturnFinishedMessageConstant;
import com.kp.framework.entity.bo.DictionaryBO;
import com.kp.framework.enums.YesNoEnum;
import com.kp.framework.exception.KPServiceException;
import com.kp.framework.modules.dict.mapper.DictDataMapper;
import com.kp.framework.modules.dict.mapper.DictTypeMapper;
import com.kp.framework.modules.dict.po.DictDataPO;
import com.kp.framework.modules.dict.po.DictTypePO;
import com.kp.framework.modules.dict.po.customer.DictDataDetailsCustomerPO;
import com.kp.framework.modules.dict.po.param.DictDataEditParamPO;
import com.kp.framework.modules.dict.po.param.DictDataListParamPO;
import com.kp.framework.modules.project.po.ProjectPO;
import com.kp.framework.utils.kptool.KPJsonUtil;
import com.kp.framework.utils.kptool.KPStringUtil;
import com.kp.framework.utils.kptool.KPVerifyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author lipeng
 * @Description 字典数据表 服务实现类
 * @Date 2025-07-03
 **/
@Service
public class DictDataService extends ServiceImpl<DictDataMapper, DictDataPO> {

    @Autowired
    private DictTypeMapper dictTypeMapper;

    /**
     * @Author lipeng
     * @Description 查询字典数据列表
     * @Date 2025-07-03
     * @param dictDataListParamPO
     * @return java.util.List<DictDataPO>
     **/
    public List<DictDataPO> queryPageList(DictDataListParamPO dictDataListParamPO) {
        //搜索条件
        LambdaQueryWrapper<DictDataPO> queryWrapper = Wrappers.lambdaQuery(DictDataPO.class)
                .eq(KPStringUtil.isNotEmpty(dictDataListParamPO.getDictTypeId()), DictDataPO::getDictTypeId, dictDataListParamPO.getDictTypeId())
                .like(KPStringUtil.isNotEmpty(dictDataListParamPO.getLabel()), DictDataPO::getLabel, dictDataListParamPO.getLabel())
                .eq(KPStringUtil.isNotEmpty(dictDataListParamPO.getStatus()), DictDataPO::getStatus, dictDataListParamPO.getStatus());

        //分页和排序
        PageHelper.startPage(dictDataListParamPO.getPageNum(), dictDataListParamPO.getPageSize(), dictDataListParamPO.getOrderBy(DictDataPO.class));
        return this.baseMapper.selectList(queryWrapper);
    }


    /**
     * @Author lipeng
     * @Description 根据字典编码ID查询详情
     * @Date 2025-07-03
     * @param parameter
     * @return DictDataPO
     **/
    public DictDataDetailsCustomerPO queryDetailsById(JSONObject parameter) {
        DictDataPO dictDataPO = KPJsonUtil.toJavaObject(parameter, DictDataPO.class);
        KPVerifyUtil.notNull(dictDataPO.getDictDataId(), "请输入dictDataId");
        DictDataPO body = this.baseMapper.selectById(dictDataPO.getDictDataId());
        if (body == null) return null;

        DictDataDetailsCustomerPO dictDataDetailsCustomerPO = KPJsonUtil.toJavaObject(body, DictDataDetailsCustomerPO.class);
        DictTypePO dictTypePO = dictTypeMapper.selectById(dictDataDetailsCustomerPO.getDictTypeId());
        dictDataDetailsCustomerPO.setDictName(dictTypePO.getDictName()).setDictType(dictTypePO.getDictType());
        return dictDataDetailsCustomerPO;
    }


    /**
     * @Author lipeng
     * @Description 新增字典数据
     * @Date 2025-07-03
     * @param dictDataEditParamPO
     * @return void
     **/
    public void saveDictData(DictDataEditParamPO dictDataEditParamPO) {
        DictDataPO dictDataPO = KPJsonUtil.toJavaObjectNotEmpty(dictDataEditParamPO, DictDataPO.class);

        DictTypePO dictTypePO = dictTypeMapper.selectById(dictDataPO.getDictTypeId());
        if (KPStringUtil.isEmpty(dictTypePO)) throw new KPServiceException("字典类型异常！");

        if (this.baseMapper.selectList(Wrappers.lambdaQuery(DictDataPO.class)
                .eq(DictDataPO::getDictTypeId, dictDataPO.getDictTypeId())
                .eq(DictDataPO::getValue, dictDataPO.getValue())
        ).size() > 0)
            throw new KPServiceException("数据键值已存在，请勿重复添加");


        DictDataPO data = this.baseMapper.selectOne(Wrappers.lambdaQuery(DictDataPO.class)
                .eq(DictDataPO::getDictTypeId, dictDataPO.getDictTypeId())
                .orderByDesc(DictDataPO::getSort).last(" limit 1"));

        if (data == null) {
            dictDataPO.setSort(1);
        } else {
            dictDataPO.setSort(data.getSort() + 1);
        }

        if (this.baseMapper.insert(dictDataPO) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);

        dictDataEditParamPO.setDictDataId(dictDataPO.getDictDataId());
        DictCache.clearAll();
    }


    /**
     * @Author lipeng
     * @Description 修改字典数据
     * @Date 2025-07-03
     * @param dictDataEditParamPO
     * @return void
     **/
    public void updateDictData(DictDataEditParamPO dictDataEditParamPO) {
        DictDataPO dictDataPO = KPJsonUtil.toJavaObjectNotEmpty(dictDataEditParamPO, DictDataPO.class);

        DictTypePO dictTypePO = dictTypeMapper.selectById(dictDataPO.getDictTypeId());
        if (KPStringUtil.isEmpty(dictTypePO)) throw new KPServiceException("字典类型异常！");

        if (this.baseMapper.selectList(Wrappers.lambdaQuery(DictDataPO.class)
                .eq(DictDataPO::getDictTypeId, dictDataPO.getDictTypeId())
                .eq(DictDataPO::getValue, dictDataPO.getValue())
                .ne(DictDataPO::getDictDataId, dictDataPO.getDictDataId())
        ).size() > 0)
            throw new KPServiceException("数据键值已存在，请修改数据键值");

        if (this.baseMapper.updateById(dictDataPO) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);

        DictCache.clearAll();
    }


    /**
     * @Author lipeng
     * @Description 批量删除字典数据
     * @Date 2025-07-03
     * @param ids
     * @return String
     **/
    public String batchRemove(List<String> ids) {
        if (KPStringUtil.isEmpty(ids)) throw new KPServiceException("请选择要删除的内容！");

        Integer row = this.baseMapper.deleteBatchIds(ids);
        if (row == 0) throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);

        DictCache.clearAll();
        return KPStringUtil.format("删除成功{0}条数据", row);
    }


    /**
     * @Author lipeng
     * @Description 设置字典数据状态
     * @Date 2025/7/3
     * @param parameter
     * @return void
     **/
    public void doStatus(JSONObject parameter) {
        DictDataPO dictDataParameter = KPJsonUtil.toJavaObject(parameter, DictDataPO.class);
        KPVerifyUtil.notNull(dictDataParameter.getDictDataId(), "请输入字典数据Id");

        DictDataPO dictDataPO = this.baseMapper.selectById(dictDataParameter.getDictDataId());
        if (dictDataPO == null) throw new KPServiceException("字典数据不存在");

        dictDataPO.setStatus(dictDataPO.getStatus().equals(YesNoEnum.YES.code()) ? YesNoEnum.NO.code() : YesNoEnum.YES.code());

        if (this.baseMapper.updateById(dictDataPO) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);
    }


    /**
     * @Author lipeng
     * @Description 查询数据字典
     * @Date 2025/7/4
     * @param parameter
     * @return java.util.List<com.kp.framework.entity.bo.DictionaryBO>
     **/
    public List<DictionaryBO> queryDictData(JSONObject parameter) {
        KPVerifyUtil.notNull(parameter.getString("dictType"), "请输入数据字典类型！");
        KPVerifyUtil.notNull(parameter.getString("projectCode"), "请输入项目编号！");

        ProjectPO projectPO = ProjectCache.getProjectByCode(parameter.getString("projectCode"));
        if (KPStringUtil.isEmpty(projectPO)) throw new KPServiceException("项目不存在, 请输入正确的项目编号！");

        List<DictDataPO> dictDataPOList = DictCache.getDictData(projectPO.getProjectCode(), parameter.getString("dictType"));
        if (KPStringUtil.isEmpty(dictDataPOList)) return new ArrayList<>();


        List<DictionaryBO> body = new ArrayList<>();
        dictDataPOList.forEach(dictDataPO -> {
            body.add(new DictionaryBO().setLabel(dictDataPO.getLabel()).setValue(dictDataPO.getValue()));
        });
        return body;
    }


    /**
     * @Author lipeng
     * @Description 批量查询数据字典
     * @Date 2025/7/4
     * @param parameter
     * @return java.util.Map<java.lang.String,java.util.List<com.kp.framework.entity.bo.DictionaryBO>>
     **/
    public Map<String, List<DictionaryBO>> queryDictDatas(JSONObject parameter) {
        KPVerifyUtil.notNull(parameter.getJSONArray("dictTypes"), "请输入数据字典类型集合！");
        KPVerifyUtil.notNull(parameter.getString("projectCode"), "请输入项目编号！");

        ProjectPO projectPO = ProjectCache.getProjectByCode(parameter.getString("projectCode"));
        if (KPStringUtil.isEmpty(projectPO)) throw new KPServiceException("项目不存在, 请输入正确的项目编号！");

        Map<String, List<DictDataPO>> map = DictCache.getDictData(projectPO.getProjectCode(), parameter.getList("dictTypes", String.class));
        if (map == null || map.size() == 0) return new HashMap<>();

        Map<String, List<DictionaryBO>> body = new HashMap<>();

        map.forEach((key, dictDataPOList) -> {
            List<DictionaryBO> dictionaryBOList = new ArrayList<>();
            dictDataPOList.forEach(dictDataPO -> {
                dictionaryBOList.add(new DictionaryBO().setLabel(dictDataPO.getLabel()).setValue(dictDataPO.getValue()));
            });

            body.put(key, dictionaryBOList);
        });
        return body;
    }
}
