package com.nuanyang.nursing.vo;

import com.nuanyang.nursing.domain.FamilyMemberElder;
import lombok.Data;

@Data
public class FamilyMemberElderVo extends FamilyMemberElder {

    /**
     * 老人姓名
     */
    private String elderName;
}
