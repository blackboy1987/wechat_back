package com.igomall;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Demo {

    public static void main(String[] args) throws Exception{
        /*String path = "F:\\BaiduNetdiskDownload\\新建文件夹\\web前端教程";
        File parent = new File(path);
        List<File> files = new ArrayList<>();
        listFile(parent,files);
        for (File file:files) {
            System.out.println(file.getAbsolutePath());
        }
        System.out.println(files.size());*/


        String path = "D:\\Program Files\\JiJiDown\\Download\\2019年最全最新Vue、Vuejs教程，从入门到精通";
        File parent = new File(path);
        //List<File> files = new ArrayList<>();
       // listFile(parent,files);
        File[] files = parent.listFiles();
        for (int i=0;i<files.length;i++) {
            File file = files[i];
            String fileName = file.getName();
            String[] fileNames = fileName.split("\\.");
            // 01-(了解)Vuejs课程介绍(Av89760569,P1);
            String aa = fileNames[1].substring(3);
            String bb = aa.substring(0,aa.lastIndexOf("("));
            fileName = fileNames[0]+"."+ bb + "." +fileNames[2];
            System.out.println(fileName);

            FileUtils.copyFile(file,new File(file.getParentFile().getAbsolutePath(),fileName));
            FileUtils.deleteQuietly(file);
        }

    }


    public static List<File> listFile(File parent,List<File> files){
        if(parent.isDirectory()){
            File[] files1 = parent.listFiles();
            for (int i=0;i<files1.length;i++) {
                listFile(files1[i],files);
            }
        }else{
            files.add(parent);
        }
        return files;

    }
}
