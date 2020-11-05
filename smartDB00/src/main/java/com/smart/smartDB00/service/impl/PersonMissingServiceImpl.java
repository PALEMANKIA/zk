package com.smart.smartDB00.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smart.smartDB00.dao.b39.CheckRecordMapper;
import com.smart.smartDB00.dao.b39.PersonMapper;
import com.smart.smartDB00.dao.b39.PersonMissingMapper;
import com.smart.smartDB00.domain.*;
import com.smart.smartDB00.dto.CheckRecordDto;
import com.smart.smartDB00.dto.GroupNodeDto;
import com.smart.smartDB00.dto.PersonMissingDto;
import com.smart.smartDB00.dto.common.GroupNode;
import com.smart.smartDB00.dto.common.SelectNode;
import com.smart.smartDB00.service.PersonMissingService;
import com.smart.smartDB00.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Vector;

@Service
public class PersonMissingServiceImpl implements PersonMissingService {
    @Autowired
    private PersonMapper personMapper;
    @Autowired
    private PersonMissingMapper personMissingMapper;
    @Autowired
    private CheckRecordMapper checkRecordMapper;

    @Override
    public PageInfo<Person> getPersonList(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Person> list = personMapper.selectAll();
        return new PageInfo(list);
    }

    /**
     * 把无流水记录的记录添加到失联表里
     *
     * @param nullList es查询不到的人员集合
     */
    @Override
    public void insertNullRecord(Vector<Long> nullList) {
        List<List<Long>> subNullList = CollectionUtils.splitList(nullList, 100);
        for (List<Long> list : subNullList) {
            List<Long> nullRecords = personMissingMapper.getNullRecordsByMissing(list);//查询无记录人员是否已经存在失联表里
            for (Long personId : nullRecords) {
                PersonMissing missing = new PersonMissing();
                missing.setId(personMissingMapper.getNextVal());
                missing.setPersonId(personId);
                missing.setStatusCode(0);// 0 无记录
                personMissingMapper.insertSelective(missing);
            }
        }
    }

    @Override
    public void insertMissingRecord(Vector<RecordData> insertList, List<Machine> machineList) {
        List<List<RecordData>> subInsertList = CollectionUtils.splitList(insertList, 100);
        for (List<RecordData> list : subInsertList) {
            List<PersonMissingDto> updateRecordIndexs = personMissingMapper.getMissingRecordDtoByNullMissing(list);//查询list(正常人员)是否之前是无记录人员
            for (PersonMissingDto missingDto : updateRecordIndexs) {
                RecordData recordData = list.get(missingDto.getIndex());
                Machine machine = getMachineListById(machineList, recordData.getMachineId());
                missingDto.setLastTime(recordData.getDate());
                missingDto.setLastMachineId(recordData.getMachineId());
                missingDto.setStatusCode(2);
                if (recordData.getMoney() != null) {
                    missingDto.setLastOperation(recordData.getMoney().toString());
                }
                if (machine != null) {
                    missingDto.setLastAddress(machine.getDetailedAddress());
                    missingDto.setLastBehaviorTypeCode(machine.getBehaviorType());
                }
                personMissingMapper.updateSelective(missingDto);//把无记录人员记录改为失联记录人员
            }
            List<Integer> insertRecordIndexs = personMissingMapper.getMissingRecordIndexsByMissing(list);//查询失联人员是否已经存在
            for (Integer index : insertRecordIndexs) {
                RecordData recordData = list.get(index);
                Machine machine = getMachineListById(machineList, recordData.getMachineId());
                PersonMissing missing = new PersonMissing();
                missing.setId(personMissingMapper.getNextVal());
                missing.setPersonId(recordData.getPersonId());
                missing.setLastTime(recordData.getDate());
                missing.setLastMachineId(recordData.getMachineId());
                missing.setStatusCode(2);
                if (recordData.getMoney() != null) {
                    missing.setLastOperation(recordData.getMoney().toString());
                }
                if (machine != null) {
                    missing.setLastAddress(machine.getDetailedAddress());
                    missing.setLastBehaviorTypeCode(machine.getBehaviorType());
                }
                personMissingMapper.insertSelective(missing);//不存在就添加失联记录
            }
        }
    }

