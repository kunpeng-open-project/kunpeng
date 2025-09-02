package com.kp.framework.entity.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class DictionaryChildrenBO {

    private String label;

    private String value;

    private List<DictionaryChildrenBO> children;

}
