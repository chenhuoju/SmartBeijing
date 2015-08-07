package com.itheima.smartbeijing.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @包名:com.itheima.smartbeijing.widget
 * @类名:NoScrollViewPager
 * @作者:陈火炬
 * @时间:2015-8-7 上午11:04:55
 * 
 * 
 * @描述:不可以触摸滑动的ViewPager,重写ViewPager的监听事件
 */
public class NoScrollViewPager extends LazyViewPager
{

	public NoScrollViewPager(Context context) {
		super(context);

	}

	public NoScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev)
	{
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev)
	{
		return false;
	}
}
