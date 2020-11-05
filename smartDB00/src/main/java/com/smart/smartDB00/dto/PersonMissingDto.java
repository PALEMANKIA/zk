package com.smart.smartDB00.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.smart.smartDB00.domain.PersonMissing;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonMissingDto extends PersonMissing implements Serializable {
    private Integer index;//下标
    private String lastBehaviorType;//最新行为类型
    private String newestBehaviorType;//最新行为类型
    private String personNo;//学号
    private String college;//学院
    private String specialty;//专业
    private String grade;//年级
    private String name;//姓名
    private String sex;//性别
    private Integer missingDay;//失联天数
    private String status;//目前状态
    private List<CheckRecordDto> checkRecordList;
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public String getLastBehaviorType() {
		return lastBehaviorType;
	}
	public void setLastBehaviorType(String lastBehaviorType) {
		this.lastBehaviorType = lastBehaviorType;
	}
	public String getNewestBehaviorType() {
		return newestBehaviorType;
	}
	public void setNewestBehaviorType(String newestBehaviorType) {
		this.newestBehaviorType = newestBehaviorType;
	}
	public String getPersonNo() {
		return personNo;
	}
	public void setPersonNo(String personNo) {
		this.personNo = personNo;
	}
	public String getCollege() {
		return college;
	}
	public void setCollege(String college) {
		this.college = college;
	}
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Integer getMissingDay() {
		return missingDay;
	}
	public void setMissingDay(Integer missingDay) {
		this.missingDay = missingDay;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<CheckRecordDto> getCheckRecordList() {
		return checkRecordList;
	}
	public void setCheckRecordList(List<CheckRecordDto> checkRecordList) {
		this.checkRecordList = checkRecordList;
	}

}
