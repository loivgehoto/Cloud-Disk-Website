package com.loivgehoto.disk.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.loivgehoto.disk.Service.FileService;
import com.loivgehoto.disk.Service.UserService;

import com.loivgehoto.disk.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;
import java.util.UUID;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.loivgehoto.disk.Mapper.FileMapper;
import com.loivgehoto.disk.Mapper.Usermapper;
import com.loivgehoto.disk.Service.FileService;
import com.loivgehoto.disk.Util.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
public class UserController
{

    @GetMapping("/login")
    @ResponseBody
    public String LoginJudge (@RequestParam("username")String name, @RequestParam("password")String password,HttpSession session)
    {
        User user=new User();
        user.setName(name);
        user.setPassword(password);
        String Name=name;
        System.out.println("ALREADY IN!!!!!!!!!!!!!");


        UserService service=new UserService();
        String check= service.checkUser(user);

//        System.out.println("result"+check);

//      Connection connection=null;
//         response.setContentType("text/html");
//        MybatisUtil.closeSqlSession(sqlSession);
//         System.out.println(name+" "+password);

         if(check!=null)//////////之前用check.equals(null)是错的，null不是对象，空指针报错
         {
             session.setAttribute("user_name",Name);
             return "right";
         }
         else
             return "false";
//        SQLconnector op=new SQLconnector();
//        connection=op.init();


//            pstmt=(PreparedStatement) connection.prepareStatement(Sql_state);
//            pstmt.setString(1,name); //这里的意思将用户名和密码填到SQL语句的问号处
//            pstmt.setString(2,password);
//            System.out.println(pstmt);
//
//            ResultSet rSET=(ResultSet) pstmt.executeQuery();///得到数据库的查询结果,一个数据集
//
//            if(rSET.next())  //判断结果集是否有效(查询语句在表中查不到任何结果，则是false)，此情况表示该账户信息是对的
//            {
//                System.out.println("账户对");
//
//                connection.close();/////////////关闭数据库连接
//                return "right";
////                PrintWriter out = response.getWriter();//////////这个往前端传字符串，ajax会接受此字符串
////                out.write("right");
////                out.flush();
////                out.close();
//            }
//            else
//            {
//                System.out.println("账户错误");
//                connection.close();/////////////关闭数据库连接
//                return "false";
//            }
    }


    @RequestMapping( "/register_home")
    public String Register_home()
    {
        return "register";
    }

    @RequestMapping( "/register")
    public String Register(@RequestParam("user_name")String name,@RequestParam("password")String password,HttpSession session)
    {

        UserService service=new UserService();
        User user=new User();
        user.setName(name);
        user.setPassword(password);
        service.Register(user);
        System.out.println("register        "+name+password);

        session.setAttribute("user_name",name);
        File dictionary=new File("D:/TEST/"+name);
        dictionary.mkdir();

        File dictionary2=new File("D:/TEST/"+name+"/回收站");
        dictionary2.mkdir();

        return "register";
    }

    @GetMapping("/register_check")
    @ResponseBody
    public String Register_check (@RequestParam("username")String name,HttpSession session)
    {
        System.out.println("register");
        UserService service=new UserService();
        String check= service.Register_check(name);

        if(check!=null)////
        {
//            session.setAttribute("user_name",name);
            return "false";
        }
        else
            return "true";
    }



    @RequestMapping( "/")
    public String index()
    {
        return "index";
    }


    @RequestMapping( "/name_desc")//////////
    @ResponseBody
    public void sort_by_name_desc( HttpSession session)
    {
        session.setAttribute("sort_by_name","desc");
        session.setAttribute("sort_type","name");

    }

    @RequestMapping( "/name_asc")//////////
    @ResponseBody
    public void sort_by_name_asc( HttpSession session)
    {
        session.setAttribute("sort_by_name","asc");
        session.setAttribute("sort_type","name");

    }

    @RequestMapping( "/size_asc")//////////
    @ResponseBody
    public void sort_by_size_asc( HttpSession session)
    {
        session.setAttribute("sort_by_size","asc");
        session.setAttribute("sort_type","size");

    }

