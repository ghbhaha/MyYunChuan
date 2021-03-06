package suda.myyunchuan.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import suda.myyunchuan.R;
import suda.myyunchuan.base.BaseActivity;
import suda.myyunchuan.module.model.VideoInfo;
import suda.myyunchuan.module.presenter.MainPresenter;
import suda.myyunchuan.module.view.MainView;
import suda.myyunchuan.util.Constant;
import suda.myyunchuan.util.EndlessRecyclerOnScrollListener;

public class MainActivity extends BaseActivity<MainPresenter>
        implements NavigationView.OnNavigationItemSelectedListener, MainView {

    private RecyclerView mRcMain;
    private List<VideoInfo> mVideoInfos = new ArrayList<>();
    private HomeAdapter homeAdapter;
    private Constant.VideoType videoType = Constant.VideoType.DAINSHIJU;
    private SwipeRefreshLayout swipeRefreshLayout;
    private final static int CODE_FOR_WRITE_PERMISSION = 111;

    GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
    EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(gridLayoutManager) {
        @Override
        public void onLoadMore(int currentPage) {
            mvpPerenter.getAllList(MainActivity.this, videoType);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        initWidget();
        initData();
    }

    @Override
    public void initWidget() {
        super.initWidget();
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRcMain = (RecyclerView) findViewById(R.id.rv_main);
        initRcMain();
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mvpPerenter.initAllList(MainActivity.this, videoType);
            }
        });
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void initData() {
        super.initData();
        mvpPerenter.initAllList(this, videoType);
    }

    private void initRcMain() {
        mRcMain.setLayoutManager(gridLayoutManager);
        homeAdapter = new HomeAdapter(mVideoInfos, this);
        mRcMain.setAdapter(homeAdapter);
        mRcMain.addOnScrollListener(endlessRecyclerOnScrollListener);
    }

    @Override
    protected MainPresenter creatPerenter() {
        return new MainPresenter(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            videoType = Constant.VideoType.DAINSHIJU;
            mvpPerenter.initAllList(this, videoType);
        } else if (id == R.id.nav_gallery) {
            videoType = Constant.VideoType.DIANYING;
            mvpPerenter.initAllList(this, videoType);
        } else if (id == R.id.nav_slideshow) {
            videoType = Constant.VideoType.ZONGYI;
            mvpPerenter.initAllList(this, videoType);
        } else if (id == R.id.nav_manage) {
            videoType = Constant.VideoType.JILUPIAN;
            mvpPerenter.initAllList(this, videoType);
        } else if (id == R.id.nav_comic) {
            videoType = Constant.VideoType.DONGMAN;
            mvpPerenter.initAllList(this, videoType);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void showList(final List<VideoInfo> videoInfos, final boolean isRefresh) {
        mRcMain.post(new Runnable() {
            @Override
            public void run() {
                if (isRefresh) {
                    endlessRecyclerOnScrollListener.init();
                    mVideoInfos.clear();
                }
                mVideoInfos.addAll(videoInfos);
                homeAdapter.notifyDataSetChanged();
                if (isRefresh)
                    mRcMain.smoothScrollToPosition(0);

                swipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }

    private void getPermission(final String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, permissions, CODE_FOR_WRITE_PERMISSION);
                break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == CODE_FOR_WRITE_PERMISSION) {
            if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                //用户不同意，自行处理即可
                finish();
            }
        }
    }

}
