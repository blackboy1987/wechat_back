package com.igomall;

import com.fasterxml.jackson.core.type.TypeReference;
import com.igomall.util.JsonUtils;
import com.igomall.util.WebUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

public class Demo07 {

    public static void main(String[] args) throws Exception{
        String baseUrl="https://www.pingshu8.com";
        String url = "/MusicList/mmc_227_9651_1.htm";

       // parseInfo(baseUrl,url,new File("d:/a.txt"),"风雨红楼(15集)");

        parseIndex();

    }

    private static void parseIndex() throws Exception{
        Document document = Jsoup.parse(new URL("https://www.pingshu8.com/music/newys_1.htm"), 3000);

        Elements elements = document.getElementsByClass("tab3");
        Iterator<Element> iterator = elements.iterator();
        String baseUrl="https://www.pingshu8.com";
        Integer index = 1;
        while (iterator.hasNext()&&index<=2){
            Element element = iterator.next();
            Element a = element.getElementsByTag("a").first();
            String url = a.attr("href");
            String name = a.text();
            String[] aa = url.split("/");
            File file = new File("d:/xiaoshuo/"+name+"_"+aa[aa.length-1]+".txt");
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            parseInfo(baseUrl,url,file,name);
            Thread.sleep(3000);
            index ++;
        }
    }


    private static void parseInfo(String baseUrl,String url,File file,String name) throws Exception{
        // 解析出页码和数量
        Document document = Jsoup.parse(new URL(baseUrl+url), 3000);
        Elements elements = document.getElementsByClass("list5").first().getElementsByTag("option");
        Iterator<Element> iterator = elements.iterator();
        while (iterator.hasNext()){
            Element element = iterator.next();
            parseContent(baseUrl,element.attr("value"),file,name);
        }
    }


    private static void parseContent(String baseUrl,String url,File file,String name) throws Exception{
        Document document = Jsoup.parse(new URL(baseUrl+url), 3000);
        Elements elements = document.getElementsByClass("a1");
        Iterator<Element> iterator = elements.iterator();
        while (iterator.hasNext()){
            Element element = iterator.next();
            Element a = element.getElementsByTag("a").first();
            String url1 = a.attr("href");
            String fileName = a.text();
            parsePage(baseUrl,url1, file,fileName,name);
        }
    }


    private static void parsePage(String baseUrl,String url,File file,String fileName,String name) throws Exception{
        String apiUrl = (baseUrl+url).replace("play","path");
        Map<String,String> map = JsonUtils.toObject(WebUtils.get(apiUrl, null), new TypeReference<Map<String, String>>() {
        });
        byte bytes[]=new byte[512];
        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fw);
        String result = map.get("urlpath");
        // fileName: 解密紫禁城 01回
        // https://play02.pingshu8.xyz:8002/02/ys/风雨红楼(15集)/10.周汝昌与红学一.mp3?t=8x6qy29f420f50adde3f41afd33de4e0d58d9&m=5E8742C6
        // https://play02.pingshu8.xyz:8002/02/ys/解密紫禁城(2集)/解密紫禁城_02.mp3?t=htaxkafbe4579138e9b927b3b81f85e861ac0&m=5E873283
        String[] results = result.split("/");
        String fileUrl="";
        for (String str:results) {
            System.out.println(str);
        }
        for(int i=0;i<results.length-2;i++){
            fileUrl = fileUrl+results[i]+"/";
        }
        fileUrl = fileUrl+name+"/"+fileName.split(" ")[0];
        try {
            String [] aa = results[results.length-1].split("_");
            if(aa.length==1){
                aa = results[results.length-1].split("\\.");
                fileUrl = fileUrl+"."+aa[aa.length-1];
            }else{
                fileUrl = fileUrl+"_"+aa[aa.length-1];
            }
            fileUrl = fileUrl.replace(".flv",".mp3");
        }catch (Exception e){
            e.printStackTrace();
        }
        bw.write(fileUrl+"\n");
        bw.flush();
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
