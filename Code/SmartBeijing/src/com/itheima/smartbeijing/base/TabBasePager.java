package com.itheima.smartbeijing.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.itheima.smartbeijing.R;

/**
 * @包名:com.itheima.smartbeijing.base
 * @类名:TabBasePager
 * @作者:陈火炬
 * @时间:2015-8-7 上午9:42:36
 * 
 * 
 * @描述:viewPager的每一个子页面的controller(控制器)的基类
 */
public abstract class TabBasePager
{
	protected Context		mContext;			// 上下文
	protected View			mRootView;			// 根视图
	protected TextView		mTvTitle;			// title
	protected ImageButton	mIconMenu;			// menu
	protected FrameLayout	mContentContainer;	// 内容容器

	public TabBasePager(Context context) {
		this.mContext = context;
		mRootView = initView();
	}

	/**
	 * 初始化视图
	 * 
	 * @return
	 */
	protected View initView()
	{
		View view = View.inflate(mContext, R.layout.tab_base_pager, null);

		// 实现查找view
		mTvTitle = (TextView) view.findViewById(R.id.title_bar_tv_title);
		mIconMenu = (ImageButton) view.findViewById(R.id.title_bar_icon_menu);
		mContentContainer = (FrameLayout) view.findViewById(R.id.tab_base_content_container);

		return view;
	}

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
