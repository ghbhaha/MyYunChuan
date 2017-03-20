package suda.myyunchuan.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import suda.myyunchuan.R;
import suda.myyunchuan.module.model.VideoInfo;
import suda.myyunchuan.util.Constant;

/**
 * Created by guhaibo on 2017/3/19.
 */

public class HomeAdapter extends RecyclerView.Adapter {

    private List<VideoInfo> mVideoInfos;
    private Context mContext;


    public HomeAdapter(List<VideoInfo> videoInfos, Context context){
        mVideoInfos = videoInfos;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoViewHolder(View.inflate(mContext,R.layout.item_vedio,null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VideoViewHolder videoViewHolder = (VideoViewHolder) holder;
        final VideoInfo videoInfo = mVideoInfos.get(position);
        videoViewHolder.mTvTitle.setText(videoInfo.getName());
        Glide.with(mContext).load(Constant.getImgUrl(mContext, videoInfo.getId())).into(videoViewHolder.mImgInfo);
        videoViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,VideoDetailActivity.class);
                intent.putExtra("videoId",videoInfo.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mVideoInfos.size();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {

        ImageView mImgInfo;
        TextView mTvTitle;

        public VideoViewHolder(View itemView) {
            super(itemView);
            mImgInfo = (ImageView) itemView.findViewById(R.id.img_info);
            mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }

}
