package com.zsx.demo.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Description TODO
 * @Date 2021/3/12 17:37
 * @Created by zhushuxian
 */
@Data
public class Message {
    private String id;
    private String message;
    private Date sendTime;
    // getter setter 略
}
