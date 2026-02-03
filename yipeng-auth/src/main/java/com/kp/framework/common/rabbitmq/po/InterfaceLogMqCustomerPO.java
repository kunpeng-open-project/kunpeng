package com.kp.framework.common.rabbitmq.po;

import com.kp.framework.entity.bo.OperationUserMessageBO;
import com.kp.framework.modules.logRecord.po.InterfaceLogPO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@Schema(name = "InterfaceLogMqCustomerPO", description = "InterfaceLogMqCustomerPO")
public class InterfaceLogMqCustomerPO extends InterfaceLogPO {

    @Schema(description = "mq的deliveryTag")
    private long deliveryTag;

    @Schema(description = "用户信息")
    private OperationUserMessageBO userMessage;

}
