package com.smart.smartDB00.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SelectNode implements Serializable {
    private String selectId;
    private String selectName;
}
