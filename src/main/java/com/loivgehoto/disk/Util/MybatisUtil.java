package com.loivgehoto.disk.Util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MybatisUtil {

    private  static SqlSessionFactory sessionFactory;

    static {
        try {
            InputStream inputStream=Resources.getResourceAsStream("mybatis-config.xml");
            sessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SqlSession createSQLsession()
    {
        return sessionFactory.openSession();
    }

    public static void closeSqlSession(SqlSession sqlSession)
    {
        if(sqlSession!=null){
            sqlSession.close();
        }
    }


}
