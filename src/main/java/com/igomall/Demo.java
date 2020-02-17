package com.igomall;

import java.io.File;

public class Demo {

    public static void main(String[] args) {
        String path = "D:\\Program Files\\JiJiDown\\Download\\【千锋】2020Vue全套教程全开源（完整版）";
        File parent = new File(path);
        File[] files = parent.listFiles();
        for (int i=1;i<=files.length;i++) {
            File file = files[i];
            String fileName = file.getName();
            fileName = fileName.replace(i+".千锋Web前端教程：","");
            System.out.println(fileName);
        }
    }
}
