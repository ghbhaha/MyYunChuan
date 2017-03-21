package suda.myyunchuan.base;

/**
 * Created by guhaibo on 2017/3/19.
 */

public interface BaseView {
    void showLoading();

    void hideLoading();

    void toastShow(int resId);

    void toastShow(String resId);
}

