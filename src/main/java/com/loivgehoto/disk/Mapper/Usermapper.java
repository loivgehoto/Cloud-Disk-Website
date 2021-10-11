package com.loivgehoto.disk.Mapper;

import com.loivgehoto.disk.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


public interface Usermapper
{
//    @Select("select name from user where name =#{username}  and password = #{password}")
    public String checkUser(User user);

    public String Register_check(String user_name);

    public void Register(User user);
}
