package com.loivgehoto.disk.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLconnector///数据库连接类
{
    Connection DBconn = null;
    public Connection init()
    {
        String DB_url  = "jdbc:mysql://localhost:3306/web_user";///数据库 URL
        String username ="root";
        String password = "123456";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");// 注册 JDBC 驱动器
            DBconn = DriverManager.getConnection(DB_url,username,password); // 打开一个连接
            System.out.println("数据库连接成功");
        }catch (ClassNotFoundException e1){
            System.out.println(e1+"驱动程序找不到");
        }catch(SQLException e2){
            System.out.println(e2);
        }
        return DBconn;
    }
}
