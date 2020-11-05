package com.smart.smartDB00.controller;

import com.smart.smartDB00.dao.v39.CreateViewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {
//    @Value(value = "${thread.pool.size}")
//    private Integer threadPoolSize;
//    @Value(value = "${minssingDate.day}")
//    private Integer minssingDateDay;
//
//    @Autowired
//    private PersonMissingService personMissingService;
//
//    @Autowired
//    private RecordDataESService recordDataESService;
//
//    @Autowired
//    private MachineService machineService;
    @Autowired
    private CreateViewMapper createViewMapper;

    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    public Integer demo() {
        List<String> list = new ArrayList<>();
        list.add("smart_timeing_202001_G");
        list.add("smart_timeing_202002_G");
        createViewMapper.createTimeIngView(list);
        Integer count = 0;
        return count;
    }


//    @RequestMapping(value = "/test", method = RequestMethod.GET)
//    public void personMissingTask() {
//        System.out.println("定时任务开启==================");
//        Runnable task = () -> {
//            ExecutorService threadPool = Executors.newFixedThreadPool(threadPoolSize);
//            Date missingDate = DateUtils.dateAddDays(new Date(), -minssingDateDay);
//            Integer pageNum = 1;
//            Integer pages = null;
//            while (pages == null || pageNum <= pages) {
//                CountDownLatch countDownLatch = new CountDownLatch(threadPoolSize);
//                PageInfo<Person> pageInfo = personMissingService.getPersonList(pageNum, 1000);
//                pages = pageInfo.getPages();
//                List<List<Person>> averageAssign = CollectionUtils.averageAssign(pageInfo.getList(), threadPoolSize);
//                Vector<Long> nullList = new Vector<>();//无记录人员
//                Vector<RecordData> insertList = new Vector<>();//失联人员
//                Vector<RecordData> updateList = new Vector<>();//正常人员
//                for (List<Person> personSubList : averageAssign) {
//                    Runnable thread = () -> {
//                        for (Person person : personSubList) {
//                            RecordData recordData = recordDataESService.searchNewestRecordDataByTerm("personId", person.getId(), "rowNo");
//                            try {
//                                if (recordData == null) {
//                                    nullList.add(person.getId());
//                                } else if (missingDate.after(DateUtils.dateParse(recordData.getDateTime(), DateUtils.DATE_TIME_PATTERN))) {
//                                    insertList.add(recordData);
//                                } else {
//                                    updateList.add(recordData);
//                                }
//                            } catch (ParseException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        countDownLatch.countDown();
//                    };
//                    threadPool.execute(thread);
//                }
//                try {
//                    countDownLatch.await();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                List<Machine> machineList = machineService.selectMachine();//获取设备信息
//                if (nullList.size() != 0) {
//                    personMissingService.insertNullRecord(nullList);
//                }
//                if (insertList.size() != 0) {
//                    try {
//                        personMissingService.insertMissingRecord(insertList, machineList);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                }
//                if (updateList.size() != 0) {
//                    try {
//                        personMissingService.updateMissingRecord(updateList, machineList);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                }
//                nullList.clear();
//                insertList.clear();
//                updateList.clear();
//                System.out.println(String.format("============执行到%s页", pageNum++));
//            }
//            System.out.println("***END************失联人员更新执行完成************END***");
//        };
//        new Thread(task).start();
//    }
}
