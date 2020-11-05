package com.smart.smartDB00.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonMissing implements Serializable {
    private Long id;//id
    private Long personId;//人员id
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastTime;//最后打卡时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date newestTime;//最新打卡时间
    private String lastAddress;//最后地理位置
    private String newestAddress;//最新地理位置
    private Long lastMachineId;//最后设备ID
    private Long newestMachineId;//最新设备ID
    private Integer lastBehaviorTypeCode;//最后行为类型代码
    private Integer newestBehaviorTypeCode;//最新行为类型
    private String lastOperation;//最后出现操作
    private String newestOperation;//最新出现操作
    private String lastResult;//最后出现结果
    private String newestResult;//最新出现结果
    private Integer statusCode;//目前状态

}
