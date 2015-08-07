package com.itheima.smartbeijing.fragment;

import android.view.View;
import android.widget.TextView;

import com.itheima.smartbeijing.base.BaseFragment;

/**
 * @包名:com.itheima.wisdombeijing.fragment
 * @类名:LeftMenuFragment
 * @作者:陈火炬
 * @时间:2015-8-6 下午7:56:08
 * 
 * 
 * @描述:左侧菜单
 */
public class LeftMenuFragment extends BaseFragment
{

	@Override
	protected View initView()
	{
		TextView tv = new TextView(mActivity);

		tv.setText("菜单内容");

		return tv;
	}
}
