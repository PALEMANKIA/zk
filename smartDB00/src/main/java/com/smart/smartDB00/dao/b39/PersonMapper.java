package com.smart.smartDB00.dao.b39;

import org.apache.ibatis.annotations.Mapper;
import com.smart.smartDB00.domain.Person;

import java.util.List;

@Mapper
public interface PersonMapper {
    List<Person> selectAll();
}
