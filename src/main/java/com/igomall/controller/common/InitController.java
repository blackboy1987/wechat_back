package com.igomall.controller.common;

import com.igomall.controller.common.bilibil.BiliBiliResponse;
import com.igomall.entity.Material;
import com.igomall.entity.Sn;
import com.igomall.entity.course.Course;
import com.igomall.entity.course.Lesson;
import com.igomall.entity.member.Member;
import com.igomall.entity.member.MemberStatistics;
import com.igomall.service.MaterialService;
import com.igomall.service.SnService;
import com.igomall.service.course.CourseCategoryService;
import com.igomall.service.course.CourseService;
import com.igomall.service.course.LessonService;
import com.igomall.service.member.MemberRankService;
import com.igomall.service.member.MemberService;
import com.igomall.service.member.MemberStatisticsService;
import com.igomall.util.JsonUtils;
import com.igomall.util.WebUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/common/init")
public class InitController {

    @Autowired
    private SnService snService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseCategoryService courseCategoryService;

    @Autowired
    private LessonService lessonService;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRankService memberRankService;
    @Autowired
    private MemberStatisticsService memberStatisticsService;

    @GetMapping
    public String index(String avid) throws Exception{
        if(StringUtils.isEmpty(avid)){
            return "avid不能为空";
        }

        String url="https://www.bilibili.com/video/"+avid;
        return bilibili(url,0);
    }

    public String bilibili(String url,Integer index) throws Exception{
        bilibiliCreate(url,index);
        return "ok";
    }

    public void bilibiliCreate(String url,Integer index) throws Exception{
        String biliSn = url.replace("https://www.bilibili.com/video/","");
        biliSn = biliSn.split("/?/")[0].replace("/","");
        biliSn = biliSn.replace("av","");
        if(!courseService.biliSnExists(biliSn)){
            String baseUrl = "https://www.bilibili.com";
            //String pageListUrl="https://api.bilibili.com/x/player/pagelist?aid=80632400&jsonp=jsonp";
            String reuslt = WebUtils.get("https://api.bilibili.com/x/web-interface/view?aid="+biliSn+"&cid="+ RandomUtils.nextInt(100,1000000000),null);
            BiliBiliResponse biliBiliResponse = JsonUtils.toObject(reuslt,BiliBiliResponse.class);
            BiliBiliResponse.BiliBiliResponseData data = biliBiliResponse.getData();
            Course course = new Course();
            course.init();
            course.setVideos(data.getVideos());
            course.setTitle(data.getTitle());
            course.setImage(data.getPic());
            course.setDescription(data.getDesc());
            course.setDuration(data.getDuration());
            course.setCourseCategory(courseCategoryService.find(1L));
            course.setSn(snService.generate(Sn.Type.course));
            List<BiliBiliResponse.BiliBiliResponseData.Page> pages = data.getPages();
            for (BiliBiliResponse.BiliBiliResponseData.Page page:pages) {
                if(lessonService.bilibiliCidExists(page.getCid())){
                    continue;
                }
                Lesson lesson =new Lesson();
                lesson.setCourse(course);
                String lessonUrl = "";
                String lessonTitle = page.getPart();
                if(StringUtils.isEmpty(lessonTitle)){
                    lessonTitle="无标题";
                }
                System.out.println(lessonUrl);
                lesson.setCourse(course);
                lesson.setTitle(lessonTitle);
                lesson.setDuration(page.getDuration());
                lesson.setBilibiliCid(page.getCid());
                lesson.setOrder(page.getPage());
                lesson.setBilibiliUrl(lessonUrl);
                lesson.setSn(snService.generate(Sn.Type.lesson));
                course.getLessons().add(lesson);
            }
            courseService.save(course);
        }
        if(index<1){
            relation(biliSn,index++);
        }
    }

