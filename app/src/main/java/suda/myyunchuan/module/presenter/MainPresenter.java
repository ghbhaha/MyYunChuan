package suda.myyunchuan.module.presenter;

import android.content.Context;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    private int pageIndex=1;

    public MainPresenter(MainView mainView) {
        attachView(mainView);
    }

    public void getAllList(Context context) {
        String url = Constant.getUrlFormat(context,Constant.VideoType.DIANYING, Constant.FilmTypeDetail.ALL, null, pageIndex);
        Request request = new Request.Builder().url(url).build();
        OkHttpClient okHttpClient = MyApplication.getOkHttpClient();
        Call call = okHttpClient.newCall(request);
        mvpView.showLoading();
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mvpView.hideLoading();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mvpView.hideLoading();
                try {
                    pageIndex++;
                    mvpView.showList(parseFilmInfo(new String(response.body().bytes(), "gb2312")));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private List<VideoInfo> parseFilmInfo(String xmlStr) throws Exception {
        List<VideoInfo> videoInfos = new ArrayList<>();

        xmlStr = xmlStr.replace("<l><a>165</a></l>","");
        XmlPullParser xrp = Xml.newPullParser();
        xrp.setInput(new ByteArrayInputStream(xmlStr.getBytes()), "UTF-8");
        VideoInfo videoInfo = null;

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
                        if ("m".equals(tagName)){
                            videoInfo = new VideoInfo();
                        }else if ("a".equals(tagName)) {
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
