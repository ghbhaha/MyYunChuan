package suda.myyunchuan.util;

import android.content.Context;
import android.text.TextUtils;

/**
 * Created by guhaibo on 2017/3/19.
 */

public class Constant {

    private static String URL_FORMAT_LOCATION = "bar-list-%d_%d_adddate.xml&typ=m";
    private static String URL_FORMAT_ALL = "&xml=bar-list-%d_adddate.xml&typ=m";
    private static int ALL_MASK = -99;


    public static String getImgUrl(Context context, String id) {
        return getBaseUrl(context) + "/mov/" + id + "/1.jpg";
    }

    public static String getVideoDetailUrl(Context context, String id) {
        return getBaseUrl(context) + "/mov/" + id + "/film.xml";
    }

    public static String getVideoDownUrl(Context context, String id) {
        return getBaseUrl(context) + "/mov/" + id + "/url2.xml";
    }

    public static String getBaseUrl(Context context) {
        //218.29.109.229
        //202.196.222.201
        return SPUtils.get(context, "ip", "http://218.29.109.229").toString();
    }

    public static String formatDownUrl(Context context, String orgUrl) {
        return getBaseUrl(context) + "/kuu"
                + orgUrl.charAt(0)
                + "/"
                + orgUrl.substring(orgUrl.lastIndexOf("\\") + 1);
    }

    public static String getUrlFormat(Context context, VideoType videoType, FilmTypeDetail filmTypeDetail, TvTypeDetail tvTypeDetail, int pageIndex) {
        String endUrl = "";
        switch (videoType) {
            case DIANYING:
                if (filmTypeDetail.typeDetailCode != ALL_MASK)
                    endUrl = String.format(URL_FORMAT_LOCATION, filmTypeDetail.typeDetailCode, filmTypeDetail.typeDetailCode);
                break;
            case DAINSHIJU:
                if (tvTypeDetail.typeDetailCode != ALL_MASK)
                    endUrl = String.format(URL_FORMAT_LOCATION, tvTypeDetail.typeDetailCode, tvTypeDetail.typeDetailCode);
                break;
        }
        if (TextUtils.isEmpty(endUrl)) {
            endUrl = String.format(URL_FORMAT_ALL, videoType.typeCode);
        }

        if (pageIndex == 0)
            pageIndex = 1;
        int start = (pageIndex - 1) * 12;
        int end = start + 12;
        return getBaseUrl(context) + "/readxml.asp?num1=" + start + "&num2=" + end + endUrl;
    }

    public enum VideoType {
        DIANYING("电影", 25), DAINSHIJU("电视剧", 30),
        ZONGYI("综艺", 41), DONGMAN("动漫", 37), GONGKAIKE("公开课", 52),
        JILUPIAN("纪录片", 53), JIANGZUO("讲座", 54);
        public String typeName;
        public int typeCode;

        VideoType(String typeName, int typeCode) {
            this.typeName = typeName;
            this.typeCode = typeCode;
        }
    }

    public enum FilmTypeDetail {
        ALL("全部", ALL_MASK), XIJU("喜剧", 1), DONGZUO("动作", 2),
        AIQING("爱情", 3),
        KEHAUN("科幻", 4), ZHANZHEN("战争", 6), KONGBU("恐怖", 8),
        JUQING("剧情", 270), ZUIAN("罪案", 9);
        public String typeDetailName;
        public int typeDetailCode;

        FilmTypeDetail(String typeDetailName, int typeDetailCode) {
            this.typeDetailName = typeDetailName;
            this.typeDetailCode = typeDetailCode;
        }
    }

    public enum TvTypeDetail {
        ALL("全部", ALL_MASK), OUXIANG("偶像", 11), LISHI("历史", 12),
        WUXIA("武侠", 13), DIEZHAN("谍战", 14), DUSHI("都市", 15),
        ZHANZHEN("战争", 16), XINGZHEN("刑侦", 17), LUNLI("伦理", 22);

        public String typeDetailName;
        public int typeDetailCode;

        TvTypeDetail(String typeDetailName, int typeDetailCode) {
            this.typeDetailName = typeDetailName;
            this.typeDetailCode = typeDetailCode;
        }
    }

    public enum FilmLocation {
        DALU("大陆", 12), GANGTAI("港台", 15), OUMEI("欧美", 13), RIHANG("日韩", 14);
        public String locationName;
        public int locationCode;

        FilmLocation(String locationName, int locationCode) {
            this.locationName = locationName;
            this.locationCode = locationCode;
        }
    }

    public enum TvLocation {
        DALU("大陆", 18), GANGTAI("港台", 21), OUMEI("欧美", 19), RIHANG("日韩", 20);
        public String locationName;
        public int locationCode;

        TvLocation(String locationName, int locationCode) {
            this.locationName = locationName;
            this.locationCode = locationCode;
        }
    }
}
