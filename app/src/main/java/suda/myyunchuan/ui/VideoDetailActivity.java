package suda.myyunchuan.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import suda.myyunchuan.R;
import suda.myyunchuan.base.BaseActivity;
import suda.myyunchuan.module.model.VideoDetailInfo;
import suda.myyunchuan.module.presenter.VideoDetailPresenter;
import suda.myyunchuan.module.view.VideoDetailView;

public class VideoDetailActivity extends BaseActivity<VideoDetailPresenter> implements VideoDetailView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        initWidget();
        initData();
    }

    public void initWidget(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("测试");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        jcVideoPlayerStandard = new JCVideoPlayerStandard(this);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.content);
        frameLayout.addView(jcVideoPlayerStandard);
        String url = "http://202.196.222.201//kuuE/687474703A2F2F6D382E6E65746B75752E636F6D2F77772F6E617169656A69322F30332E6D7034.mp4";
        jcVideoPlayerStandard.setUp(url, JCVideoPlayer.CURRENT_STATE_NORMAL,"");
    }

    @Override
    public void initData() {
        super.initData();
        mvpPerenter.getVideoDetailInfo(this,"");
    }

    @Override
    protected VideoDetailPresenter creatPerenter() {
        return new VideoDetailPresenter(this);
    }

    @Override
    public void showVideoDetailInfo(VideoDetailInfo videoDetailInfo) {
        jcVideoPlayerStandard.post(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    private JCVideoPlayerStandard jcVideoPlayerStandard;
}
