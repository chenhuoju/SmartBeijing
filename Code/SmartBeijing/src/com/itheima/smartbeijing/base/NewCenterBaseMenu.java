package com.itheima.smartbeijing.base;

import android.content.Context;
import android.view.View;

/**
 * @包名:com.itheima.smartbeijing.base
 * @类名:NewCenterBaseMenu
 * @作者:陈火炬
 * @时间:2015-8-7 下午3:58:28
 * 
 * 
 * @描述:新闻中心 内容区域的基类
 */
public abstract class NewCenterBaseMenu
{
	public Context	mContext;	// 上下文
	protected View	mRootView;	// 根视图

	public NewCenterBaseMenu(Context context) {
		this.mContext = context;

		mRootView = initView();
	}

	/**
	 * 初始化视图,让子类去实现自己长什么样子
	 * 
	 * @return
	 */
	protected abstract View initView();

	/**
	 * 数据加载的方法，子类如果要实现数据加载，就需要复习这个方法
	 * 
	 * @return
	 */
	public void initData()
	{
	}

	/**
	 * 获取根视图
	 * 
	 * @return
	 */
	public View getRootView()
	{
		return mRootView;
	}
}
