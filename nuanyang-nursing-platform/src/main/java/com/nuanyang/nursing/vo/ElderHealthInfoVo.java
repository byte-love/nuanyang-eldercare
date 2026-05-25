package com.nuanyang.nursing.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 老人健康信息VO
 */
@Data
@ApiModel(value = "ElderHealthInfoVo", description = "老人健康信息VO")
public class ElderHealthInfoVo {

    @ApiModelProperty(value = "老人姓名")
    private String elderName;

    @ApiModelProperty(value = "健康评分")
    private String healthScore;

    @ApiModelProperty(value = "风险等级（健康, 提示, 风险, 危险, 严重危险）")
    private String riskLevel;

    @ApiModelProperty(value = "健康报告总结")
    private String reportSummary;

    @ApiModelProperty(value = "异常分析")
    private String abnormalAnalysis;

    @ApiModelProperty(value = "推荐护理等级")
    private String nursingLevelName;

    @ApiModelProperty(value = "评估时间")
    private String assessmentTime;

    @ApiModelProperty(value = "总检日期")
    private String totalCheckDate;
}
