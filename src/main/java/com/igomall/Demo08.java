package com.igomall;

import org.apache.commons.io.FileUtils;

import java.io.File;

public class Demo08 {
    public static void main(String[] args) throws Exception{
        String path = "/Users/blackboy/Desktop/0411/中国刑事大案纪实_刑事案件要案记录";


        File parent = new File(path);

        File[] files = parent.listFiles();
        for (File file :files) {
            String fileName = file.getName();
            //String[] names = fileName.split(" _ ");
            //if(names.length==3){
                String newFileName = fileName.replaceAll("-","_");
                try{
                    File file1 = new File(path,newFileName);
                    FileUtils.copyFile(file,file1);
                    FileUtils.deleteQuietly(file);
                }catch (Exception e){
                    e.printStackTrace();
                }
            //}
        }

    }
}
