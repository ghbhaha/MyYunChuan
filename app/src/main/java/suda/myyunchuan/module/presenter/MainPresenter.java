package suda.myyunchuan.module.presenter;

import android.content.Context;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import suda.myyunchuan.MyApplication;
import suda.myyunchuan.base.BasePersenter;
import suda.myyunchuan.module.model.VideoInfo;
import suda.myyunchuan.module.view.MainView;
import suda.myyunchuan.util.Constant;

/**
 * Created by guhaibo on 2017/3/19.
 */

public class MainPresenter extends BasePersenter<MainView> {

    private final static String TAG = "MainPresenter";
    private int pageIndex = 1;
    public MainPresenter(MainView mainView) {
        attachView(mainView);
    }


    public void initAllList(Context context, Constant.VideoType videoType) {
        pageIndex = 1;
        getAllList(context, videoType);
    }

    public void getAllList(Context context, Constant.VideoType videoType) {
        String url = Constant.getUrlFormat(context, videoType, Constant.FilmTypeDetail.ALL, Constant.TvTypeDetail.ALL, pageIndex);
        Request request = new Request.Builder().url(url)
                .cacheControl(new CacheControl.Builder().maxAge(5 * 60, TimeUnit.SECONDS).build())
                .build();
        OkHttpClient okHttpClient = MyApplication.getOkHttpClient();
        Call call = okHttpClient.newCall(request);
        mvpView.showLoading();
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mvpView.hideLoading();
                mvpView.toastShow("加载失败，请重试");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mvpView.hideLoading();
                try {
                    mvpView.showList(parseFilmInfo(new String(response.body().bytes(), "gb2312")), pageIndex == 1);
                    pageIndex++;
                } catch (Exception e) {
                    e.printStackTrace();
                    mvpView.toastShow("加载失败，请重试");
                }
            }
        });
    }

    private List<VideoInfo> parseFilmInfo(String xmlStr) throws Exception {
        List<VideoInfo> videoInfos = new ArrayList<>();
        XmlPullParser xrp = Xml.newPullParser();
        xrp.setInput(new ByteArrayInputStream(xmlStr.getBytes()), "UTF-8");
        VideoInfo videoInfo = null;

        try {
            int eventType = -1;
            eventType = xrp.getEventType();
            boolean end = false;
            while (eventType != XmlPullParser.END_DOCUMENT && !end) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.END_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG: {
                        String tagName = xrp.getName();
                        if ("l".equals(tagName)){
                            end = true;
                        }
                        if ("m".equals(tagName)) {
                            videoInfo = new VideoInfo();
                        } else if ("a".equals(tagName)) {
                            videoInfo.setName(xrp.nextText());
                        } else if ("b".equals(tagName)) {
                            videoInfo.setId(xrp.nextText());
                        } else if ("c".equals(tagName)) {
                            videoInfo.setMain(xrp.nextText());
                        } else if ("d".equals(tagName)) {
                            videoInfo.setDir(xrp.nextText());
                        } else if ("e".equals(tagName)) {
                            videoInfo.setClass1(xrp.nextText());
                        } else if ("f".equals(tagName)) {
                            videoInfo.setJianjie(xrp.nextText());
                        } else if ("g".equals(tagName)) {
                            videoInfo.setClass2(xrp.nextText());
                        } else if ("i".equals(tagName)) {
                            videoInfo.setArea(xrp.nextText());
                        } else if ("z".equals(tagName)) {
                            videoInfo.setCount(xrp.nextText());
                        } else if ("v".equals(tagName)) {
                            videoInfo.setTime(xrp.nextText());
                        } else if ("pl".equals(tagName)) {
                            videoInfo.setTitle(xrp.nextText());
                        } else if ("u".equals(tagName)) {
                            videoInfo.setVote(xrp.nextText());
                        }
                    }
                    break;
                    case XmlPullParser.END_TAG: {
                        if (xrp.getName().equals("m")) {
                            videoInfos.add(videoInfo);
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

        return videoInfos;
    }

}