    @RequestMapping( "/size_desc")//////////
    @ResponseBody
    public void sort_by_size_desc( HttpSession session)
    {
        session.setAttribute("sort_by_size","desc");
        session.setAttribute("sort_type","size");

    }

    @RequestMapping( "/time_asc")//////////
    @ResponseBody
    public void sort_by_time_asc( HttpSession session)
    {
        session.setAttribute("sort_by_time","asc");
        session.setAttribute("sort_type","time");

    }

    @RequestMapping( "/time_desc")//////////
    @ResponseBody
    public void sort_by_time_desc( HttpSession session)
    {
        session.setAttribute("sort_by_time","desc");
        session.setAttribute("sort_type","time");

    }


    @RequestMapping( "/video")//////////
    public String videohome( @RequestParam("videoName")String videoName ,HttpSession session)
    {
        session.setAttribute("video_name",videoName);
        return "home2";
    }

    @RequestMapping( "/home")//////////
    public String home(  @RequestParam(required = false,defaultValue="1",value="pageNum")Integer pageNum,
                         @RequestParam(defaultValue="15",value="pageSize")Integer pageSize,Model model, HttpSession session)
    {
//        System.out.println(pageNum+" "+pageSize);
        FileService service=new FileService();

        String user_name=(String) session.getAttribute("user_name");
//        "D:/TEST/"+ user_name+"/"+filename
        List<com.loivgehoto.disk.model.File> list0=searchFolder("D:/TEST/"+ user_name+"/");
        model.addAttribute("folder",list0);


        String temp=(String) session.getAttribute("user_name");///////从session中获取用户名，用于在网页右上角显示并数据库查询用户所有文件

        if(session.getAttribute("sort_by_name")!=null)
        {
            switch ((String) session.getAttribute("sort_type"))
            {
                case "name":if(session.getAttribute("sort_by_name").equals("asc"))
                {
                    PageHelper.startPage(pageNum,pageSize);
                    List<com.loivgehoto.disk.model.File> list=service.SearchAllFile_by_name_asc(temp);//        list.addAll(service.SearchAllFile(temp));//                searchFolder("D:/TEST/");
                    PageInfo<com.loivgehoto.disk.model.File> info=new PageInfo<>(list);
                    model.addAttribute("pageInfo",info);
                }
                else if(session.getAttribute("sort_by_name").equals("desc"))
                {
                    PageHelper.startPage(pageNum,pageSize);
                    List<com.loivgehoto.disk.model.File> list=service.SearchAllFile_by_name_desc(temp);//        list.addAll(service.SearchAllFile(temp));//                searchFolder("D:/TEST/");
                    PageInfo<com.loivgehoto.disk.model.File> info=new PageInfo<>(list);
                    model.addAttribute("pageInfo",info);
                }break;

                case "size":if(session.getAttribute("sort_by_size").equals("asc"))
                {
                    PageHelper.startPage(pageNum,pageSize);
                    List<com.loivgehoto.disk.model.File> list=service.SearchAllFile_by_size_asc(temp);//        list.addAll(service.SearchAllFile(temp));//                searchFolder("D:/TEST/");
                    PageInfo<com.loivgehoto.disk.model.File> info=new PageInfo<>(list);
                    model.addAttribute("pageInfo",info);
                }
                else if(session.getAttribute("sort_by_size").equals("desc"))
                {
                    PageHelper.startPage(pageNum,pageSize);
                    List<com.loivgehoto.disk.model.File> list=service.SearchAllFile_by_size_desc(temp);//        list.addAll(service.SearchAllFile(temp));//                searchFolder("D:/TEST/");
                    PageInfo<com.loivgehoto.disk.model.File> info=new PageInfo<>(list);
                    model.addAttribute("pageInfo",info);
                }break;

                case "time":if(session.getAttribute("sort_by_time").equals("asc"))
                {
                    PageHelper.startPage(pageNum,pageSize);
                    List<com.loivgehoto.disk.model.File> list=service.SearchAllFile_by_time_asc(temp);//        list.addAll(service.SearchAllFile(temp));//                searchFolder("D:/TEST/");
                    PageInfo<com.loivgehoto.disk.model.File> info=new PageInfo<>(list);
                    model.addAttribute("pageInfo",info);
                }
                else if(session.getAttribute("sort_by_time").equals("desc"))
                {
                    PageHelper.startPage(pageNum,pageSize);
                    List<com.loivgehoto.disk.model.File> list=service.SearchAllFile_by_time_desc(temp);//        list.addAll(service.SearchAllFile(temp));//                searchFolder("D:/TEST/");
                    PageInfo<com.loivgehoto.disk.model.File> info=new PageInfo<>(list);
                    model.addAttribute("pageInfo",info);
                }break;


            }

//            if(session.getAttribute("sort_by_name").equals("asc"))
//            {
//                PageHelper.startPage(pageNum,pageSize);
//                List<com.loivgehoto.disk.model.File> list=service.SearchAllFile_by_name_asc(temp);//        list.addAll(service.SearchAllFile(temp));//                searchFolder("D:/TEST/");
//                PageInfo<com.loivgehoto.disk.model.File> info=new PageInfo<>(list);
//                model.addAttribute("pageInfo",info);
//            }
//            else if(session.getAttribute("sort_by_name").equals("desc"))
//            {
//                PageHelper.startPage(pageNum,pageSize);
//                List<com.loivgehoto.disk.model.File> list=service.SearchAllFile_by_name_desc(temp);//        list.addAll(service.SearchAllFile(temp));//                searchFolder("D:/TEST/");
//                PageInfo<com.loivgehoto.disk.model.File> info=new PageInfo<>(list);
//                model.addAttribute("pageInfo",info);
//            }
        }

        if(session.getAttribute("sort_by_name")==null||session.getAttribute("sort_type")==null||session.getAttribute("sort_by_size")==null||session.getAttribute("sort_by_time")==null)
        {
            session.setAttribute("sort_by_name","asc");
            session.setAttribute("sort_by_size","asc");
            session.setAttribute("sort_by_time","asc");
            session.setAttribute("sort_type","name");
            PageHelper.startPage(pageNum,pageSize);
            List<com.loivgehoto.disk.model.File> list=service.SearchAllFile_by_name_asc(temp);//        list.addAll(service.SearchAllFile(temp));//                searchFolder("D:/TEST/");
            PageInfo<com.loivgehoto.disk.model.File> info=new PageInfo<>(list);
            model.addAttribute("pageInfo",info);
        }
//        System.out.println("sort_by_name      "+session.getAttribute("sort_by_name"));


//        System.out.println(list.get(0).getSuffix());
//        int count=list.size();

//        MybatisUtil.closeSqlSession(sqlSession);

        session.setAttribute("name",session.getAttribute("user_name"));

//        session.setAttribute("count",count);

        return "home";
    }

