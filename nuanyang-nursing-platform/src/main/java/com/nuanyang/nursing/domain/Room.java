package com.nuanyang.nursing.domain;

import com.nuanyang.common.annotation.Excel;
import com.nuanyang.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 房间对象 room
 * 
 * @author byte-love
 * @date 2024-04-26
 */
@Data
public class Room extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 房间编号 */
    @Excel(name = "房间编号")
    private String code;

    /** 排序号 */
    @Excel(name = "排序号")
    private Long sort;

    /** 房间类型名称 */
    @Excel(name = "房间类型ID")
    private Long roomTypeId;

    /** 楼层id */
    @Excel(name = "楼层id")
    private Long floorId;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private Integer isDeleted;




}
