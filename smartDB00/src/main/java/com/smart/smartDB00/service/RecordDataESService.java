package com.smart.smartDB00.service;

import com.smart.smartDB00.domain.RecordData;

public interface RecordDataESService {
    RecordData searchNewestRecordDataByTerm(String name, Object text, String sort);
}
