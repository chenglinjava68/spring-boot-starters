package com.zsx.demo.dynamicdatasource.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.zsx.demo.dynamicdatasource.dao.TestDao;
import com.zsx.demo.dynamicdatasource.dao.UserMapper;
import com.zsx.demo.dynamicdatasource.model.User;
import com.zsx.demo.dynamicdatasource.service.TestService;
import com.zsx.demo.dynamicdatasource.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestDao testDao;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;

    @Override
    public List<Map<String, Object>> getList() {
        return testDao.getList();
    }

    @Override
    @DS("slave_1")
    public List<Map<String, Object>> hello(String name) {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
        System.out.println("hello:"+name);
        userService.getList();
        return null;
    }

    @Override
    public List<Map<String, Object>> hello2(String name) {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
        System.out.println("hello:"+name);
        return null;
    }
}