    public void relation(String biliSn,Integer index) throws Exception{
        Document document = Jsoup.parse(new URL("https://www.bilibili.com/video/av"+biliSn),2000);

        Element element = document.getElementsByClass("rec-list").first();
        Elements elements = element.getElementsByClass("pic-box");

        Iterator<Element> iterator = elements.iterator();
        while (iterator.hasNext()){
            Element element1 = iterator.next();
            Element a = element1.getElementsByTag("a").first();
            String url = "https://www.bilibili.com"+a.attr("href");
            bilibiliCreate(url,index);
        }

    }


    public static void main(String[] args) throws Exception{
        Document document = Jsoup.parse(new URL("https://www.bilibili.com/video/av80632400"),2000);

        Element element = document.getElementsByClass("rec-list").first();
        Elements elements = element.getElementsByClass("pic-box");

        Iterator<Element> iterator = elements.iterator();
        while (iterator.hasNext()){
            Element element1 = iterator.next();
            Element a = element1.getElementsByTag("a").first();
            String url = "https://www.bilibili.com"+a.attr("href");
        }


        System.out.println(element);

        System.out.println("https://www.bilibili.com/video/av77314550/?spm_id_from=333.788.videocard.0".replace("https://www.bilibili.com/video/","").split("/?/")[0]);
    }

    @GetMapping("/avatar")
    public String initMaterial() throws Exception{
        String url = "http://www.duoziwang.com";
        Document document = Jsoup.parse(new URL(url),2000);
        Elements elements = document.getElementsByClass("hdbox").first().getElementsByClass("hd-tab").first().getElementsByTag("a");
        Iterator<Element> iterator = elements.iterator();
        while (iterator.hasNext()) {
            Element element = iterator.next();
            String url1 = url+element.attr("href");
            initMaterial1(url1);
        }

        return "ok";
    }


    private void initMaterial1(String url) throws Exception{
        Document document = Jsoup.parse(new URL(url),2000);
        Elements elements = document.getElementsByClass("pagelist").first().getElementsByTag("li");
        Integer pageNumber = elements.size()-2;
        for (int i=1;i<=pageNumber;i++){
            String url1 = url+"list_"+i+".html";
            initMaterial2(url1);
        }
    }

    private void initMaterial2(String url) throws Exception{
        Document document = Jsoup.parse(new URL(url),2000);
        Elements elements = document.getElementsByClass("pics").first().getElementsByTag("img");
        Iterator<Element> iterator = elements.iterator();
        while (iterator.hasNext()) {
            Element element = iterator.next();
            System.out.println(element.attr("src"));
            Material material = new Material();
            material.setUrl(element.attr("src"));
            try {
                materialService.save(material);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    @GetMapping("/member")
    public String member(String url) throws Exception{
        /*Document document = Jsoup.parse(new URL(url),2000);
        Elements elements = document.getElementsByClass("fs14").first().getElementsByTag("li");
        Iterator<Element> iterator = elements.iterator();
        while (iterator.hasNext()) {
            Element element = iterator.next();
            Member member = new Member();
            member.init();
            member.setUsername(element.text());
            member.setName(member.getUsername());
            member.setEmail(member.getUsername()+"@i-gomall.com");
            member.setPassword("123456");
            member.setMemberRank(memberRankService.findDefault());
            Member member11 = memberService.findByUsername(member.getUsername());
            if(member11==null){
                memberService.save(member);
            }
        }*/
        List<Member> members = memberService.findAll();
        for (Member member:members) {
            try {
                //member.setAvatar(materialService.find(member.getId()).getUrl());
                //memberService.update(member);
                if(!memberStatisticsService.memberIdExists(member.getId())){
                    MemberStatistics memberStatistics = new MemberStatistics();
                    memberStatistics.init();
                    memberStatistics.setMemberId(member.getId());
                    memberStatisticsService.save(memberStatistics);
                }


            }catch (Exception e){
                e.printStackTrace();
            }
        }




        return "ok";
    }
}
