package com.igomall;

import com.igomall.util.WebUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;

public class Demo06 {

    public static void main(String[] args) throws Exception{
        String result = WebUtils.get("http://www.ysxs8.com/playdata/148/3476.js",null);
        String[] urls = result.split("\\$");
        Integer i = 1;
        for (String url:urls){
            if(StringUtils.startsWithIgnoreCase(url,"http:")){
                System.out.println(url);
                /*URL url1 = new URL(url);
                String fileName = i<10? "00"+i:i<100?("0"+i):i+"";
                System.out.println(fileName);
                File file = new File("D:/宣传部长/宣传部长"+fileName+".mp3");
                // FileUtils.writeByteArrayToFile(file,readInputStream(url1.openStream()));
                i +=1;*/

                File file1 = new File("d:/3476.txt");
                if(!file1.getParentFile().exists()){
                    file1.getParentFile().mkdirs();
                }

                byte bytes[]=new byte[512];
                FileWriter fw = new FileWriter(file1, true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(url+"\n");
                bw.flush();

            }
        }
    }


    public static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

}
