package com.smart.smartDB00.task;

import com.github.pagehelper.PageInfo;
import com.smart.smartDB00.domain.Person;
import com.smart.smartDB00.service.CreateViewService;
import com.smart.smartDB00.service.PersonMissingService;
import com.smart.smartDB00.service.RecordDataESService;
import com.smart.smartDB00.util.CollectionUtils;
import com.smart.smartDB00.util.DateUtils;
import com.smart.smartDB00.domain.Machine;
import com.smart.smartDB00.domain.RecordData;
import com.smart.smartDB00.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling
public class QuartzScheduled {
    @Value(value = "${thread.pool.size}")
    private Integer threadPoolSize;
    @Value(value = "${minssingDate.day}")
    private Integer minssingDateDay;

    @Autowired
    private PersonMissingService personMissingService;

    @Autowired
    private RecordDataESService recordDataESService;

    @Autowired
    private CreateViewService createViewService;

    @Autowired
    private MachineService machineService;

    @Scheduled(cron = "${personMissingTask.cron}")
    private void personMissingTask() throws ParseException {
        System.out.println("定时任务开启==================");
        Runnable task = () -> {
            ExecutorService threadPool = Executors.newFixedThreadPool(threadPoolSize);
            Date missingDate = DateUtils.dateAddDays(new Date(), -minssingDateDay);//计算失联日期
            Integer pageNum = 1;
            Integer pages = null;
            while (pages == null || pageNum <= pages) {//循环分页查询人员数据
                CountDownLatch countDownLatch = new CountDownLatch(threadPoolSize);//同步代码，需要都执行完
                PageInfo<Person> pageInfo = personMissingService.getPersonList(pageNum, 1000);
                pages = pageInfo.getPages();
                List<List<Person>> averageAssign = CollectionUtils.averageAssign(pageInfo.getList(), threadPoolSize);//人员分批放入线程

                Vector<Long> nullList = new Vector<>();//无记录人员
                Vector<RecordData> insertList = new Vector<>();//失联人员
                Vector<RecordData> updateList = new Vector<>();//正常人员
                for (List<Person> personSubList : averageAssign) {
                    Runnable thread = () -> {
                        for (Person person : personSubList) {
                            RecordData recordData = recordDataESService.searchNewestRecordDataByTerm("personId", person.getId(), "recordTime");
                            try {
//                                if (recordData == null || recordData.getRecordTime() == null) {//找不到流水记录
//                                    nullList.add(person.getId());
//                                } else if (missingDate.after(DateUtils.dateParse(recordData.getRecordTime(), DateUtils.TDATE_TIME_PATTERN))) {//最近流水记录小于计算失联日期
//                                    insertList.add(recordData);
//                                } else {//最近流水记录大于计算失联日期
//                                    updateList.add(recordData);
//                                }
                                if (recordData == null || recordData.getRecordTime() == null) {//找不到流水记录
                                    nullList.add(person.getId());
                                } else {
                                    Date date = DateUtils.dateParse(recordData.getRecordTime(), DateUtils.TDATE_TIME_PATTERN);
                                    if (missingDate.after(date)) {//最近流水记录小于计算失联日期
                                        insertList.add(recordData);
                                    } else {//最近流水记录大于计算失联日期
                                        updateList.add(recordData);
                                    }
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        countDownLatch.countDown();
                    };
                    threadPool.execute(thread);
                }
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<Machine> machineList = machineService.selectMachine();//获取设备信息
                if (nullList.size() != 0) {
                    personMissingService.insertNullRecord(nullList);
                }
                if (insertList.size() != 0) {
                    personMissingService.insertMissingRecord(insertList, machineList);
                }
                if (updateList.size() != 0) {
                    personMissingService.updateMissingRecord(updateList, machineList);
                }
                nullList.clear();
                insertList.clear();
                updateList.clear();
                System.out.println(String.format("总共" + pages + "页============执行到%s页", pageNum++));
            }
            System.out.println("***END************失联人员更新执行完成************END***");
        };
        new Thread(task).start();
    }

    @Scheduled(cron = "${createViewTask.cron}")
    private void createViewTask() throws Exception {
        System.out.println("创建视图开始" + DateUtils.dateFormat(new Date(), DateUtils.DATE_TIME_PATTERN));
        String tableNamePrefix = "SMART_TIMEING";
        createViewService.createTimeingView(tableNamePrefix);
    }
}
