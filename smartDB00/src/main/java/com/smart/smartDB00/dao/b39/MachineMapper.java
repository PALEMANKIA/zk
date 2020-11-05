package com.smart.smartDB00.dao.b39;

import org.apache.ibatis.annotations.Mapper;
import com.smart.smartDB00.domain.Machine;

import java.util.List;

@Mapper
public interface MachineMapper {
    List<Machine> selectAll();
}
