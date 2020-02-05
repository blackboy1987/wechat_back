package com.igomall.controller.common.bilibil;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BiliBiliResponse implements Serializable {

    private Integer code;

    private String message;

    private BiliBiliResponseData data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BiliBiliResponseData getData() {
        return data;
    }

    public void setData(BiliBiliResponseData data) {
        this.data = data;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class BiliBiliResponseData implements Serializable {

        private String aid;

        private Integer videos;

        private String pic;

        private String title;

        private String desc;

        private Long duration;


        private List<Page> pages = new ArrayList<>();

        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        public Integer getVideos() {
            return videos;
        }

        public void setVideos(Integer videos) {
            this.videos = videos;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public Long getDuration() {
            return duration;
        }

        public void setDuration(Long duration) {
            this.duration = duration;
        }

        public List<Page> getPages() {
            return pages;
        }

        public void setPages(List<Page> pages) {
            this.pages = pages;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Page implements Serializable{
            private Long cid;

            private Integer page;

            private String part;

            private Long duration;

            public Long getCid() {
                return cid;
            }

            public void setCid(Long cid) {
                this.cid = cid;
            }

            public Integer getPage() {
                return page;
            }

            public void setPage(Integer page) {
                this.page = page;
            }

            public String getPart() {
                return part;
            }

            public void setPart(String part) {
                this.part = part;
            }

            public Long getDuration() {
                return duration;
            }

            public void setDuration(Long duration) {
                this.duration = duration;
            }
        }
    }
}