    @RequestMapping( "/bin")//////////
    public String recycle_bin(  @RequestParam(required = false,defaultValue="1",value="pageNum")Integer pageNum,
                         @RequestParam(defaultValue="12",value="pageSize")Integer pageSize,Model model, HttpSession session)
    {

        String user_name=(String) session.getAttribute("user_name");//////从session中获取用户名，用于在网页右上角显示并数据库查询用户所有文件
        List<com.loivgehoto.disk.model.File> list0=searchFolder("D:/TEST/"+user_name+"/回收站/");
        model.addAttribute("folder",list0);

        FileService service=new FileService();

        PageHelper.startPage(pageNum,pageSize);
        List<com.loivgehoto.disk.model.File> list=service.SearchAllFile_in_recycle_bin(user_name);//        list.addAll(service.SearchAllFile(temp));//                searchFolder("D:/TEST/");
        PageInfo<com.loivgehoto.disk.model.File> info=new PageInfo<>(list);
        model.addAttribute("pageInfo",info);

//        session.setAttribute("name",session.getAttribute("user_name"));

        return "bin";
    }

    @RequestMapping( "/search")//////////
    @ResponseBody/////////////////////////////////////important，不加这个ajax get请求将不会被接受!!!!!!
    public String search( @RequestParam("file_name")String file_name, HttpSession session)
    {
        session.setAttribute("search_file_name",file_name);
        return "redirect:/search_result";////这里是无效的，由ajax去跳转
    }

