package com.smart.smartDB00.controller;

import com.github.pagehelper.PageInfo;
import com.smart.smartDB00.dto.PersonMissingDto;
import com.smart.smartDB00.dto.common.GroupNode;
import com.smart.smartDB00.dto.common.ResResult;
import com.smart.smartDB00.dto.common.SelectNode;
import com.smart.smartDB00.service.PersonMissingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 失联展示页
 */

@CrossOrigin
@RestController
@RequestMapping("/personMissing")
public class PersonMissingController {

    @Autowired
    private PersonMissingService personMissingService;

    /**
     * 学院分布
     *
     * @return
     */
    @RequestMapping(value = "/getCollegeMissingGroup", method = RequestMethod.POST)
    @ResponseBody
    public ResResult getCollegeMissingGroup(@DateTimeFormat(pattern = "yyyy-MM-dd") Date beginDate, @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        ResResult resResult = new ResResult(ResResult.SUCCESS_CODE, ResResult.SUCCESS_MSG);
        try {
            List<GroupNode> dataList = personMissingService.getCollegeMissingGroup(beginDate, endDate);
            resResult.setDetail(dataList);
        } catch (Exception e) {
            e.printStackTrace();
            resResult.getReturn().setReturnCode("500");
            resResult.getReturn().setReturnMessage(ResResult.FAIL_MSG);
        }
        return resResult;
    }

    /**
     * 专业分布
     *
     * @return
     */
    @RequestMapping(value = "/getSpecialtyMissingGroup", method = RequestMethod.POST)
    @ResponseBody
    public ResResult getSpecialtyMissingGroup(@DateTimeFormat(pattern = "yyyy-MM-dd") Date beginDate, @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        ResResult resResult = new ResResult(ResResult.SUCCESS_CODE, ResResult.SUCCESS_MSG);
        try {
            List<GroupNode> dataList = personMissingService.getSpecialtyMissingGroup(beginDate, endDate);
            resResult.setDetail(dataList);
        } catch (Exception e) {
            e.printStackTrace();
            resResult.getReturn().setReturnCode("500");
            resResult.getReturn().setReturnMessage(ResResult.FAIL_MSG);
        }
        return resResult;
    }

    /**
     * 年级分布
     *
     * @return
     */
    @RequestMapping(value = "/getGradeMissingGroup", method = RequestMethod.POST)
    @ResponseBody
    public ResResult getGradeMissingGroup(@DateTimeFormat(pattern = "yyyy-MM-dd") Date beginDate, @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        ResResult resResult = new ResResult(ResResult.SUCCESS_CODE, ResResult.SUCCESS_MSG);
        try {
            List<GroupNode> dataList = personMissingService.getGradeMissingGroup(beginDate, endDate);
            resResult.setDetail(dataList);
        } catch (Exception e) {
            e.printStackTrace();
            resResult.getReturn().setReturnCode("500");
            resResult.getReturn().setReturnMessage(ResResult.FAIL_MSG);
        }
        return resResult;
    }

    /**
     * 性别分布
     *
     * @return
     */
    @RequestMapping(value = "/getSexMissingGroup", method = RequestMethod.POST)
    @ResponseBody
    public ResResult getSexMissingGroup(@DateTimeFormat(pattern = "yyyy-MM-dd") Date beginDate, @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        ResResult resResult = new ResResult(ResResult.SUCCESS_CODE, ResResult.SUCCESS_MSG);
        try {
            List<GroupNode> dataList = personMissingService.getSexMissingGroup(beginDate, endDate);
            resResult.setDetail(dataList);
        } catch (Exception e) {
            e.printStackTrace();
            resResult.getReturn().setReturnCode("500");
            resResult.getReturn().setReturnMessage(ResResult.FAIL_MSG);
        }
        return resResult;
    }

    /**
     * 各学院失联天数图
     *
     * @return
     */
    @RequestMapping(value = "/getCollegeMissingDayGroup", method = RequestMethod.POST)
    @ResponseBody
    public ResResult getCollegeMissingDayGroup(@DateTimeFormat(pattern = "yyyy-MM-dd") Date beginDate, @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        ResResult resResult = new ResResult(ResResult.SUCCESS_CODE, ResResult.SUCCESS_MSG);
        try {
            Object[][] dataList = personMissingService.getCollegeMissingDayGroup(beginDate, endDate);
            resResult.setDetail(dataList);
        } catch (Exception e) {
            e.printStackTrace();
            resResult.getReturn().setReturnCode("500");
            resResult.getReturn().setReturnMessage(ResResult.FAIL_MSG);
        }
        return resResult;
    }


