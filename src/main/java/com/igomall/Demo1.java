package com.igomall;

import com.igomall.entity.other.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;

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
}
