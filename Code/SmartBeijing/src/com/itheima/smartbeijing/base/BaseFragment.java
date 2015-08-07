package com.itheima.smartbeijing.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @包名:com.itheima.wisdombeijing.base
 * @类名:BaseFragment
 * @作者:陈火炬
 * @时间:2015-8-6 下午8:02:51
 * 
 * 
 * @描述:fragment的基类
 */
public abstract class BaseFragment extends Fragment
{
	protected Activity	mActivity;	// fragment的宿主

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		this.mActivity = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return initView();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		// 数据加载的操作
		initData();
	}

	/**
	 * 数据加载的方法，子类如果需要加载数据，就复写这个方法
	 */
	protected void initData()
	{

	}

	/**
	 * 初始化视图,返回一个视图对象
	 * 
	 * @return
	 */
	protected abstract View initView();
}
