package com.zsx.demo.dynamicdatasource.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Description TODO
 * @Date 2021/2/22 16:50
 * @Created by zhushuxian
 */
@Data
@TableName("USER_INFO")
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}