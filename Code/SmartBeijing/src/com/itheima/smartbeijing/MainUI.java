package com.itheima.smartbeijing;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.itheima.smartbeijing.fragment.ContentFragment;
import com.itheima.smartbeijing.fragment.LeftMenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * @包名:com.itheima.wisdombeijing
 * @类名:MainUI
 * @作者:陈火炬
 * @时间:2015-8-6 上午10:28:52
 * 
 * 
 * @描述:主界面
 */
public class MainUI extends SlidingFragmentActivity
{
	private final static String	TAG_CONTENT			= "content";	// 内容区域的标记
	private final static String	TAG_LEFT_CONTENT	= "left_menu";	// 左侧区域的标记

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		initView();
	}

	/**
	 * 初始化界面
	 */
	private void initView()
	{
		// 去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 设置内容区域
		setContentView(R.layout.main);

		// 设置behind部分(left,right)
		setBehindContentView(R.layout.main_left);

		// 获得SlidingMenu实例
		SlidingMenu menu = getSlidingMenu();
		// 设置模式
		menu.setMode(SlidingMenu.LEFT);
		// 指的是菜单的边缘到屏幕边缘的距离
		// menu.setBehindOffset(180);
		// 菜单宽度
		menu.setBehindWidth(180);// 120
		// 设置SlidingMenu滑动模式:全屏拉出TOUCHMODE_FULLSCREEN(TOUCHMODE_MARGIN,TOUCHMODE_NONE)
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		// 通过fragment的方法加载页面
		initFragment();
	}

	/**
	 * 初始化帧布局,加载页面
	 */
	private void initFragment()
	{
		// 创建fragment管理者
		FragmentManager fm = getSupportFragmentManager();

		// 1.开启事物
		FragmentTransaction transaction = fm.beginTransaction();

		// 添加主页fragment
		transaction.replace(R.id.main_container, new ContentFragment(), TAG_CONTENT);

		// 添加左侧fragment
		transaction.replace(R.id.main_left_container, new LeftMenuFragment(), TAG_LEFT_CONTENT);

		// 提交事务
		transaction.commit();
	}
}
