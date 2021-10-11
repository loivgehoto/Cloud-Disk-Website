package com.loivgehoto.disk.Service;

import com.loivgehoto.disk.Mapper.FileMapper;
import com.loivgehoto.disk.Mapper.Usermapper;
import com.loivgehoto.disk.Util.MybatisUtil;
import com.loivgehoto.disk.model.File;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

public class FileService {
    public void AddFile(File file) {

        SqlSession sqlSession = MybatisUtil.createSQLsession();//////////util类没写好总是空指针
        FileMapper map = sqlSession.getMapper(FileMapper.class);
        map.addFile(file);
        MybatisUtil.closeSqlSession(sqlSession);

    }

    public List<File> SearchAllFile_by_name_asc(String name) {

        java.io.File file = new java.io.File("D:/TEST/");
        java.io.File[] tempList = file.listFiles();
        List<File> list = new ArrayList<>();
//        for (int i = 0; i < tempList.length; i++)
//        {
//            if (tempList[i].isDirectory()) {
//                System.out.println("文件夹：" + tempList[i]);
//                com.loivgehoto.disk.model.File temp=new com.loivgehoto.disk.model.File();
//                temp.setFile_name(tempList[i].getName());
//                temp.setSuffix("folder");
//                list.add(temp);
//            }
//        }

        SqlSession sqlSession = MybatisUtil.createSQLsession();//////////util类没写好总是空指针
        FileMapper map = sqlSession.getMapper(FileMapper.class);
        list = (map.SearchAllFile_by_name_asc(name));
        MybatisUtil.closeSqlSession(sqlSession);///////important未写此句关闭该sqlsession会导致在文件列表中快速点击翻页，网络直接卡住，无法跳转

//        for (File f:list)
//        {
//            System.out.println("list的内容"+f.getFile_name());
//        }

        return list;
    }

    public List<File> SearchAllFile_by_name_desc(String name) {

        java.io.File file = new java.io.File("D:/TEST/");
        java.io.File[] tempList = file.listFiles();
        List<File> list = new ArrayList<>();


        SqlSession sqlSession = MybatisUtil.createSQLsession();//////////util类没写好总是空指针
        FileMapper map = sqlSession.getMapper(FileMapper.class);
        list = (map.SearchAllFile_by_name_desc(name));
        MybatisUtil.closeSqlSession(sqlSession);///////important未写此句关闭该sqlsession会导致在文件列表中快速点击翻页，网络直接卡住，无法跳转

        return list;
    }

    public List<File> SearchAllFile_by_size_asc(String name) {

        java.io.File file = new java.io.File("D:/TEST/");
        List<File> list = new ArrayList<>();


        SqlSession sqlSession = MybatisUtil.createSQLsession();//////////util类没写好总是空指针
        FileMapper map = sqlSession.getMapper(FileMapper.class);
        list = (map.SearchAllFile_by_size_asc(name));
        MybatisUtil.closeSqlSession(sqlSession);///////important未写此句关闭该sqlsession会导致在文件列表中快速点击翻页，网络直接卡住，无法跳转

        return list;
    }

    public List<File> SearchAllFile_by_size_desc(String name) {

        java.io.File file = new java.io.File("D:/TEST/");
        List<File> list = new ArrayList<>();


        SqlSession sqlSession = MybatisUtil.createSQLsession();//////////util类没写好总是空指针
        FileMapper map = sqlSession.getMapper(FileMapper.class);
        list = (map.SearchAllFile_by_size_desc(name));
        MybatisUtil.closeSqlSession(sqlSession);///////important未写此句关闭该sqlsession会导致在文件列表中快速点击翻页，网络直接卡住，无法跳转

        return list;
    }

    public List<File> SearchAllFile_by_time_asc(String name) {

        java.io.File file = new java.io.File("D:/TEST/");
        List<File> list = new ArrayList<>();


        SqlSession sqlSession = MybatisUtil.createSQLsession();//////////util类没写好总是空指针
        FileMapper map = sqlSession.getMapper(FileMapper.class);
        list = (map.SearchAllFile_by_time_asc(name));
        MybatisUtil.closeSqlSession(sqlSession);///////important未写此句关闭该sqlsession会导致在文件列表中快速点击翻页，网络直接卡住，无法跳转

        return list;
    }

