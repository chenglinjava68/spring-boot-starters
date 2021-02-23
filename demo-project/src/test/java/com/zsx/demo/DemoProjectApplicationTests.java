package com.zsx.demo;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsx.demo.dynamicdatasource.dao.UserMapper;
import com.zsx.demo.dynamicdatasource.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class DemoProjectApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private UserMapper userMapper;

    @Test
    @DS("slave_1")
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        Page<User> page = new Page<>();
        page.setSize(2);
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);

        IPage<User> data = userMapper.selectPage(page,null);
        data.getRecords().forEach(System.out::println);
    }

}
