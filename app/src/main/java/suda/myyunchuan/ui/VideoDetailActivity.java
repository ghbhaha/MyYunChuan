package suda.myyunchuan.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.wefika.flowlayout.FlowLayout;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import suda.myyunchuan.R;
import suda.myyunchuan.base.BaseActivity;
import suda.myyunchuan.module.model.VideoDetailInfo;
import suda.myyunchuan.module.presenter.VideoDetailPresenter;
import suda.myyunchuan.module.view.VideoDetailView;
import suda.myyunchuan.util.ScreenUtil;

public class VideoDetailActivity extends BaseActivity<VideoDetailPresenter> implements VideoDetailView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        initWidget();
        initData();
    }

    public void initWidget() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("加载ing");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        jcVideoPlayerStandard = new JCVideoPlayerStandard(this);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.content);
        frameLayout.addView(jcVideoPlayerStandard);
        urlContent = (ViewGroup) findViewById(R.id.url_content);
    }

    @Override
    public void initData() {
        super.initData();
        mvpPerenter.getVideoDetailInfo(this, getIntent().getStringExtra("videoId"));
    }

    @Override
    protected VideoDetailPresenter creatPerenter() {
        return new VideoDetailPresenter(this);
    }

    @Override
    public void showVideoDetailInfo(final VideoDetailInfo videoDetailInfo) {
        jcVideoPlayerStandard.post(new Runnable() {
            @Override
            public void run() {
                if (videoDetailInfo != null) {
                    toolbar.setTitle(videoDetailInfo.getName());
                    List<String> urls = videoDetailInfo.getUrls();
                    int width = ScreenUtil.getScreenWidth(VideoDetailActivity.this) / 4;
                    if (urls != null && urls.size() > 0) {
                        int i = 1;
                        for (final String url : urls) {
                            Button button = new Button(VideoDetailActivity.this);
                            FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            layoutParams.width = width;
                            button.setText("第" + i + "集");
                            button.setLayoutParams(layoutParams);
                            i++;
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    jcVideoPlayerStandard.setUp(url, JCVideoPlayer.CURRENT_STATE_NORMAL, videoDetailInfo.getName());
                                    jcVideoPlayerStandard.startVideo();
                                }
                            });
                            urlContent.addView(button);
                        }
                        jcVideoPlayerStandard.setUp(urls.get(0), JCVideoPlayer.CURRENT_STATE_NORMAL, videoDetailInfo.getName());
                    }

                }

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

    private Toolbar toolbar;
    private ViewGroup urlContent;
    private JCVideoPlayerStandard jcVideoPlayerStandard;
}
