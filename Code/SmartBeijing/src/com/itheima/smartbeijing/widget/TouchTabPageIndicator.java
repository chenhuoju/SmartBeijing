package com.itheima.smartbeijing.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.viewpagerindicator.TabPageIndicator;

/**
 * @包名:com.itheima.smartbeijing.widget
 * @类名:TouchTabPageIndicator
 * @作者:陈火炬
 * @时间:2015-8-9 上午10:13:18
 * 
 * 
 * @描述:不让父窗体抢占touch事件
 * 
 * @SVN版本号:$Rev: 22 $
 * @更新人:$Author: chj $
 * @更新描述:TODO
 * 
 */
public class TouchTabPageIndicator extends TabPageIndicator
{

	public TouchTabPageIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TouchTabPageIndicator(Context context) {
		super(context);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev)
	{
		//true:不希望父容器去拦截touch事件
		//false:希望父容器去拦截touch事件
		getParent().requestDisallowInterceptTouchEvent(true);
		
		return super.dispatchTouchEvent(ev);
	}

}
