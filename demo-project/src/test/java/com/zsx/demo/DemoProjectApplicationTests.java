package com.zsx.demo;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.toolkit.CryptoUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsx.demo.dynamicdatasource.dao.UserMapper;
import com.zsx.demo.dynamicdatasource.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DemoProjectApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private UserMapper userMapper;

    @Test
    @DS("slave_1")//单元测试无法切换数据源
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        Page<User> page = new Page<>();
        page.setSize(2);
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);

        IPage<User> data = userMapper.selectPage(page,null);
        data.getRecords().forEach(System.out::println);
    }


    /**
     * 生成RSA非对称秘钥 以及加密
     * @throws Exception
     */
    @Test
    public  void generateKey() throws  Exception{
        String password = "123456";

        //自定义publicKey
        String[] arr = CryptoUtils.genKeyPair(512);
        System.out.println("privateKey:" + arr[0]);
        System.out.println("publicKey:" + arr[1]);
        System.out.println("password:" + CryptoUtils.encrypt(arr[0], password));
    }
//    privateKey:MIIBUwIBADANBgkqhkiG9w0BAQEFAASCAT0wggE5AgEAAkEAjVLHgE1B6axi73twGZUEtXhhVI890wFBiXSAiGx26yft3Gj8kJZ8Qx1A5z+2LUHVaZ//C/wDG8/to6VeNHBhvwIDAQABAkAZwPcYO3wCgGa7K6x/DPgBtgFOrpfepwDxcqils9Zr/d+gPfvmfUpXxAQV+WIHYrf91lPP9NWt6WL4UwSLUL+ZAiEA/RFFi78/RgOjvzris9RAKVwjZE8j3olo1h/yIhyJikMCIQCO9gRgy6gBLF5EASwSmbmDZlQe9wxd35xW3IyRjFTI1QIgDnv5lgkJLxGwQpa1OQOx9GwQnoIlFGya8n57llY5zQ8CIBNkd++RcLbIB/l9lzbmt22Fof+ZT4okXRbDUCYmPRIJAiAuQg3za+xmpes2oI1EUMIcrBxKjTIsVket6bH6c2mRZw==
//    publicKey:MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAI1Sx4BNQemsYu97cBmVBLV4YVSPPdMBQYl0gIhsdusn7dxo/JCWfEMdQOc/ti1B1Wmf/wv8AxvP7aOlXjRwYb8CAwEAAQ==
//    password:RawLV/Hi/FLc0DNEzPQtbX28kySDIHwubXwgBRWvKQpZhu+rdrs6pmn0oxKdgkYxfU5mkTGYIocED7y2gnZFLg==
}
