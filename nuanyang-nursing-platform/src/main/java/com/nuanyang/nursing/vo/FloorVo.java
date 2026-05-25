package com.nuanyang.nursing.vo;

import lombok.Data;

import java.util.List;

@Data
public class FloorVo {

    private Long id;

    private String name;

    private Integer code;

    private List<RoomVo> roomVoList;

}