    @Override
    public void updateMissingRecord(Vector<RecordData> updateList, List<Machine> machineList) {
        List<List<RecordData>> subUpdateList = CollectionUtils.splitList(updateList, 100);
        for (List<RecordData> list : subUpdateList) {
            List<PersonMissingDto> updateRecordIndexs = personMissingMapper.getMissingRecordDtoByMissing(list);//查询list(正常人员)是否之前是失联人员
            for (PersonMissingDto missingDto : updateRecordIndexs) {
                RecordData recordData = list.get(missingDto.getIndex());
                Machine machine = getMachineListById(machineList, recordData.getMachineId());
                missingDto.setNewestTime(recordData.getDate());
                missingDto.setNewestMachineId(recordData.getMachineId());
                if (recordData.getMoney() != null) {
                    missingDto.setNewestOperation(recordData.getMoney().toString());
                }
                if (machine != null) {
                    missingDto.setNewestAddress(machine.getDetailedAddress());
                    missingDto.setNewestBehaviorTypeCode(machine.getBehaviorType());
                }
                missingDto.setStatusCode(1);
                personMissingMapper.updateSelective(missingDto);//把失联人员记录改为正常
            }
        }
    }

    @Override
    public List<GroupNode> getCollegeMissingGroup(Date beginDate, Date endDate) {
        return personMissingMapper.getCollegeMissingGroup(beginDate, endDate);
    }

    @Override
    public List<GroupNode> getSpecialtyMissingGroup(Date beginDate, Date endDate) {
        return personMissingMapper.getSpecialtyMissingGroup(beginDate, endDate);
    }

    @Override
    public List<GroupNode> getGradeMissingGroup(Date beginDate, Date endDate) {
        return personMissingMapper.getGradeMissingGroup(beginDate, endDate);
    }

    @Override
    public List<GroupNode> getSexMissingGroup(Date beginDate, Date endDate) {
        return personMissingMapper.getSexMissingGroup(beginDate, endDate);
    }

    @Override
    public Object[][] getCollegeMissingDayGroup(Date beginDate, Date endDate) {
        List<GroupNodeDto> dtoList = personMissingMapper.getCollegeMissingDayGroup(beginDate, endDate);
        Object[][] dataList = new Object[dtoList.size()][3];
        for (int i = 0; i < dtoList.size(); i++) {
            dataList[i][0] = dtoList.get(i).getValue();
            dataList[i][1] = dtoList.get(i).getName();
            dataList[i][2] = dtoList.get(i).getMissingDay();
        }
        return dataList;
    }

    @Override
    public PageInfo<PersonMissingDto> selectMissingRecord(String collegeCode, String specialtyCode, String gradeCode, String personNo, Date beginDate, Date endDate, Integer pageNum, Integer pageSize) {
        if (!StringUtils.isEmpty(personNo)) {
            personNo = "%".concat(personNo).concat("%");
        } else {
            personNo = null;
        }
        if (StringUtils.isEmpty(collegeCode)) {
            collegeCode = null;
        }
        if (StringUtils.isEmpty(specialtyCode)) {
            specialtyCode = null;
        }
        if (StringUtils.isEmpty(gradeCode)) {
            gradeCode = null;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<PersonMissingDto> list = personMissingMapper.selectMissingRecord(collegeCode, specialtyCode, gradeCode, personNo, beginDate, endDate);
        return new PageInfo(list);
    }

    @Override
    public PersonMissingDto getMissingRecord(Long id) {
        PersonMissingDto dto = personMissingMapper.getMissingRecord(id);
        List<CheckRecordDto> checkRecordList = checkRecordMapper.selectCheckRecordByMissingId(id);
        dto.setCheckRecordList(checkRecordList);
        return dto;
    }

    @Override
    @Transactional
    public void saveReviseRecord(Long missingId, Long checkPersonId, String remark, Integer statusCode) {
        PersonMissingDto dto = personMissingMapper.getMissingRecord(missingId);
        Integer checkStatusCode = dto.getStatusCode();

        PersonMissing personMissing = new PersonMissing();
        personMissing.setId(missingId);
        personMissing.setStatusCode(statusCode);
        personMissingMapper.updateSelective(personMissing);//修改失联记录当前状态

        Date sysdate = new Date();
        Long id = checkRecordMapper.getNextVal();
        CheckRecord checkRecord = new CheckRecord();
        checkRecord.setId(id);
        checkRecord.setMissingId(missingId);
        checkRecord.setCheckPersonId(checkPersonId);
        checkRecord.setCheckStatusCode(checkStatusCode);
        checkRecord.setStatusCode(statusCode);
        checkRecord.setRemark(remark);
        checkRecord.setCheckDate(sysdate);
        checkRecordMapper.insertSelective(checkRecord);//保存校正记录
    }

    @Override
    public List<SelectNode> selectCollege() {
        return personMissingMapper.selectCollege();
    }

    @Override
    public List<SelectNode> selectSpecialty() {
        return personMissingMapper.selectSpecialty();
    }

    @Override
    public List<SelectNode> selectGrade() {
        return personMissingMapper.selectGrade();
    }

    private Machine getMachineListById(List<Machine> machineList, Long id) {
        for (Machine machine : machineList) {
            if (machine.getId().equals(id)) {
                return machine;
            }
        }
        return null;
    }

}
