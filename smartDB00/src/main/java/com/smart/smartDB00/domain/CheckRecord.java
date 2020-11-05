package com.smart.smartDB00.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckRecord {
    private Long id;//id
    private Long missingId;//失联人员ID
    private Long checkPersonId;//校正人员ID
    private Integer statusCode;//目前状态
    private Integer checkStatusCode;//校正状态
    private String remark;//备注
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date checkDate;//校正

}
