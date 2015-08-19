package com.lokesh.weatherinfo;

import com.lokesh.weatherinfo.Adapter.DrawerAdapter;
import com.lokesh.weatherinfo.Adapter.ViewPagerAdapter;
import com.lokesh.weatherinfo.UI.SlidingTabLayout;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;

import android.support.v4.widget.DrawerLayout;

import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import com.lokesh.weatherinfo.Adapter.DrawerAdapter.OnItemClickListener;

public class MainActivity extends ActionBarActivity {


    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Current","Forcast"};
    int Numboftabs =2;

    private Toolbar toolbar;

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    DrawerLayout Drawer;

    ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String TITLES[] = {"Home","Settings"};
        int ICONS[] = {R.drawable.ic_home,R.drawable.ic_setting};
 
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs);

 
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
 
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);
 
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });
 
        tabs.setViewPager(pager);

        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);

        mRecyclerView.setHasFixedSize(true);

        mAdapter = new DrawerAdapter(TITLES,ICONS,this);



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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.weather, menu);
        return true;
    }


}
