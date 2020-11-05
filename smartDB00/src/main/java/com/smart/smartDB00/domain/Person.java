package com.smart.smartDB00.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person implements Serializable {
    private Long id;//人员id
    private String personNo;//人员号
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPersonNo() {
		return personNo;
	}
	public void setPersonNo(String personNo) {
		this.personNo = personNo;
	}
    
}
