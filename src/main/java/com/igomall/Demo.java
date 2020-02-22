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


        String path = "F:\\BaiduNetdiskDownload\\02：2019千锋web前端系列教程（共1300集）";
        File parent = new File(path);
        List<File> files = new ArrayList<>();
        listFile(parent,files);
        for (int i=0;i<files.size();i++) {
            File file = files.get(i);
            String fileName = file.getName();
            if(fileName.indexOf(".mp4")>0 && StringUtils.contains(fileName,"千锋Web")){
                System.out.println(file.getAbsolutePath());
                System.out.println(file.getParentFile().getAbsolutePath());
                fileName = fileName.replace("千锋Web前端教程：","");
                fileName = fileName.replace("2019千锋Web前端：","");
                System.out.println(fileName);
                FileUtils.copyFile(file,new File(file.getParentFile().getAbsolutePath(),fileName));
                FileUtils.deleteQuietly(file);
            }
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
