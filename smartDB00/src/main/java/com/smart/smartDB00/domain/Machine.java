package com.smart.smartDB00.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Machine implements Serializable {
    private Long id;//终端Id
    private String machineNo;//终端出厂编号
    private String addressId;//地理位置ID
    private String detailedAddress;//详细地址
    private Integer behaviorType;//行为类型
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMachineNo() {
		return machineNo;
	}
	public void setMachineNo(String machineNo) {
		this.machineNo = machineNo;
	}
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	public String getDetailedAddress() {
		return detailedAddress;
	}
	public void setDetailedAddress(String detailedAddress) {
		this.detailedAddress = detailedAddress;
	}
	public Integer getBehaviorType() {
		return behaviorType;
	}
	public void setBehaviorType(Integer behaviorType) {
		this.behaviorType = behaviorType;
	}
    
}
