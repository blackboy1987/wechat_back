package com.igomall;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Demo {

    public static void main(String[] args) {
        String path = "F:\\BaiduNetdiskDownload\\新建文件夹\\web前端教程";
        File parent = new File(path);
        List<File> files = new ArrayList<>();
        listFile(parent,files);
        for (File file:files) {
            System.out.println(file.getAbsolutePath());
        }
        System.out.println(files.size());
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
