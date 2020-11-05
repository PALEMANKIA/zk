package com.smart.smartDB00.repository;

import com.smart.smartDB00.domain.RecordData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface RecordDataRepository extends ElasticsearchRepository<RecordData, String> {
}
