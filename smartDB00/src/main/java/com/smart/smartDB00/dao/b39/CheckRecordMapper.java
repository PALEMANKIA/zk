package com.smart.smartDB00.dao.b39;

import com.smart.smartDB00.dto.CheckRecordDto;
import org.apache.ibatis.annotations.Mapper;
import com.smart.smartDB00.domain.CheckRecord;

import java.util.List;


@Mapper
public interface CheckRecordMapper {
    Long getNextVal();

    void insertSelective(CheckRecord checkRecord);

    List<CheckRecordDto> selectCheckRecordByMissingId(Long missingId);
}
