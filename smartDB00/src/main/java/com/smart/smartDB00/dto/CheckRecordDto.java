package com.smart.smartDB00.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.smart.smartDB00.domain.CheckRecord;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckRecordDto extends CheckRecord {
    private String checkStatus;//校正状态
    private String status;//状态
    private String checkPersonName;//校正人员
    private String checkPersonNo;//校正人员号
}
