package com.loivgehoto.disk.Service;

import com.loivgehoto.disk.Mapper.FileMapper;
import com.loivgehoto.disk.Mapper.Usermapper;
import com.loivgehoto.disk.Util.MybatisUtil;
import com.loivgehoto.disk.model.User;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service(value="UserService")
public class UserService
{

    public String checkUser(User user)
    {
        SqlSession sqlSession= MybatisUtil.createSQLsession();//////////util类没写好总是空指针
        Usermapper map=sqlSession.getMapper(Usermapper.class);
//      MybatisUtil.closeSqlSession(sqlSession);
        return map.checkUser(user);
    }

    public String Register_check(String user_name)
    {
        SqlSession sqlSession= MybatisUtil.createSQLsession();//////////util类没写好总是空指针
        Usermapper map=sqlSession.getMapper(Usermapper.class);
//      MybatisUtil.closeSqlSession(sqlSession);
        return map.Register_check(user_name);

    }

    public void Register(User user)
    {
        SqlSession sqlSession= MybatisUtil.createSQLsession();//////////util类没写好总是空指针
        Usermapper map=sqlSession.getMapper(Usermapper.class);
        map.Register(user);
        MybatisUtil.closeSqlSession(sqlSession);/////////important!!!!!!!!!!不加这句关闭数据库连接直接导致注册用户后执行的账户信息插入语句无效，数据库不会插入任何信息；加了之后正常
    }

}
