package com.loivgehoto.disk.Mapper;

import com.loivgehoto.disk.model.File;
import com.loivgehoto.disk.model.User;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

public interface FileMapper {

    public void addFile(File file);

    public List<File> SearchAllFile_by_name_asc(String name);

    public List<File> SearchAllFile_by_name_desc(String name);

    public List<File> SearchAllFile_by_size_asc(String name);

    public List<File> SearchAllFile_by_size_desc(String name);

    public List<File> SearchAllFile_by_time_asc(String name);

    public List<File> SearchAllFile_by_time_desc(String name);

    public List<File> SearchAllFile_in_recycle_bin(String name);

    public List<File>  Return_Search_Result(String user_name,String file_name);

    public  void DeleteFile(String file_name );

    public void From_rootFolder_to_recycle_bin(String user_name,String file_name);

    public void From_recycle_bin_to_rootFolder(String user_name,String file_name);

    public void share(String user_name,String file_name);

    public void quxiao(String share_url);

    public void set_url(String url, String user_name,String file_name);

    public List<File> SearchAllFile_shared(String name);

    public File SearchSingleFile_shared(String share_url);
}
