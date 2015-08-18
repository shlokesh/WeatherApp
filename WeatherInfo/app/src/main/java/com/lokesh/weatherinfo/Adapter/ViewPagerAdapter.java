package com.lokesh.weatherinfo.Adapter;

import com.lokesh.weatherinfo.UI.CurrentWeatherFragment;
import com.lokesh.weatherinfo.UI.ForcastWeatherFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
	CharSequence Titles[]; 
    int NumbOfTabs;
    
    public ViewPagerAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);
 
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
 
    }
    
    @Override
    public Fragment getItem(int position) {
 
        if(position == 0)
        {
            CurrentWeatherFragment currentWeatherFragment = new CurrentWeatherFragment();
            return currentWeatherFragment;
        }
        else 
        {
            ForcastWeatherFragment forcastWeatherFragment = new ForcastWeatherFragment();
            return forcastWeatherFragment;
        }
 
 
    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return NumbOfTabs;
	}
	
	@Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }
}
