package com.itheima.smartbeijing.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @����:com.itheima.smartbeijing.widget
 * @����:NoScrollViewPager
 * @����:�»��
 * @ʱ��:2015-8-7 ����11:04:55
 * 
 * 
 * @����:�����Դ���������ViewPager,��дViewPager�ļ����¼�
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
