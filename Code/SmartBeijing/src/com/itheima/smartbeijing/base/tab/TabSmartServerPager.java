package com.itheima.smartbeijing.base.tab;

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
 * @描述:主页界面中tab对应的智慧服务设置
 */
public class TabSmartServerPager extends TabBasePager
{

	public TabSmartServerPager(Context context) {
		super(context);
	}

	@Override
	public void initData()
	{
		// 1.title部分数据的设置
		mTvTitle.setText("生活");
		mIconMenu.setVisibility(View.VISIBLE);

		// 2.内容区域数据的设置
		TextView tv = new TextView(mContext);
		tv.setText("智慧服务内容区域");
		tv.setTextColor(Color.RED);
		tv.setTextSize(24);
		tv.setGravity(Gravity.CENTER);

		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		mContentContainer.addView(tv, params);
	}
}
