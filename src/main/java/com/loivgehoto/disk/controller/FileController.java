package com.loivgehoto.disk.controller;

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
public class FileController {

    @RequestMapping("/upload")
    public String upload (@RequestParam(required = false,defaultValue="1",value="pageNum")Integer pageNum,
                          MultipartFile[] file, HttpSession session) throws IOException {
        for (MultipartFile f:file) {
            String filename = f.getOriginalFilename();
            System.out.println(filename);


            File filetemp = new File(filename);
//            String separator = File.separator;

//            if (filetemp.getParent() != null)
//            {
//                String str=filename.substring(0,filename.indexOf("/"));
//                System.out.println(str);
//
//                String suffix = "folder";
//
//                com.loivgehoto.disk.model.File temp_file = new com.loivgehoto.disk.model.File();
//                temp_file.setFile_name(str);
//
//                Date date = new Date();
//                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                String time = format1.format(date.getTime());
//                temp_file.setCreate_time(time);
//
//                String user_name = (String) session.getAttribute("user_name");
//                temp_file.setUser_name(user_name);
//                temp_file.setSuffix(suffix);
//                temp_file.setIn_folder(0);
//                temp_file.setFile_path(filename);
//
//                File temp=new File("D:/TEST/"+str);
//                temp_file.setFile_size(temp.length());
//
//                FileService service = new FileService();
//
//                service.AddFile(temp_file);
//
//                File dest = new File("D:/TEST/" + filename);
//                if (!dest.exists()) {
//                    //????????????????????????????????????
//                    dest.mkdirs();
//                }
//                f.transferTo(dest); // ????????????
//
//            }
          String user_name=(String) session.getAttribute("user_name");
            System.out.println("??????????????????" + filetemp.getParent());
            if (filetemp.getParent() != null) {


                File dest = new File("D:/TEST/"+ user_name+"/"+filename);
                System.out.println("dest+    " + dest);
                if (!dest.exists()) {
                    //????????????????????????????????????
                    dest.mkdirs();
                }
                f.transferTo(dest); // ????????????
            } else {
                String suffix = filename.substring(filename.lastIndexOf(".") + 1);//???????????????

                com.loivgehoto.disk.model.File file2 = new com.loivgehoto.disk.model.File();

                System.out.println("?????????: " + filename + " " + suffix);

                File dest = new File("D:/TEST/"+ user_name+"/"+filename);
                System.out.println("dest:   " + dest);
                if (!dest.exists()) {
                    //????????????????????????????????????
                    dest.mkdirs();
                }
                f.transferTo(dest); // ????????????

                FileService service = new FileService();
                String temp = (String) session.getAttribute("user_name");

                Date date = new Date();
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = format1.format(date.getTime());

                file2.setFile_name(filename);
                file2.setFile_size(f.getSize());
                file2.setCreate_time(time);
                file2.setUser_name(temp);
                file2.setSuffix(suffix);
                file2.setIn_folder(0);
//                String realPath = "D:/TEST/" + filename;
//                file2.setFile_path(realPath);
                service.AddFile(file2);

            }

//                String suffix = filename.substring(filename.lastIndexOf(".") + 1);//???????????????
//
//                com.loivgehoto.disk.model.File file2 = new com.loivgehoto.disk.model.File();
//
//                System.out.println("?????????: " + filename + " " + suffix);
//
//                File dest = new File("D:/TEST/" + filename);
//                if (!dest.exists()) {
//                    //????????????????????????????????????
//                    dest.mkdirs();
//                }
//                f.transferTo(dest); // ????????????
//
//                FileService service = new FileService();
//                String temp = (String) session.getAttribute("user_name");
//
//                Date date = new Date();
//                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                String time = format1.format(date.getTime());
//
//                file2.setFile_name(filename);
//                file2.setFile_size(f.getSize());
//                file2.setCreate_time(time);
//                file2.setUser_name(temp);
//                file2.setSuffix(suffix);
//                file2.setIn_folder(0);
//                service.AddFile(file2);
        }

//        System.out.println("pagenum" + pageNum);

        return "redirect:/home";////???ajax????????????????????????????????????ajax??????,????????????????????????????????????
    }



