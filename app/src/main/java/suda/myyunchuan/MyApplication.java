package suda.myyunchuan;

import android.app.Application;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by guhaibo on 2017/3/19.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initOkHttpClient();
    }

    private void initOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS);
        mOkHttpClient = builder.build();
    }

    public static OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    private static OkHttpClient mOkHttpClient;
}
