package com.loivgehoto.disk.model;


import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class File
{
    private int file_id;
    private String file_name;
    private String file_path;
    private String suffix_name;/////////important这些属性名必须和mysql表中的数据名完全一致，否则收不到值
    private String create_time;
    private int in_folder;
    private Long file_size;

    private String user_name;
    private int share;
    private String share_url;



    public String getShare_url() {
        return share_url;
    }

    public void setIn_folder(int in_folder){this.in_folder=in_folder;}

    public String getSuffix() {/////////important!!!!!!!!!!!!!!!!!!!!!!!!!!
        return suffix_name;
    }

    public void setSuffix(String suffix) {
        this.suffix_name = suffix;
    }

////////////important!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//    public String getSuffix_name() {///////////////////////////////////important!!!!!!!!!!!!mybatis返回的File类结果(list)，后端设置结果给叫file的model，前端使用thymeleaf对名为file的model取值，使用的是${file.suffix}，而file类中代表后缀名的属性名是suffix_name,
//      这表明，前端取model的属性值（${file.suffix}）是通过get方法去拿，应该是分析get方法的名字，像这个方法写getSuffix_name()，而不是getSuffix(),前端取值应该这样写(${file.suffix_name})
//        return suffix_name;
//    }
//
//    public void setSuffix_name(String suffix_name) {
//        this.suffix_name = suffix_name;
//    }




    public void setFile_id(int file_id) {
        this.file_id = file_id;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public void setFile_size(Long file_size) {
        this.file_size = file_size;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }


    public int getFile_id() {
        return file_id;
    }

    public String getFile_name() {
        return file_name;
    }

    public String getFile_path() {
        return file_path;
    }

    public Long getFile_size() {
        return file_size;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getCreate_time() {
        return create_time;
    }
}
