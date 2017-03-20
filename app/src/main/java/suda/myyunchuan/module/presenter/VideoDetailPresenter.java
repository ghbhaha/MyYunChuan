package suda.myyunchuan.module.presenter;

import android.content.Context;

import suda.myyunchuan.base.BasePersenter;
import suda.myyunchuan.module.view.VideoDetailView;

/**
 * Created by guhaibo on 2017/3/19.
 */

public class VideoDetailPresenter extends BasePersenter {

    public VideoDetailPresenter(VideoDetailView videoDetailView) {
        mvpView = videoDetailView;
    }

    public void getVideoDetailInfo(Context context,String videoId){

    }

}
