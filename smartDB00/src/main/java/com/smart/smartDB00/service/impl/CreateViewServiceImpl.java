package com.smart.smartDB00.service.impl;

import com.smart.smartDB00.dao.v39.CreateViewMapper;
import com.smart.smartDB00.service.CreateViewService;
import com.smart.smartDB00.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CreateViewServiceImpl implements CreateViewService {
    @Autowired
    private CreateViewMapper createViewMapper;

    @Override
    public void createTimeingView(String tableNamePrefix) throws Exception {
        List<String > tableNameList = new ArrayList<>();
        Date sysdate = DateUtils.dateAddMonths(new Date(),-2);
        Date min = DateUtils.minDateOfMonth(sysdate);
        Integer monthStatusCount = createViewMapper.getTimeingMonthExistStatus(min);//判断视图是否存在当前月表
        if(monthStatusCount <= 0 ){
            String month = DateUtils.dateFormat(sysdate,"yyyyMM");
            String tableName = String.format("%s_%s_G",tableNamePrefix, month);
            Integer statusCount = createViewMapper.getTableExistStatus(tableName);
            if(statusCount > 0){
                String lastMonth = DateUtils.dateFormat(DateUtils.dateAddMonths(sysdate,-1),"yyyyMM");
                String lastMonthTableName = String.format("%s_%s_G",tableNamePrefix, lastMonth);
                Integer lastMonthStatusCount = createViewMapper.getTableExistStatus(tableName);
                if(lastMonthStatusCount > 0) {
                    tableNameList.add(lastMonthTableName);
                }
                tableNameList.add(tableName);
                createViewMapper.dropView("smart_timeing_view");//删除视图
                createViewMapper.createTimeIngView(tableNameList);//生成新的视图
            }
        }
    }
}
