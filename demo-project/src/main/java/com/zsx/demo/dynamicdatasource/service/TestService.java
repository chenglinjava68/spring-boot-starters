package com.zsx.demo.dynamicdatasource.service;

import java.util.List;
import java.util.Map;

public interface TestService {
    public List<Map<String,Object>> getList();
    public List<Map<String,Object>> hello(String name);
    public List<Map<String,Object>> hello2(String name);
}
