package com.igomall;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.igomall.entity.other.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Demo1 {

    public static ToolCategory parse(String tag) throws Exception {
        ToolCategory toolCategory = new ToolCategory();
        toolCategory.setName(tag+"开发工具");
        String url = "https://www.lmonkey.com/tools/"+tag;
        Document document = Jsoup.parse(new URL(url),10000);
        Element element = document.getElementsByClass("main-content").first();
        Elements elements = element.getElementsByTag("h4");
        Elements element1s = element.getElementsByClass("row");
        for (int i=0;i<elements.size();i++){
            String categoryName = elements.get(i).text();
            ToolCategory child = new ToolCategory();
            child.setName(categoryName);
            child.setParent(toolCategory);
            toolCategory.getChildren().add(child);

            Element element1 = element1s.get(i);
            Elements nodes = element1.getElementsByClass("col-sm-3");
            if(nodes!=null && nodes.size()>0){
                for (int j=0;j<nodes.size();j++){
                    Element element2 = nodes.get(j);
                    String image = element2.getElementsByTag("img").first().attr("src");
                    String name = element2.getElementsByTag("strong").first().text();
                    String memo = element2.getElementsByClass("overflowClip_2").first().text();
                    ToolItem toolItem = new ToolItem();
                    toolItem.setIcon(image);
                    toolItem.setName(name);
                    toolItem.setMemo(memo);
                    toolItem.setIsPublication(true);
                    toolItem.setToolCategory(child);
                    child.getToolItems().add(toolItem);
                }
            }
        }

        return toolCategory;
    }



    public static BookCategory parse1(String tag) throws Exception {
        BookCategory toolCategory = new BookCategory();
        toolCategory.setName(tag+"开发工具");
        String url = "https://www.lmonkey.com/ebook/"+tag;
        Document document = Jsoup.parse(new URL(url),10000);
        Element element = document.getElementsByClass("main-content").first();
        Elements elements = element.getElementsByTag("h4");
        Elements element1s = element.getElementsByClass("row");
        for (int i=0;i<elements.size();i++){
            String categoryName = elements.get(i).text();
            BookCategory child = new BookCategory();
            child.setName(categoryName);
            child.setParent(toolCategory);
            child.setOrder(i+1);
            toolCategory.getChildren().add(child);

            Element element1 = element1s.get(i);
            Elements nodes = element1.getElementsByClass("col-sm-3");
            if(nodes!=null && nodes.size()>0){
                for (int j=0;j<nodes.size();j++){
                    Element element2 = nodes.get(j);
                    String image = element2.getElementsByTag("img").first().attr("src");
                    String name = element2.getElementsByTag("strong").first().text();
                    String memo = element2.getElementsByClass("overflowClip_2").first().text();
                    BookItem toolItem = new BookItem();
                    toolItem.setIcon(image);
                    toolItem.setName(name);
                    toolItem.setMemo(memo);
                    toolItem.setOrder(j+1);
                    toolItem.setIsPublication(true);
                    toolItem.setBookCategory(child);
                    child.getBookItems().add(toolItem);
                }
            }
        }
        return toolCategory;
    }


    public static ProjectCategory parse2(String tag) throws Exception {
        ProjectCategory toolCategory = new ProjectCategory();
        toolCategory.setName(tag+"开发工具");
        String url = "https://www.lmonkey.com/ebook/"+tag;
        Document document = Jsoup.parse(new URL(url),10000);
        Element element = document.getElementsByClass("main-content").first();
        Elements elements = element.getElementsByTag("h4");
        Elements element1s = element.getElementsByClass("row");
        for (int i=0;i<elements.size();i++){
            String categoryName = elements.get(i).text();
            ProjectCategory child = new ProjectCategory();
            child.setName(categoryName);
            child.setParent(toolCategory);
            toolCategory.getChildren().add(child);

            Element element1 = element1s.get(i);
            Elements nodes = element1.getElementsByClass("col-sm-3");
            if(nodes!=null && nodes.size()>0){
                for (int j=0;j<nodes.size();j++){
                    Element element2 = nodes.get(j);
                    String image = element2.getElementsByTag("img").first().attr("src");
                    String name = element2.getElementsByTag("strong").first().text();
                    String memo = element2.getElementsByClass("overflowClip_2").first().text();
                    ProjectItem toolItem = new ProjectItem();
                    toolItem.setIcon(image);
                    toolItem.setName(name);
                    toolItem.setMemo(memo);
                    toolItem.setIsPublication(true);
                    toolItem.setProjectCategory(child);
                    child.getProjectItems().add(toolItem);
                }
            }
        }
        return toolCategory;
    }


    public static List<String> parse3() throws Exception{

        File file = new File("d:/project.txt");
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }

        byte bytes[]=new byte[512];
        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fw);

        List<String> urls = new ArrayList<>();
        String url = "https://ishangedu.oss-cn-hangzhou.aliyuncs.com/project.html";
        Document document = Jsoup.parse(new URL(url),10000);
        Elements elements = document.getElementsByClass("tool-menu");
        for (int i=0;i<elements.size();i++) {
           try{
               Element a = elements.get(i).getElementsByTag("a").first();
               String link = a.attr("href");
               bw.write(link+"\r\n");
               bw.flush();
               urls.add(a.attr("href"));
           }catch (Exception e){
               System.out.println(elements.get(i).html());
               e.printStackTrace();
           }
        }

        bw.close();
        fw.close();

        return urls;
    }

    public static void rename() throws Exception{
        String path = "E:\\迅雷\\tools";

        File parent = new File(path);
        File[] files = parent.listFiles();
        for (File file:files) {
            if(StringUtils.endsWith(file.getName(),".pdf.pdf")){
                String newPath = file.getAbsolutePath().substring(0,file.getAbsolutePath().length()-4);
                File newFile =new File(newPath);
                FileUtils.copyFile(file,newFile);
                FileUtils.deleteQuietly(file);
            }else if(StringUtils.endsWith(file.getName(),".zip.zip")){
                String newPath = file.getAbsolutePath().substring(0,file.getAbsolutePath().length()-4);
                File newFile =new File(newPath);
                FileUtils.copyFile(file,newFile);
                FileUtils.deleteQuietly(file);
            }else if(StringUtils.endsWith(file.getName(),".gz.gz")){
                String newPath = file.getAbsolutePath().substring(0,file.getAbsolutePath().length()-3);
                File newFile =new File(newPath);
                FileUtils.copyFile(file,newFile);
                FileUtils.deleteQuietly(file);
            }
        }
    }


    public static void main(String[] args) throws Exception{
        rename();
    }
}
