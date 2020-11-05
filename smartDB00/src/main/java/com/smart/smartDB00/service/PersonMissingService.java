package com.smart.smartDB00.service;

import com.github.pagehelper.PageInfo;
import com.smart.smartDB00.dto.PersonMissingDto;
import com.smart.smartDB00.dto.common.GroupNode;
import com.smart.smartDB00.dto.common.SelectNode;
import com.smart.smartDB00.domain.Machine;
import com.smart.smartDB00.domain.Person;
import com.smart.smartDB00.domain.RecordData;

import java.util.Date;
import java.util.List;
import java.util.Vector;

public interface PersonMissingService {
    PageInfo<Person> getPersonList(Integer pageNum, Integer pageSize);

    void insertNullRecord(Vector<Long> nullList);

    void insertMissingRecord(Vector<RecordData> insertList,List<Machine> machineList);

    void updateMissingRecord(Vector<RecordData> updateList,List<Machine> machineList);

    List<GroupNode> getCollegeMissingGroup(Date beginDate, Date endDate);

    List<GroupNode> getSpecialtyMissingGroup(Date beginDate, Date endDate);

    List<GroupNode> getGradeMissingGroup(Date beginDate, Date endDate);

    List<GroupNode> getSexMissingGroup(Date beginDate, Date endDate);

    Object[][] getCollegeMissingDayGroup(Date beginDate, Date endDate);

    PageInfo<PersonMissingDto> selectMissingRecord(String collegeCode, String specialtyCode, String gradeCode, String personNo, Date beginDate, Date endDate, Integer pageNum, Integer pageSize);

    PersonMissingDto getMissingRecord(Long id);

    void saveReviseRecord(Long missingId, Long checkPersonId, String remark, Integer statusCode);

    List<SelectNode> selectCollege();

    List<SelectNode> selectSpecialty();

    List<SelectNode> selectGrade();
}
