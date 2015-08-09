package com.itheima.smartbeijing.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.viewpagerindicator.TabPageIndicator;

/**
 * @����:com.itheima.smartbeijing.widget
 * @����:TouchTabPageIndicator
 * @����:�»��
 * @ʱ��:2015-8-9 ����10:13:18
 * 
 * 
 * @����:���ø�������ռtouch�¼�
 * 
 * @SVN�汾��:$Rev: 22 $
 * @������:$Author: chj $
 * @��������:TODO
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
		//true:��ϣ��������ȥ����touch�¼�
		//false:ϣ��������ȥ����touch�¼�
		getParent().requestDisallowInterceptTouchEvent(true);
		
		return super.dispatchTouchEvent(ev);
	}

}
