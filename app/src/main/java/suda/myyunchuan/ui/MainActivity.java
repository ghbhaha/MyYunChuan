package suda.myyunchuan.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import suda.myyunchuan.R;
import suda.myyunchuan.base.BaseActivity;
import suda.myyunchuan.module.model.VideoInfo;
import suda.myyunchuan.module.presenter.MainPresenter;
import suda.myyunchuan.module.view.MainView;

public class MainActivity extends BaseActivity<MainPresenter>
        implements NavigationView.OnNavigationItemSelectedListener, MainView {

    private RecyclerView mRcMain;
    private List<VideoInfo> mVideoInfos = new ArrayList<>();
    private HomeAdapter homeAdapter;
    private int pageIndex = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidget();
        initData();
    }

    @Override
    public void initWidget() {
        super.initWidget();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRcMain = (RecyclerView) findViewById(R.id.rv_main);
        initRcMain();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mvpPerenter.getAllList(mActivity);
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
        mvpPerenter.getAllList(this);
    }

    private void initRcMain(){
        mRcMain.setLayoutManager(new GridLayoutManager(this,2));
        homeAdapter = new HomeAdapter(mVideoInfos,this);
        mRcMain.setAdapter(homeAdapter);
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
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void showList(final List<VideoInfo> videoInfos) {

        mRcMain.post(new Runnable() {
            @Override
            public void run() {
                mVideoInfos.clear();
                mVideoInfos.addAll(videoInfos);
                homeAdapter.notifyDataSetChanged();
            }
        });
    }
}
