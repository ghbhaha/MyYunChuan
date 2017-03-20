package suda.myyunchuan.base;

/**
 * Created by guhaibo on 2017/3/19.
 */

public class BasePersenter<V> {
    public V mvpView;

    public void attachView(V mvpView) {
        this.mvpView = mvpView;
    }

    public void detachView() {
        this.mvpView = null;
    }
}
