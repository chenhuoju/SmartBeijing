package com.itheima.smartbeijing.base;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.itheima.smartbeijing.MainUI;
import com.itheima.smartbeijing.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * @包名:com.itheima.smartbeijing.base
 * @类名:TabBasePager
 * @作者:陈火炬
 * @时间:2015-8-7 上午9:42:36
 * 
 * 
 * @描述:viewPager的每一个子页面的controller(控制器)的基类
 */
public abstract class TabBasePager implements OnClickListener
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

		mIconMenu.setOnClickListener(this);// 设置ImageButton监听事件

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

	/**
	 * 监听方法
	 */
	@Override
	public void onClick(View view)
	{
		if (view == mIconMenu)
		{
			toggleSlidingMenu();
		}
	}

	/**
	 * 切换侧滑菜单
	 */
	private void toggleSlidingMenu()
	{
		// 打开slidingMenu
		MainUI ui = (MainUI) mContext;
		SlidingMenu menu = ui.getSlidingMenu();
		// 如果slidingMenu是打开的，那么就关闭，否则相反
		menu.toggle();
	}
}
