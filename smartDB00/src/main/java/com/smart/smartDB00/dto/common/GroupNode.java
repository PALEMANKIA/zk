package com.smart.smartDB00.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupNode implements Serializable {
    private String groupId;
    private String name;
    private Integer value;
}
