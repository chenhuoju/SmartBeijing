package com.itheima.smartbeijing.base.tab;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.itheima.smartbeijing.base.TabBasePager;

/**
 * @包名:com.itheima.smartbeijing.base.tab
 * @类名:TabHomePager
 * @作者:陈火炬
 * @时间:2015-8-7 上午10:13:38
 * 
 * 
 * @描述:主页界面中tab对应的新闻中心设置
 */
public class TabNewsCenterPager extends TabBasePager
{

	private List<TextView>	mPagerList;

	public TabNewsCenterPager(Context context) {
		super(context);
	}

	@Override
	public void initData()
	{
		// 1.title部分数据的设置
		mTvTitle.setText("新闻");
		mIconMenu.setVisibility(View.VISIBLE);

		// 2.内容区域数据的设置 TODO:
		// TextView tv = new TextView(mContext);
		// tv.setText("新闻内容区域");
		// tv.setTextColor(Color.RED);
		// tv.setTextSize(24);
		// tv.setGravity(Gravity.CENTER);
		//
		// LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
		// LayoutParams.MATCH_PARENT);
		// mContentContainer.addView(tv, params);

		// 通过网络去获取数据，将数据加载到页面上来
		// 模拟死数据
		mPagerList = new ArrayList<TextView>();
		for (int i = 0; i < 4; i++)
		{
			TextView tv = new TextView(mContext);
			tv.setText("菜单" + (i + 1) + "的页面");

			mPagerList.add(tv);
		}

		// 设置内容区域视图的展示默认值
		switchPager(0);

	}

	/**
	 * 设置内容区域视图的展示
	 * 
	 * @param i
	 */
	public void switchPager(int i)
	{
		// 清空内容的数据
		mContentContainer.removeAllViews();

		// TODO:伪代码，用来展示用的
		TextView tv = mPagerList.get(i);
		tv.setTextColor(Color.RED);
		tv.setTextSize(24);
		tv.setGravity(Gravity.CENTER);

		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
												LayoutParams.MATCH_PARENT);
		mContentContainer.addView(tv, params);
	}
}