    @RequestMapping("/download_in_folder")
    public String fileDownLoad_in_folder(HttpServletResponse response, @RequestParam("fileName") String fileName,HttpSession session) throws UnsupportedEncodingException {
        System.out.println("download"+session.getAttribute("current_path"));

//        response.setHeader("Content-Disposition", "attachment; fileName="+  fileName +";filename*=utf-8''"+ URLEncoder.encode(fileName,"UTF-8"));
        String temp=session.getAttribute("current_path")+fileName;
        System.out.println(temp);
        File file = new File(temp);

        System.out.println("?????????"+fileName);
        if(!file.exists()){
            System.out.println("?????????????????????");
            return "home";
//            return "?????????????????????";
        }
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
//        response.setHeader("Content-Disposition", "attachment;filename=" + fileName );

        response.setHeader("Content-Disposition", "attachment; filename="
                + new String(fileName.getBytes("utf-8"),"ISO-8859-1"));

        System.out.println("xin"+fileName);

        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));) {
            byte[] buff = new byte[1024];
            OutputStream os  = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
//            log.error("{}",e);
            return "????????????";
        }
        return "home";
    }

    @RequestMapping("/download")
    public String fileDownLoad(HttpServletResponse response, @RequestParam("fileName") String fileName,HttpSession session) throws UnsupportedEncodingException {
//        System.out.println("download"+session.getAttribute("parent"));
        String user_name=(String) session.getAttribute("user_name");

//        response.setHeader("Content-Disposition", "attachment; fileName="+  fileName +";filename*=utf-8''"+ URLEncoder.encode(fileName,"UTF-8"));
        String temp="D:/TEST/"+ user_name+"/"+fileName;
        System.out.println(temp);
        File file = new File(temp);

        System.out.println("?????????"+fileName);
        if(!file.exists()){
            System.out.println("?????????????????????");
            return "home";
//            return "?????????????????????";
        }
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
//        response.setHeader("Content-Disposition", "attachment;filename=" + fileName );

        response.setHeader("Content-Disposition", "attachment; filename="
                + new String(fileName.getBytes("utf-8"),"ISO-8859-1"));

        System.out.println("xin"+fileName);

        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));) {
            byte[] buff = new byte[1024];
            OutputStream os  = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
//            log.error("{}",e);
            return "????????????";
        }
        return "home";
    }

    @RequestMapping("/download_in_recycle_bin")
    public void fileDownLoad_in_recycle_bin(HttpServletResponse response, @RequestParam("fileName") String fileName,HttpSession session) throws UnsupportedEncodingException {
//        System.out.println("download"+session.getAttribute("parent"));
        String user_name=(String) session.getAttribute("user_name");
//        response.setHeader("Content-Disposition", "attachment; fileName="+  fileName +";filename*=utf-8''"+ URLEncoder.encode(fileName,"UTF-8"));
        String temp="D:/TEST/"+ user_name+"/?????????/"+fileName;
        System.out.println(temp);
        File file = new File(temp);

        System.out.println("?????????"+fileName);
        if(!file.exists()){
            System.out.println("?????????????????????");
//            return "home";
//            return "?????????????????????";
        }
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
//        response.setHeader("Content-Disposition", "attachment;filename=" + fileName );

        response.setHeader("Content-Disposition", "attachment; filename="
                + new String(fileName.getBytes("utf-8"),"ISO-8859-1"));

        System.out.println("xin"+fileName);

        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));) {
            byte[] buff = new byte[1024];
            OutputStream os  = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
//            log.error("{}",e);
//            return "????????????";
        }

    }

    @RequestMapping("/download_in_share")
    public void download_in_share(HttpServletResponse response, @RequestParam("fileName") String fileName,@RequestParam("user_name") String user_name,HttpSession session) throws UnsupportedEncodingException {
        System.out.println("download_in_share");
        String temp="D:/TEST/"+ user_name+"/"+fileName;
        System.out.println(temp);
        File file = new File(temp);

        System.out.println("?????????"+fileName);
        if(!file.exists()){
            System.out.println("?????????????????????");

//            return "?????????????????????";
        }
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
//        response.setHeader("Content-Disposition", "attachment;filename=" + fileName );

        response.setHeader("Content-Disposition", "attachment; filename="
                + new String(fileName.getBytes("utf-8"),"ISO-8859-1"));

        System.out.println("xin"+fileName);

        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));) {
            byte[] buff = new byte[1024];
            OutputStream os  = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
//            log.error("{}",e);

        }

    }


    @RequestMapping("/delete_in_folder")
    public void DeleteFile_in_folder(HttpServletResponse response, @RequestParam("fileName") String fileName,HttpSession session)
    {
        FileService service = new FileService();
//        service.DeleteFile(fileName);

        String temp=session.getAttribute("current_path")+fileName;

        File delFile = new File(temp);
        if(delFile.isFile() && delFile.exists()) {
            delFile.delete();
//            logger.info("??????????????????");

        }else {
//            logger.info("??????????????????????????????");
        }
    }

    @RequestMapping("/move_to_recycle_bin")
    public void move_to_recycle_bin(HttpServletResponse response, @RequestParam("fileName") String fileName,HttpSession session)
    {
        FileService service = new FileService();
//        service.DeleteFile(fileName);
        String user_name=(String) session.getAttribute("user_name");
        service.From_rootFolder_to_recycle_bin(user_name,fileName);
        String temp="D:/TEST/"+user_name+"/"+fileName;


        File startFile=new File(temp);

        File endFile=new File("D:/TEST/"+user_name+"/?????????/"+fileName);

//        startFile.renameTo(endFile);/////////////?????????????????????????????????
////////////////////////////////
        try {//////////?????????????????????????????????
            if (startFile.renameTo(endFile)) {
                System.out.println("??????????????????");
            } else {
                System.out.println("??????????????????");
            }
        }catch(Exception e) {
            System.out.println("????????????????????????");
        }
///////////////////////////////


//        System.out.println("/delete    "+temp);

//        File delFile = new File(temp);



//        if(delFile.isFile() && delFile.exists()) {
//            delFile.delete();
////            logger.info("??????????????????");
//
//        }else {
////            logger.info("??????????????????????????????");
//        }


    }

    @RequestMapping("/restore")
    public void restore(HttpServletResponse response, @RequestParam("fileName") String fileName,HttpSession session)///////////////////////??????????????????????????????????????????????????????
    {
        System.out.println("??????????????????????????????????????????-------------------------");
        FileService service = new FileService();
//        service.DeleteFile(fileName);
        String user_name=(String) session.getAttribute("user_name");

        service.From_recycle_bin_to_rootFolder(user_name,fileName);

        String temp="D:/TEST/"+user_name+"/?????????/"+fileName;


        File startFile=new File(temp);

        File endFile=new File("D:/TEST/"+user_name+"/"+fileName);

//        startFile.renameTo(endFile);/////////////?????????????????????????????????
////////////////////////////////
        try {//////////?????????????????????????????????
            if (startFile.renameTo(endFile)) {
                System.out.println("??????????????????");
            } else {
                System.out.println("??????????????????");
            }
        }catch(Exception e) {
            System.out.println("????????????????????????");
        }
        System.out.println("??????????????????????????????????????????-------------------------");
///////////////////////////////


//        System.out.println("/delete    "+temp);

//        File delFile = new File(temp);



//        if(delFile.isFile() && delFile.exists()) {
//            delFile.delete();
////            logger.info("??????????????????");
//
//        }else {
////            logger.info("??????????????????????????????");
//        }

    }


    @RequestMapping("/DeleteFolder")
    public void DeleteFolder(HttpServletResponse response, @RequestParam("folderName") String fileName,HttpSession session)
    {
        String user_name=(String) session.getAttribute("user_name");
        String temp="D:/TEST/"+user_name+"/?????????/"+fileName;

        File delFile = new File(temp);
        deleteFile(delFile);
    }

    public static boolean deleteFile(File dirFile) {
        // ??????dir????????????????????????????????????
        if (!dirFile.exists()) {
            return false;
        }
        if (dirFile.isFile()) {
            return dirFile.delete();
        } else {

            for (File file : dirFile.listFiles()) {
                deleteFile(file);
            }
        }
        return dirFile.delete();
    }


    @RequestMapping("/DeleteFile")
    public void DeleteFile(HttpServletResponse response, @RequestParam("fileName") String fileName,HttpSession session)
    {
        FileService service = new FileService();
        service.DeleteFile(fileName);

//        service.From_rootFolder_to_recycle_bin(fileName);
        String user_name=(String) session.getAttribute("user_name");
        String temp="D:/TEST/"+user_name+"/?????????/"+fileName;
//
//
//        File startFile=new File(temp);
//
//        File endFile=new File("D:/TEST/?????????/"+fileName);
//
////        startFile.renameTo(endFile);/////////////?????????????????????????????????
//////////////////////////////////
//        try {//////////?????????????????????????????????
//            if (startFile.renameTo(endFile)) {
//                System.out.println("??????????????????");
//            } else {
//                System.out.println("??????????????????");
//            }
//        }catch(Exception e) {
//            System.out.println("????????????????????????");
//        }
/////////////////////////////////


//        System.out.println("/delete    "+temp);

        File delFile = new File(temp);



        if(delFile.isFile() && delFile.exists()) {
            delFile.delete();
//            logger.info("??????????????????");

        }else {
//            logger.info("??????????????????????????????");
        }
    }


    @RequestMapping("/delete_folder")
    public void DeleteFolder(HttpServletResponse response, @RequestParam("fileName") String fileName)
    {
        FileService service = new FileService();
        service.DeleteFile(fileName);

//        File delFile = new File();


//
//        if(delFile.isFile() && delFile.exists()) {
//            delFile.delete();
////            logger.info("??????????????????");
//
//        }else {
////            logger.info("??????????????????????????????");
//        }
   }



