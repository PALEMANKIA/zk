package com.smart.smartDB00.dao.b39;

import org.apache.ibatis.annotations.Mapper;
import com.smart.smartDB00.domain.Address;

import java.util.List;

@Mapper
public interface AddressMapper {
    List<Address> selectAll();
}
