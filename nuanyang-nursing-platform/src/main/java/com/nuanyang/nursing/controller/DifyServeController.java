package com.nuanyang.nursing.controller;

import com.nuanyang.nursing.domain.CheckIn;
import com.nuanyang.nursing.domain.Elder;
import com.nuanyang.nursing.domain.HealthAssessment;
import com.nuanyang.nursing.domain.HealthAssessmentDetail;
import com.nuanyang.nursing.service.ICheckInService;
import com.nuanyang.nursing.service.IElderService;
import com.nuanyang.nursing.service.IHealthAssessmentDetailService;
import com.nuanyang.nursing.service.IHealthAssessmentService;
import com.nuanyang.nursing.service.IReservationService;
import com.nuanyang.nursing.vo.ElderBasicInfoVo;
import com.nuanyang.nursing.vo.ElderHealthInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/dify/serve")
public class DifyServeController {

    @Autowired
    private IReservationService reservationService;

    @Autowired
    private IElderService elderService;

    @Autowired
    private ICheckInService checkInService;

    @Autowired
    private IHealthAssessmentService healthAssessmentService;

    @Autowired
    private IHealthAssessmentDetailService healthAssessmentDetailService;

    @GetMapping("/getReservationByToday")
    public String getReservationByDay(String datetime){
        System.out.println(datetime);

        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 字符串转 LocalDateTime
        LocalDate dateTime = LocalDate.parse(datetime, formatter);

        return reservationService.getReservationByDay(dateTime);

    }