    @RequestMapping( "/search_result")//////////
    public String search_result( @RequestParam(required = false,defaultValue="1",value="pageNum")Integer pageNum,
                            @RequestParam(defaultValue="12",value="pageSize")Integer pageSize, Model model, HttpSession session)
    {

        String user_name=(String) session.getAttribute("user_name");//////从session中获取用户名，用于在网页右上角显示并数据库查询用户所有文件

        FileService service=new FileService();
        System.out.println("search_file_name=="+(String) session.getAttribute("search_file_name"));
        PageHelper.startPage(pageNum,pageSize);
        List<com.loivgehoto.disk.model.File> list=service.Return_Search_Result(user_name,(String) session.getAttribute("search_file_name"));//        list.addAll(service.SearchAllFile(temp));//                searchFolder("D:/TEST/");
        PageInfo<com.loivgehoto.disk.model.File> info=new PageInfo<>(list);
        model.addAttribute("pageInfo",info);

        return "search_result";
    }


    @RequestMapping( "/folder")
    public String folder(  @RequestParam(required = false,defaultValue="1",value="pageNum")Integer pageNum,
                           @RequestParam("folder_name")String folder_name,@RequestParam("parent")String parent,Model model, HttpSession session)
    {
//        FileService service=new FileService();

//        if(session.getAttribute("parent")!=null)
//        {
//            String temp=(String)session.getAttribute("parent");

            File ff=new File(parent+folder_name);
            String k=ff.getParent();
            k=k.substring(k.lastIndexOf('\\'));
            k=k.substring(1);
            System.out.println("k========"+k);
            session.setAttribute("parent",k);

            session.setAttribute("full_parent",parent);

            String k2=parent.substring(0,parent.length()-1);
            k2=k2.substring(0,k2.lastIndexOf('/'));
            k2+='/';
            System.out.println("k2   "+k2);
            session.setAttribute("parents_parent",k2);

//            int len=parent.length();
//
//            String p=parent;
//            String p2="";
//            int k=0;
//            char ch;
//            for(int i=len-2;i>=0;i--)
//            {
//                ch=p.charAt(i);
//                if(k==1)
//                {
//
//                }
//                if(ch=='/'&&i!=0&&k==0)
//                {
//                    k=1;
//                    i-=1;
//                }
////            }
//             String d=parent.substring(0,parent.length()-1);
//             String k2=d.substring(0,d.lastIndexOf('/'));
//             k2=k2.substring(0,k2.indexOf('/'));
//             session.setAttribute("parents_parent",k2);




            session.setAttribute("current_path",parent+folder_name+"/");

            session.setAttribute("current_folder_name",folder_name);
            String t=(parent+folder_name+"/").substring(8);

            session.setAttribute("parent2",t);
//        }
//        else
//            session.setAttribute("parent",parent+folder_name+"/");

        System.out.println("获取的foldername："+folder_name+"   parent: "+(String)session.getAttribute("parent") );

        String temp=(String) session.getAttribute("user_name");///////从session中获取用户名，用于在网页右上角显示并数据库查询用户所有文件

        List<com.loivgehoto.disk.model.File> list0=searchFolder((String)session.getAttribute("current_path"));
//        session.setAttribute("folder_name",folder_name);

        model.addAttribute("folder",list0);

        PageHelper.startPage(pageNum,12);
        List<com.loivgehoto.disk.model.File> list=searchFile((String)session.getAttribute("current_path"));//        list.addAll(service.SearchAllFile(temp));//                searchFolder("D:/TEST/");
        PageInfo<com.loivgehoto.disk.model.File> info=new PageInfo<>(list);
        model.addAttribute("pageInfo",info);
//        System.out.println(list.get(0).getSuffix());
//        int count=list.size();

//        MybatisUtil.closeSqlSession(sqlSession);

//        PageHelper.startPage(pageNum,pageSize);
//        List<File> list=searchFolder("D:/TEST/");
//        list.addAll(service.SearchAllFile(temp));


//        PageInfo<File> info=new PageInfo<>(list);
//        model.addAttribute("pageInfo",info);

//        session.setAttribute("name",session.getAttribute("user_name"));

        return "folder";
    }


