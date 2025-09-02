package com.kp.framework.entity.bo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DictionaryBO {

    private String label;

    private String value;
}
