package com.itheima.smartbeijing.base.newscentermenu;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.itheima.smartbeijing.base.NewCenterBaseMenu;

/**
 * 
 * @包名:com.itheima.smartbeijing.base.newscentermenu
 * @类名:NewCenterTopicMenu
 * @作者:陈火炬
 * @时间:2015-8-7 下午8:01:30
 * 
 * 
 * @描述:新闻中心-->页面中-->专题菜单中对应的内容页面
 * 
 * @SVN版本号:$Rev: 12 $
 * @更新人:$Author: chj $
 * @更新描述:TODO
 * 
 */
public class NewCenterTopicMenu extends NewCenterBaseMenu
{

	public NewCenterTopicMenu(Context context) {
		super(context);
	}

	@Override
	protected View initView()
	{
		TextView tv = new TextView(mContext);

		tv.setText("专题页面的内容区域");
		tv.setTextSize(24);
		tv.setTextColor(Color.RED);
		tv.setGravity(Gravity.CENTER);

		return tv;
	}
}
