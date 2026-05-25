package com.nuanyang.nursing.domain;

import com.nuanyang.common.annotation.Excel;
import com.nuanyang.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 护理员老人关联对象 nursing_elder
 * 
 * @author byte-love
 * @date 2025-08-17
 */
@Data
public class NursingElder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 护理员id */
    @Excel(name = "护理员id")
    private Long nursingId;

    /** 老人id */
    @Excel(name = "老人id")
    private Long elderId;



}
