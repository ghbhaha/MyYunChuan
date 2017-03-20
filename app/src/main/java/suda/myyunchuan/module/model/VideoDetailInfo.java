package suda.myyunchuan.module.model;

import java.util.List;

/**
 * Created by guhaibo on 2017/3/19.
 */

public class VideoDetailInfo {
    private String Contentnumber;
    private String actor;
    private String brief;
    private String channelid;
    private String director;
    private String filmtype;
    private String name;
    private String pinglun1;
    private String publishTime;
    private String region;
    private List<String> urls;

    public String getContentnumber() {
        return Contentnumber;
    }

    public void setContentnumber(String contentnumber) {
        Contentnumber = contentnumber;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getChannelid() {
        return channelid;
    }

    public void setChannelid(String channelid) {
        this.channelid = channelid;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getFilmtype() {
        return filmtype;
    }

    public void setFilmtype(String filmtype) {
        this.filmtype = filmtype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinglun1() {
        return pinglun1;
    }

    public void setPinglun1(String pinglun1) {
        this.pinglun1 = pinglun1;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}