    /**
     * 疑似失联记录列表
     *
     * @param collegeCode
     * @param specialtyCode
     * @param gradeCode
     * @param personNo
     * @param beginDate
     * @param endDate
     * @return
     */
    @RequestMapping(value = "/selectMissingRecord", method = RequestMethod.POST)
    public ResResult selectMissingRecord(String collegeCode, String specialtyCode, String gradeCode, String personNo, @DateTimeFormat(pattern = "yyyy-MM-dd") Date beginDate, @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        ResResult resResult = new ResResult(ResResult.SUCCESS_CODE, ResResult.SUCCESS_MSG);
        try {
            PageInfo<PersonMissingDto> pageInfo = personMissingService.selectMissingRecord(collegeCode, specialtyCode, gradeCode, personNo, beginDate, endDate, pageNum, pageSize);
            resResult.setDetail(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            resResult.getReturn().setReturnCode("500");
            resResult.getReturn().setReturnMessage(ResResult.FAIL_MSG);
        }
        return resResult;
    }

    /**
     * 疑似失联记录
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/getMissingRecord", method = RequestMethod.POST)
    public ResResult getMissingRecord(Long id) {
        ResResult resResult = new ResResult(ResResult.SUCCESS_CODE, ResResult.SUCCESS_MSG);
        try {
            if (id == null) {
                resResult.getReturn().setReturnCode("500");
                resResult.getReturn().setReturnMessage("ID必填");
                return resResult;
            }
            PersonMissingDto data = personMissingService.getMissingRecord(id);
            resResult.setDetail(data);
        } catch (Exception e) {
            e.printStackTrace();
            resResult.getReturn().setReturnCode("500");
            resResult.getReturn().setReturnMessage(ResResult.FAIL_MSG);
        }
        return resResult;
    }

    /**
     * 保存校正记录
     *
     * @param missingId
     * @param remark
     * @param statusCode
     * @return
     */
    @RequestMapping(value = "/saveReviseRecord", method = RequestMethod.POST)
    public ResResult saveReviseRecord(Long missingId, Long checkPersonId, String remark, Integer statusCode) {
        ResResult resResult = new ResResult(ResResult.SUCCESS_CODE, ResResult.SUCCESS_MSG);
        try {
            personMissingService.saveReviseRecord(missingId, checkPersonId, remark, statusCode);
        } catch (Exception e) {
            e.printStackTrace();
            resResult.getReturn().setReturnCode("500");
            resResult.getReturn().setReturnMessage(ResResult.FAIL_MSG);
        }
        return resResult;
    }

    /**
     * 学院列表
     *
     * @return
     */
    @RequestMapping(value = "/selectCollege", method = RequestMethod.POST)
    public ResResult selectCollege() {
        ResResult resResult = new ResResult(ResResult.SUCCESS_CODE, ResResult.SUCCESS_MSG);
        try {
            List<SelectNode> dataList = personMissingService.selectCollege();
            resResult.setDetail(dataList);
        } catch (Exception e) {
            e.printStackTrace();
            resResult.getReturn().setReturnCode("500");
            resResult.getReturn().setReturnMessage(ResResult.FAIL_MSG);
        }
        return resResult;
    }

    /**
     * 专业列表
     *
     * @return
     */
    @RequestMapping(value = "/selectSpecialty", method = RequestMethod.POST)
    public ResResult selectSpecialty() {
        ResResult resResult = new ResResult(ResResult.SUCCESS_CODE, ResResult.SUCCESS_MSG);
        try {
            List<SelectNode> dataList = personMissingService.selectSpecialty();
            resResult.setDetail(dataList);
        } catch (Exception e) {
            e.printStackTrace();
            resResult.getReturn().setReturnCode("500");
            resResult.getReturn().setReturnMessage(ResResult.FAIL_MSG);
        }
        return resResult;
    }

    /**
     * 年级列表
     *
     * @return
     */
    @RequestMapping(value = "/selectGrade", method = RequestMethod.POST)
    public ResResult selectGrade() {
        ResResult resResult = new ResResult(ResResult.SUCCESS_CODE, ResResult.SUCCESS_MSG);
        try {
            List<SelectNode> dataList = personMissingService.selectGrade();
            resResult.setDetail(dataList);
        } catch (Exception e) {
            e.printStackTrace();
            resResult.getReturn().setReturnCode("500");
            resResult.getReturn().setReturnMessage(ResResult.FAIL_MSG);
        }
        return resResult;
    }
}