    public List<File> SearchAllFile_by_time_desc(String name) {

        java.io.File file = new java.io.File("D:/TEST/");
        List<File> list = new ArrayList<>();


        SqlSession sqlSession = MybatisUtil.createSQLsession();//////////util类没写好总是空指针
        FileMapper map = sqlSession.getMapper(FileMapper.class);
        list = (map.SearchAllFile_by_time_desc(name));
        MybatisUtil.closeSqlSession(sqlSession);///////important未写此句关闭该sqlsession会导致在文件列表中快速点击翻页，网络直接卡住，无法跳转

        return list;
    }


    public List<File> SearchAllFile_in_recycle_bin(String name) {

//        java.io.File file=new java.io.File("D:/TEST/");
//        java.io.File[] tempList = file.listFiles();

        List<File> list = new ArrayList<>();

        SqlSession sqlSession = MybatisUtil.createSQLsession();//////////util类没写好总是空指针
        FileMapper map = sqlSession.getMapper(FileMapper.class);
        list = (map.SearchAllFile_in_recycle_bin(name));
        MybatisUtil.closeSqlSession(sqlSession);///////important未写此句关闭该sqlsession会导致在文件列表中快速点击翻页，网络直接卡住，无法跳转

        return list;
    }

    public List<File> Return_Search_Result(String user_name, String file_name) {
        List<File> list = new ArrayList<>();
        SqlSession sqlSession = MybatisUtil.createSQLsession();//////////util类没写好总是空指针
        FileMapper map = sqlSession.getMapper(FileMapper.class);
        list = (map.Return_Search_Result(user_name, file_name));
        MybatisUtil.closeSqlSession(sqlSession);///////important未写此句关闭该sqlsession会导致在文件列表中快速点击翻页，网络直接卡住，无法跳转
        return list;
    }

    public void DeleteFile(String file_name) {
        SqlSession sqlSession = MybatisUtil.createSQLsession();//////////util类没写好总是空指针
        FileMapper map = sqlSession.getMapper(FileMapper.class);
        map.DeleteFile(file_name);
        MybatisUtil.closeSqlSession(sqlSession);
    }

    public void From_rootFolder_to_recycle_bin(String user_name, String file_name) {
        SqlSession sqlSession = MybatisUtil.createSQLsession();//////////util类没写好总是空指针
        FileMapper map = sqlSession.getMapper(FileMapper.class);
        map.From_rootFolder_to_recycle_bin(user_name, file_name);
        MybatisUtil.closeSqlSession(sqlSession);
    }

    public void From_recycle_bin_to_rootFolder(String user_name, String file_name) {
        SqlSession sqlSession = MybatisUtil.createSQLsession();//////////util类没写好总是空指针
        FileMapper map = sqlSession.getMapper(FileMapper.class);
        map.From_recycle_bin_to_rootFolder(user_name, file_name);
        MybatisUtil.closeSqlSession(sqlSession);
    }


    public void share(String user_name, String file_name) {
        SqlSession sqlSession = MybatisUtil.createSQLsession();//////////util类没写好总是空指针
        FileMapper map = sqlSession.getMapper(FileMapper.class);
        map.share(user_name, file_name);
        MybatisUtil.closeSqlSession(sqlSession);
    }

    public void quxiao(String share_url)
    {
        SqlSession sqlSession = MybatisUtil.createSQLsession();
        FileMapper map = sqlSession.getMapper(FileMapper.class);
        map.quxiao(share_url);
        MybatisUtil.closeSqlSession(sqlSession);
    }

    public List<File> SearchAllFile_shared(String name) {
        List<File> list = new ArrayList<>();

        SqlSession sqlSession = MybatisUtil.createSQLsession();//////////util类没写好总是空指针
        FileMapper map = sqlSession.getMapper(FileMapper.class);
        list = (map.SearchAllFile_shared(name));
        MybatisUtil.closeSqlSession(sqlSession);///////important未写此句关闭该sqlsession会导致在文件列表中快速点击翻页，网络直接卡住，无法跳转

        return list;
    }

    public void set_url(String url,String user_name, String file_name) {
        SqlSession sqlSession = MybatisUtil.createSQLsession();//////////util类没写好总是空指针
        FileMapper map = sqlSession.getMapper(FileMapper.class);
        map.set_url(url,user_name, file_name);
        MybatisUtil.closeSqlSession(sqlSession);
    }

    public File SearchSingleFile_shared(String share_url) {

        SqlSession sqlSession = MybatisUtil.createSQLsession();//////////util类没写好总是空指针
        FileMapper map = sqlSession.getMapper(FileMapper.class);
        File list = (map.SearchSingleFile_shared(share_url));
        MybatisUtil.closeSqlSession(sqlSession);///////important未写此句关闭该sqlsession会导致在文件列表中快速点击翻页，网络直接卡住，无法跳转

        return list;
    }

}
