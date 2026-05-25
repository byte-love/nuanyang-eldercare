package com.nuanyang.nursing.domain;

import com.nuanyang.common.annotation.Excel;
import com.nuanyang.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 楼层对象 floor
 * 
 * @author byte-love
 * @date 2024-04-26
 */
@Data
public class Floor extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 编号 */
    @Excel(name = "编号")
    private Long code;

}
