package suda.myyunchuan.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by guhaibo on 2017/3/19.
 */

public abstract class BaseActivity<P extends BasePersenter> extends AppCompatActivity implements BaseView{

    protected P mvpPerenter;
    public Activity mActivity;
    public ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mvpPerenter = creatPerenter();
        super.onCreate(savedInstanceState);
        mActivity = this;
    }

    protected abstract P creatPerenter();

    public void initWidget(){

    }

    public void initData(){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mvpPerenter != null) {
            mvpPerenter.detachView();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showLoading() {
        showProgressDialog();
    }

    public void hideLoading() {
        dismissProgressDialog();
    }

    public void toastShow(final int resId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mActivity, resId, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void toastShow(final String resId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mActivity, resId, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public ProgressDialog showProgressDialog() {
        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setMessage("加载中");
        progressDialog.show();
        return progressDialog;
    }

    public ProgressDialog showProgressDialog(CharSequence message) {
        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setMessage(message);
        progressDialog.show();
        return progressDialog;
    }

    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            // progressDialog.hide();会导致android.view.WindowLeaked
            progressDialog.dismiss();
        }
    }
}