    @RequestMapping("/share_file")//////////////////////////////////////////////////important!!!!!!这里之前用了List集合，那是错的方法，以为这个方法代表分享文件的下载页面，只显示单个文件
  //////  用List的话是返回多个File对象，用于给thymeleaf的foreach调用；由于分享文件下载页不会用到thymeleaf forreach，所以后端不能用List拿到结果，只能用单个File对象拿结果!!!!
    public String share_file(@RequestParam("fileName")String fileName,Model model)
    {
        FileService service=new FileService();
        com.loivgehoto.disk.model.File file= service.SearchSingleFile_shared(fileName);
        if(file!=null)
        {
            model.addAttribute("share_file",file);
        return "share_file";
        }
        else
            return "redirect:/404";
    }

    @RequestMapping("/share")
    public String share(@RequestParam("fileName")String fileName,HttpSession session)
    {
        String user_name=(String) session.getAttribute("user_name");//////从session中获取用户名，用于在网页右上角显示并数据库查询用户所有文件
        FileService service=new FileService();
        service.share(user_name,fileName);
        String uuid=UUID();
        service.set_url(uuid,user_name,fileName);
        return "redirect:/my_share";
    }

    @RequestMapping("/cancel_share")
    @ResponseBody
    public void cancel_share(@RequestParam("share_url")String share_url)
    {
        FileService service=new FileService();
        service.quxiao(share_url);
        System.out.println("fsdfdsf   "+share_url);
    }

    @RequestMapping("/my_share")
    public String my_share(@RequestParam(required = false,defaultValue="1",value="pageNum")Integer pageNum,
                           @RequestParam(defaultValue="15",value="pageSize")Integer pageSize,HttpSession session,Model model)
    {
        FileService service=new FileService();
        List<com.loivgehoto.disk.model.File> list=service.SearchAllFile_shared((String)session.getAttribute("user_name"));

        PageHelper.startPage(pageNum,pageSize);
        PageInfo<com.loivgehoto.disk.model.File> info=new PageInfo<>(list);
        model.addAttribute("pageInfo",info);

        return "my_share";
    }

//    @RequestMapping("/share_file")
//    public String share_file(@RequestParam("fileName")String fileName,HttpSession session,Model model)
//    {
//        String user_name=(String) session.getAttribute("user_name");//////从session中获取用户名，用于在网页右上角显示并数据库查询用户所有文件
//        FileService service=new FileService();
//        service.cancel_share(user_name,fileName);
//        return "/share_file";
//    }


    public String UUID()
    {
        UUID randomUUID = UUID.randomUUID();
        String UUID=(String)randomUUID.toString().replaceAll("-", "");;
        return UUID;
    }



    public List<com.loivgehoto.disk.model.File> searchFile(String path)
    {
        File file=new File(path);
//        System.out.println(path);
        File[] tempList = file.listFiles();
        List<com.loivgehoto.disk.model.File> list = new ArrayList<>();;
        for (int i = 0; i < tempList.length; i++)
        {
            if (tempList[i].isFile()) {
                String suffix=tempList[i].getName().substring(tempList[i].getName().lastIndexOf(".") + 1);//获取后缀名
//                System.out.println("文件：" + tempList[i]);
                com.loivgehoto.disk.model.File temp=new com.loivgehoto.disk.model.File();
                temp.setFile_name(tempList[i].getName());
                temp.setSuffix(suffix);
                temp.setFile_size(tempList[i].length());
                list.add(temp);
            }
        }
        return list;
    }


    public List<com.loivgehoto.disk.model.File> searchFolder(String path)
    {
        File file=new File(path);
        System.out.println("searchFolder method :"+path);
        File[] tempList = file.listFiles();
        List<com.loivgehoto.disk.model.File> list = new ArrayList<>();
        for (int i = 0; i < tempList.length; i++)
        {
            if (tempList[i].isDirectory()) {
//                System.out.println("文件夹：" + tempList[i]);
                com.loivgehoto.disk.model.File temp=new com.loivgehoto.disk.model.File();
                temp.setFile_name(tempList[i].getName());
                temp.setSuffix("folder");
                list.add(temp);
            }
        }
        return list;
    }



}
