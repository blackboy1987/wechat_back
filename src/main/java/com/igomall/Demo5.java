package com.igomall;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Demo5 {
    public static void main(String[] args) throws Exception {
        File file1 = new File("d:/20210vue.txt");
        if(!file1.getParentFile().exists()){
            file1.getParentFile().mkdirs();
        }

        byte bytes[]=new byte[512];
        FileWriter fw = new FileWriter(file1, true);
        BufferedWriter bw = new BufferedWriter(fw);

        String path = "D:\\JiJiDown\\Download\\2020Vue";
        File[] files = new File(path).listFiles();
        for (File file:files) {
            StringBuffer sb = new StringBuffer();
            sb.append("ffmpeg");
            sb.append(" -i ");
            sb.append(file.getName());
            sb.append(" -i logo.png");
            sb.append(" -filter_complex \"pad=height=ih+32:color=#000000,overlay=(main_w-overlay_w)/2:main_h-overlay_h\" ");
            sb.append("999=999"+file.getName());
            System.out.println(sb.toString());
            bw.write(sb.toString()+" && ");
            bw.flush();

            System.out.println("=========================================================ok");
        }
        bw.close();
        fw.close();
    }
}
