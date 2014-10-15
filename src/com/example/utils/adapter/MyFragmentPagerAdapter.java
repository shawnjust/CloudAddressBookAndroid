package com.example.utils.adapter;

import java.util.Vector;

import com.example.utils.abstractActivities.UpdatableFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
/**
 * 一个简单的ViewPager适配器
 * @author RenJiayue
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter{
	private Vector<UpdatableFragment> fragments;
	public MyFragmentPagerAdapter(FragmentManager fm, Vector<UpdatableFragment> fragmentList) {
		super(fm);
		// TODO Auto-generated constructor stub
		fragments = fragmentList;
	}

	@Override
	public Fragment getItem(int pos) {
		// TODO Auto-generated method stub
		return fragments.get(pos);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fragments.size();
	}

}
