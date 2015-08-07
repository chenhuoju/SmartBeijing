package com.itheima.smartbeijing.base.tab;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.itheima.smartbeijing.base.TabBasePager;

/**
 * @����:com.itheima.smartbeijing.base.tab
 * @����:TabHomePager
 * @����:�»��
 * @ʱ��:2015-8-7 ����10:13:38
 * 
 * 
 * @����:��ҳ������tab��Ӧ���ǻ۷�������
 */
public class TabSmartServerPager extends TabBasePager
{

	public TabSmartServerPager(Context context) {
		super(context);
	}

	@Override
	public void initData()
	{
		// 1.title�������ݵ�����
		mTvTitle.setText("����");
		mIconMenu.setVisibility(View.VISIBLE);

		// 2.�����������ݵ�����
		TextView tv = new TextView(mContext);
		tv.setText("�ǻ۷�����������");
		tv.setTextColor(Color.RED);
		tv.setTextSize(24);
		tv.setGravity(Gravity.CENTER);

		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		mContentContainer.addView(tv, params);
	}
}
