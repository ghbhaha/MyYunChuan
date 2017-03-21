package suda.myyunchuan.module.view;

import java.util.List;

import suda.myyunchuan.base.BaseView;
import suda.myyunchuan.module.model.VideoInfo;

/**
 * Created by guhaibo on 2017/3/19.
 */

public interface MainView extends BaseView{

    void showList(List<VideoInfo> videoInfos,boolean isRefrsh);
}
