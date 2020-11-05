package com.smart.smartDB00.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "record")
public class RecordData implements Serializable {
    private static final long serialVersionUID = -1L;

    private String id;
    private String type;
    private Long personId;
    private Long machineId;
    private String recordTime;
    private Date date;
    private Double money;

    @Override
    public String toString() {
        return "RecordData{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", personId=" + personId +
                ", machineId='" + machineId + '\'' +
                ", dateTime='" + recordTime + '\'' +
                ", money=" + money +
                '}';
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public Long getMachineId() {
		return machineId;
	}

	public void setMachineId(Long machineId) {
		this.machineId = machineId;
	}

	public String getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}
    
}