//    @RequestMapping("/image")
//    @ResponseBody
//    public void returnImage(@RequestParam("imgName") String imgName,       HttpServletResponse response){
//        FileInputStream fis = null;
//        OutputStream os = null;
//        String filepath = "D:/TEST/"+imgName;     //path???????????????????????????????????????
//        File file = new File(filepath);
//        if(file.exists()){
//            try {
//                fis = new FileInputStream(file);
//                long size = file.length();
//                byte[] temp = new byte[(int) size];
//                fis.read(temp, 0, (int) size);
//                fis.close();
//                byte[] data = temp;
//                response.setContentType("image/png");
//                os = response.getOutputStream();
//                os.write(data);
//                os.flush();
//                os.close();
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

//    @RequestMapping("/image_in_bin")
//    @ResponseBody
//    public void returnImage_in_recycle_bin(@RequestParam("imgName") String imgName,       HttpServletResponse response){
//        FileInputStream fis = null;
//        OutputStream os = null;
//        String filepath = "D:/TEST/?????????/"+imgName;     //path???????????????????????????????????????
//        File file = new File(filepath);
//        if(file.exists()){
//            try {
//                fis = new FileInputStream(file);
//                long size = file.length();
//                byte[] temp = new byte[(int) size];
//                fis.read(temp, 0, (int) size);
//                fis.close();
//                byte[] data = temp;
//                response.setContentType("image/png");
//                os = response.getOutputStream();
//                os.write(data);
//                os.flush();
//                os.close();
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

//    @RequestMapping("/playVideo")
//    public void showVideo(HttpServletResponse response, @RequestParam("fileName")String fileName) throws IOException {
//
//        show(response,fileName,"video");
//    }
//    /**
//     * ????????????
//     * @param response
//     * @param fileName  ???????????????
//     * @param type  ???????????????
//     */
//    public void  show(HttpServletResponse response, String fileName,String type){
//        try{
//            FileInputStream fis = new FileInputStream("D:/TEST/"+fileName); // ???byte????????????????????????
//            int i=fis.available(); //??????????????????
//            byte data[]=new byte[i];
//            fis.read(data);  //?????????
//            response.setContentType(type+"/*"); //???????????????????????????
//            OutputStream toClient=response.getOutputStream(); //????????????????????????????????????????????????
//            toClient.write(data);  //????????????
//            toClient.flush();
//            toClient.close();
//            fis.close();
//        }catch(Exception e){
//            e.printStackTrace();
//            System.out.println("???????????????");
//        }
//    }


}

