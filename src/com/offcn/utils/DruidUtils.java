package com.offcn.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DruidUtils {
    private  static DataSource ds = null;
    //获取数据源
    static {
        Properties properties = new Properties();
        try {
            InputStream is  = DruidUtils.class.getClassLoader().getResourceAsStream("druid.properties");
            //调用load方法加载文件
            properties.load(is);
            //druid连接池中提供了createDataSource()方法获取数据源
            ds = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取数据源对象
    public  static  DataSource getDataSource(){
        return  ds;
    }

    //获取连接
    public  static Connection getConn(){
        try {
            return  ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  null;
    }

}
