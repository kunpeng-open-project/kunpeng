package com.kunpeng.framework.entity.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value="EhcacheHitListCustomerPO", description="EhcacheHitListCustomerPO")
public class EhcacheHitListCustomerPO {

    @ApiModelProperty(value = "缓存mapper", position = 1)
    private String mapper;

    @ApiModelProperty(value = "得到缓存对象占用内存的数量" , position = 2)
    private Long memoryStoreSize;

    @ApiModelProperty(value = "得到缓存对对象占用磁盘的数量", position = 3)
    private Integer diskStoreSize;

    @ApiModelProperty(value = "得到缓存读取的命中次数", position = 4)
    private Long cacheHits;

    @ApiModelProperty(value = "得到内存中缓存读取的命中次数", position = 5)
    private Long inMemoryHits;

    @ApiModelProperty(value = "得到磁盘中缓存读取的命中次数", position = 6)
    private Long onDiskHits;

    @ApiModelProperty(value = "得到缓存读取的丢失次数", position = 6)
    private Long cacheMisses;

    @ApiModelProperty(value = "得到缓存读取的已经被销毁的对象丢失次数", position = 8)
    private Long  evictionCount;

    @ApiModelProperty(value = "缓存的数据", position = 9)
    private List<EhcacheListCustomerPO> sqlCacheList;
}
