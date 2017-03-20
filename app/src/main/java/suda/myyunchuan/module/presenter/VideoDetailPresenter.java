package suda.myyunchuan.module.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import suda.myyunchuan.MyApplication;
import suda.myyunchuan.base.BasePersenter;
import suda.myyunchuan.module.model.VideoDetailInfo;
import suda.myyunchuan.module.view.VideoDetailView;
import suda.myyunchuan.util.Constant;

/**
 * Created by guhaibo on 2017/3/19.
 */

public class VideoDetailPresenter extends BasePersenter<VideoDetailView> {

    public VideoDetailPresenter(VideoDetailView videoDetailView) {
        mvpView = videoDetailView;
    }

    public void getVideoDetailInfo(final Context context, String videoId) {
        String url = Constant.getVideoDetailUrl(context, videoId);
        String url2 = Constant.getVideoDownUrl(context, videoId);
        final Request requestDetailInfo = new Request.Builder().url(url).build();
        final Request requestDownUrl = new Request.Builder().url(url2).build();
        OkHttpClient okHttpClient = MyApplication.getOkHttpClient();
        final Call callVideoDetail = okHttpClient.newCall(requestDetailInfo);
        final Call callDownUrl = okHttpClient.newCall(requestDownUrl);
        mvpView.showLoading();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    VideoDetailInfo videoDetailInfo = parseVideoDetailInfo(new String(callVideoDetail.execute().body().bytes(), "gb2312"));
                    List<String> downUrls = parseVideoUrls(context,new String(callDownUrl.execute().body().bytes(), "gb2312"));
                    videoDetailInfo.setUrls(downUrls);
                    mvpView.showVideoDetailInfo(videoDetailInfo);
                    mvpView.hideLoading();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private VideoDetailInfo parseVideoDetailInfo(String xmlStr) throws Exception {
        VideoDetailInfo videoDetailInfo = null;
        xmlStr = xmlStr.replace("<l><a>165</a></l>", "");
        XmlPullParser xrp = Xml.newPullParser();
        xrp.setInput(new ByteArrayInputStream(xmlStr.getBytes()), "UTF-8");
        try {
            int eventType = -1;
            eventType = xrp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.END_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG: {
                        String tagName = xrp.getName();
                        if ("film".equals(tagName)) {
                            videoDetailInfo = new VideoDetailInfo();
                        } else if ("name".equals(tagName)) {
                            videoDetailInfo.setName(xrp.nextText());
                        } else if ("director".equals(tagName)) {
                            videoDetailInfo.setDirector(xrp.nextText());
                        } else if ("actor".equals(tagName)) {
                            videoDetailInfo.setActor(xrp.nextText());
                        } else if ("region".equals(tagName)) {
                            videoDetailInfo.setRegion(xrp.nextText());
                        } else if ("channelid".equals(tagName)) {
                            videoDetailInfo.setChannelid(xrp.nextText());
                        } else if ("filmtype".equals(tagName)) {
                            videoDetailInfo.setFilmtype(xrp.nextText());
                        } else if ("publishTime".equals(tagName)) {
                            videoDetailInfo.setPublishTime(xrp.nextText());
                        } else if ("languages".equals(tagName)) {

                        } else if ("Contentnumber".equals(tagName)) {
                            videoDetailInfo.setContentnumber(xrp.nextText());
                        } else if ("adddate".equals(tagName)) {

                        } else if ("pinglun1".equals(tagName)) {
                            videoDetailInfo.setPinglun1(xrp.nextText());
                        } else if ("brief".equals(tagName)) {
                            videoDetailInfo.setBrief(xrp.nextText());
                        }
                    }
                    break;
                    case XmlPullParser.END_TAG: {
                        if (xrp.getName().equals("film")) {

                        }
                    }
                    break;
                    case XmlPullParser.TEXT:
                        break;
                    default:
                        break;
                }

                eventType = xrp.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return videoDetailInfo;
    }

    private List<String> parseVideoUrls(Context context,String xmlStr) throws Exception {
        List<String> urls = new ArrayList<>();
        int start = xmlStr.indexOf("<c>") + 3;
        int end = xmlStr.indexOf("</c>");
        xmlStr = xmlStr.substring(start, end);
        String[] strings = xmlStr.split(",");
        for (String url : strings){
            if (!TextUtils.isEmpty(url)) {
                urls.add(Constant.formatDownUrl(context,url));
            }
        }
        return urls;
    }
}
