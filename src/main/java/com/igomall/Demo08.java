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
            fileName = fileName.replaceAll(" ","");
            String[] names = fileName.split("-");
            System.out.println(names.length);
            if(names.length==3){
                fileName = names[1]+".mp3";
                System.out.println(fileName);
                try{
                    File file1 = new File(path,fileName);
                    FileUtils.copyFile(file,file1);
                    FileUtils.deleteQuietly(file);
                    System.out.println("=================================");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }

    }
}
