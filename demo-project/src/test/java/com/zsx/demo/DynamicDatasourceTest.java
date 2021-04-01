package com.zsx.demo;

import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Description TODO
 * @Date 2021/3/10 18:22
 * @Created by zhushuxian
 */
public class DynamicDatasourceTest extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return null;
    }
}
