package com.zsx.demo.dynamicdatasource.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsx.demo.dynamicdatasource.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    List<Map<String,Object>> getList();
}
