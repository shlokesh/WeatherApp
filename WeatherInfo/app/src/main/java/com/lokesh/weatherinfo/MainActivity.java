package com.lokesh.weatherinfo;

import com.lokesh.weatherinfo.Adapter.DrawerAdapter;
import com.lokesh.weatherinfo.Adapter.ViewPagerAdapter;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lokesh.weatherinfo.model.WeatherList;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity implements DrawerAdapter.OnItemClickListener {


    private ViewPager pager;
    private ViewPagerAdapter adapter;
    private ArrayList<Fragment> fragmentList;

    private Toolbar toolbar;
    private TextView message;
    private ImageView nolocation;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DrawerLayout Drawer;
    private String location;

    private ActionBarDrawerToggle mDrawerToggle;
    private SharedPreferences preference;
    private CirclePageIndicator titleIndicator;
    private WeatherList weatherList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        weatherList = WeatherList.getInstance();

        String TITLES[] = {"Home","Settings"};
        int Numboftabs = 2;
        int ICONS[] = {R.drawable.ic_home,R.drawable.ic_setting};
 
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        message = (TextView) findViewById(R.id.message);
        nolocation = (ImageView) findViewById(R.id.location);
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),TITLES,Numboftabs);

        pager = (ViewPager) findViewById(R.id.pager);
        titleIndicator = (CirclePageIndicator)findViewById(R.id.titles);
        pager.setAdapter(null);

        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new DrawerAdapter(TITLES,ICONS,this,this);
        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);
        mDrawerToggle = new ActionBarDrawerToggle(this,Drawer,toolbar,R.string.openDrawer,R.string.closeDrawer){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }

        };
        Drawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

    }
 
    @Override
    public void onResume(){
        super.onResume();
        location = preference.getString("cityName", null);
        if(location!=null){
            message.setVisibility(View.GONE);
            nolocation.setVisibility(View.GONE);
            pager.setAdapter(adapter);
            titleIndicator.setFillColor(Color.BLACK);
            titleIndicator.setPageColor(Color.RED);
            titleIndicator.setViewPager(pager);

        }
    }

    @Override
    public void onItemClick(View view,int position){
        Drawer.closeDrawers();
}

}