    /**
     * 查询老人基本信息
     * @param name 老人姓名（可选）
     * @param id 老人ID（可选）
     * @return 老人基本信息
     */
    @GetMapping("/elder/basic-info")
    public ElderBasicInfoVo getElderBasicInfo(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long id) {
        
        Elder elder = null;
        
        // 根据ID或姓名查询老人信息
        if (id != null) {
            elder = elderService.selectElderById(id);
        } else if (name != null && !name.trim().isEmpty()) {
            // 根据姓名查询，取第一个匹配结果
            Elder queryParam = new Elder();
            queryParam.setName(name);
            List<Elder> elderList = elderService.selectElderList(queryParam);
            if (elderList != null && !elderList.isEmpty()) {
                elder = elderList.get(0);
            }
        }
        
        if (elder == null) {
            return null;
        }
        
        // 构建返回对象
        ElderBasicInfoVo vo = new ElderBasicInfoVo();
        vo.setId(elder.getId());
        vo.setName(elder.getName());
        vo.setPhone(elder.getPhone());
        vo.setIdCardNo(elder.getIdCardNo());
        vo.setStatus(elder.getStatus());
        vo.setRoomNumber(elder.getBedNumber());
        
        // 设置性别描述
        if (elder.getSex() != null) {
            vo.setSex(elder.getSex());
            vo.setSexDesc(elder.getSex() == 1 ? "男" : "女");
        }
        
        // 设置状态描述
        if (elder.getStatus() != null) {
            vo.setStatusDesc(getStatusDesc(elder.getStatus()));
        }
        
        // 计算年龄（优先使用出生日期，如果没有则从身份证号提取）
        Integer age = calculateAge(elder.getBirthday(), elder.getIdCardNo());
        vo.setAge(age);
        
        // 查询入住信息获取入住时间和护理等级
        CheckIn checkInQuery = new CheckIn();
        checkInQuery.setElderId(elder.getId());
        List<CheckIn> checkInList = checkInService.selectCheckInList(checkInQuery);
        
        if (checkInList != null && !checkInList.isEmpty()) {
            // 取最新的入住记录
            CheckIn checkIn = checkInList.get(0);
            if (checkIn.getStartDate() != null) {
                vo.setCheckInDate(checkIn.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
            vo.setNursingLevel(checkIn.getNursingLevelName());
        }
        
        return vo;
    }
    
    /**
     * 获取状态描述
     */
    private String getStatusDesc(Integer status) {
        switch (status) {
            case 0:
                return "禁用";
            case 1:
                return "启用";
            case 2:
                return "请假";
            case 3:
                return "退住中";
            case 4:
                return "入住中";
            case 5:
                return "已退住";
            default:
                return "未知";
        }
    }
    
    /**
     * 计算年龄
     * @param birthday 出生日期（格式：yyyy-MM-dd）
     * @param idCardNo 身份证号
     * @return 年龄
     */
    private Integer calculateAge(String birthday, String idCardNo) {
        // 优先使用出生日期
        if (birthday != null && !birthday.trim().isEmpty()) {
            try {
                // 尝试多种日期格式
                LocalDate birthDate = null;
                
                // 尝试 yyyy-MM-dd 格式
                if (birthday.contains("-")) {
                    birthDate = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                } 
                // 尝试 yyyy/MM/dd 格式
                else if (birthday.contains("/")) {
                    birthDate = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
                }
                // 尝试 yyyyMMdd 格式
                else if (birthday.length() == 8) {
                    birthDate = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("yyyyMMdd"));
                }
                
                if (birthDate != null) {
                    return Period.between(birthDate, LocalDate.now()).getYears();
                }
            } catch (Exception e) {
                System.err.println("出生日期解析失败: " + birthday + ", 错误: " + e.getMessage());
            }
        }
        
        // 如果出生日期不可用，尝试从身份证号提取
        if (idCardNo != null && idCardNo.length() == 18) {
            try {
                String birthStr = idCardNo.substring(6, 14); // 提取第7-14位（年月日）
                LocalDate birthDate = LocalDate.parse(
                    birthStr.substring(0, 4) + "-" + 
                    birthStr.substring(4, 6) + "-" + 
                    birthStr.substring(6, 8),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd")
                );
                return Period.between(birthDate, LocalDate.now()).getYears();
            } catch (Exception e) {
                System.err.println("从身份证号提取年龄失败: " + e.getMessage());
            }
        }
        
        return null;
    }

    /**
     * 查询老人健康信息
     * @param name 老人姓名（可选）
     * @param id 老人ID（可选）
     * @return 老人健康信息
     */
    @GetMapping("/elder/health-info")
    public ElderHealthInfoVo getElderHealthInfo(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long id) {
        
        // 确定查询条件
        String elderName = null;
        
        if (id != null) {
            // 根据ID查询老人姓名
            Elder elder = elderService.selectElderById(id);
            if (elder != null) {
                elderName = elder.getName();
            }
        } else if (name != null && !name.trim().isEmpty()) {
            elderName = name;
        }
        
        if (elderName == null || elderName.trim().isEmpty()) {
            return null;
        }
        
        // 查询健康评估记录
        HealthAssessment queryParam = new HealthAssessment();
        queryParam.setElderName(elderName);
        List<HealthAssessment> assessmentList = healthAssessmentService.selectHealthAssessmentList(queryParam);
        
        if (assessmentList == null || assessmentList.isEmpty()) {
            return null;
        }
        
        // 取最新的健康评估记录
        HealthAssessment latestAssessment = assessmentList.get(0);
        
        // 构建返回对象
        ElderHealthInfoVo vo = new ElderHealthInfoVo();
        vo.setElderName(latestAssessment.getElderName());
        vo.setHealthScore(latestAssessment.getHealthScore());
        vo.setNursingLevelName(latestAssessment.getNursingLevelName());
        vo.setTotalCheckDate(latestAssessment.getTotalCheckDate());
        
        if (latestAssessment.getAssessmentTime() != null) {
            vo.setAssessmentTime(latestAssessment.getAssessmentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        
        // 查询健康评估详情获取风险等级和报告总结
        if (latestAssessment.getId() != null) {
            HealthAssessmentDetail detailQuery = new HealthAssessmentDetail();
            detailQuery.setHealthAssessmentId(latestAssessment.getId());
            List<HealthAssessmentDetail> detailList = healthAssessmentDetailService.selectHealthAssessmentDetailList(detailQuery);
            
            if (detailList != null && !detailList.isEmpty()) {
                HealthAssessmentDetail detail = detailList.get(0);
                vo.setRiskLevel(detail.getRiskLevel());
                vo.setReportSummary(detail.getReportSummary());
                vo.setAbnormalAnalysis(detail.getAbnormalAnalysis());
            }
        }
        
        return vo;
    }

}
