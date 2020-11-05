package com.smart.smartDB00.dao.v39;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface CreateViewMapper {
    List<String> getTableByLikeName(@Param("tableName") String tableName);

    Integer getTableExistStatus(@Param("tableName") String tableName);

    void dropView(@Param("tableName") String tableName);

    void createTimeIngView(@Param("tableNameList") List<String> tableNameList);

    Integer getTimeingMonthExistStatus(@Param("minDate") Date minDate);
}
