package com.igomall;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Demo4 {
    public static void main(String[] args) {
        String path = "D:\\JiJiDown\\Download\\2020Vue";
        String path1 = path+"_1";
        File[] files = new File(path).listFiles();
        for (File file:files) {
            File newFile = new File(path1,file.getName());
            if(!newFile.getParentFile().exists()){
                newFile.getParentFile().mkdirs();
            }

            StringBuffer sb = new StringBuffer();
            sb.append("D:\\JiJiDown\\Download\\2020Vue\\ffmpeg");
            sb.append(" -i ");
            sb.append(file.getName());
            sb.append(" logo.png ");
            sb.append(" -filter_complex \"pad=height=ih+32:color=#71cbf4,overlay=(main_w-overlay_w)/2:main_h-overlay_h\" ");
            sb.append("3_"+file.getName());
            System.out.println(sb.toString());
            Process process = null;
            Runtime run = Runtime.getRuntime();
            try {
                process = run.exec(sb.toString());
                InputStream input = process.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                String szline;
                while ((szline = reader.readLine())!= null) {
                    System.out.println(szline);
                }
                reader.close();
                process.waitFor();
                process.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("=========================================================ok");
        }

    }
}
