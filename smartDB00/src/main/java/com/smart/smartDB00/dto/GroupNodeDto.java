package com.smart.smartDB00.dto;

import com.smart.smartDB00.dto.common.GroupNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupNodeDto extends GroupNode {
    private String missingDay;
}
