package com.zzyl.nursing.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 老人基本信息VO
 */
@Data
@ApiModel(value = "ElderBasicInfoVo", description = "老人基本信息VO")
public class ElderBasicInfoVo {

    @ApiModelProperty(value = "老人ID")
    private Long id;

    @ApiModelProperty(value = "老人姓名")
    private String name;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "性别（0:女 1:男）")
    private Integer sex;

    @ApiModelProperty(value = "性别描述")
    private String sexDesc;

    @ApiModelProperty(value = "入住时间")
    private String checkInDate;

    @ApiModelProperty(value = "房间号/床位号")
    private String roomNumber;

    @ApiModelProperty(value = "护理等级")
    private String nursingLevel;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "身份证号")
    private String idCardNo;

    @ApiModelProperty(value = "状态（0：禁用，1:启用 2:请假 3:退住中 4入住中 5已退住）")
    private Integer status;

    @ApiModelProperty(value = "状态描述")
    private String statusDesc;
}
