package com.smart.smartDB00.service.impl;

import com.smart.smartDB00.repository.RecordDataRepository;
import com.smart.smartDB00.service.RecordDataESService;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import com.smart.smartDB00.domain.RecordData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
public class RecordDataESServiceImpl implements RecordDataESService {
    @Autowired
    private RecordDataRepository recordDataRepository;

    /**
     * 去ES查询流水记录的最后一条
     * @param name 字段名称
     * @param text 筛选条件
     * @param sort 排序字段
     * @return
     */
    @Override
    public RecordData searchNewestRecordDataByTerm(String name, Object text, String sort) {
        PageRequest pageRequest = PageRequest.of(0, 1, Sort.by(Sort.Order.desc(sort)));
        QueryBuilder queryBuilder = QueryBuilders.termQuery(name, text);
        Iterable<RecordData> search = recordDataRepository.search(queryBuilder, pageRequest);
        Iterator<RecordData> userIterator = search.iterator();
        while (userIterator.hasNext()) {
            return userIterator.next();
        }
        return null;
    }
}
