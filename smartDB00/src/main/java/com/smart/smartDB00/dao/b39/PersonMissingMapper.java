package com.smart.smartDB00.dao.b39;

import com.smart.smartDB00.dto.PersonMissingDto;
import com.smart.smartDB00.dto.common.GroupNode;
import com.smart.smartDB00.dto.common.SelectNode;
import org.apache.ibatis.annotations.Mapper;
import com.smart.smartDB00.domain.PersonMissing;
import com.smart.smartDB00.domain.RecordData;
import com.smart.smartDB00.dto.GroupNodeDto;

import java.util.Date;
import java.util.List;

@Mapper
public interface PersonMissingMapper {
    Long getNextVal();

    void insertSelective(PersonMissing record);

    void updateSelective(PersonMissing record);

    List<Long> getNullRecordsByMissing(List<Long> list);

    List<Integer> getMissingRecordIndexsByMissing(List<RecordData> list);

    List<PersonMissingDto> getMissingRecordDtoByNullMissing(List<RecordData> list);

    List<PersonMissingDto> getMissingRecordDtoByMissing(List<RecordData> list);

    List<GroupNode> getCollegeMissingGroup(Date beginDate, Date endDate);

    List<GroupNode> getSpecialtyMissingGroup(Date beginDate, Date endDate);

    List<GroupNode> getGradeMissingGroup(Date beginDate, Date endDate);

    List<GroupNode> getSexMissingGroup(Date beginDate, Date endDate);

    List<GroupNodeDto> getCollegeMissingDayGroup(Date beginDate, Date endDate);

    List<PersonMissingDto> selectMissingRecord(String collegeCode, String specialtyCode, String gradeCode, String personNo, Date beginDate, Date endDate);

    PersonMissingDto getMissingRecord(Long id);

    List<SelectNode> selectCollege();

    List<SelectNode> selectSpecialty();

    List<SelectNode> selectGrade();
}
