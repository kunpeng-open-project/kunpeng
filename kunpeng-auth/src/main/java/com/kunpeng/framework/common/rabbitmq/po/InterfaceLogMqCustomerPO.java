package com.kunpeng.framework.common.rabbitmq.po;

import com.kunpeng.framework.entity.bo.OperationUserMessageBO;
import com.kunpeng.framework.modules.logRecord.po.InterfaceLogPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author lipeng
 * @Description
 * @Date 2025/5/16
 * @return
 **/
@Data
@Accessors(chain = true)
@ApiModel(value="InterfaceLogMqCustomerPO", description="")
public class InterfaceLogMqCustomerPO extends InterfaceLogPO {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "mq的deliveryTag")
    private long deliveryTag;

    @ApiModelProperty(value = "用户信息")
    private OperationUserMessageBO userMessage;

}
