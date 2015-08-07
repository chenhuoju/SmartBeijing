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
 * @����:��ҳ������tab��Ӧ����ҳ����
 */
public class TabHomePager extends TabBasePager
{

	public TabHomePager(Context context) {
		super(context);
	}

	@Override
	public void initData()
	{
		// 1.title�������ݵ�����
		mTvTitle.setText("�ǻ۱���");
		mIconMenu.setVisibility(View.GONE);

		// 2.�����������ݵ�����
		TextView tv = new TextView(mContext);
		tv.setText("��ҳ��������");
		tv.setTextColor(Color.RED);
		tv.setTextSize(24);
		tv.setGravity(Gravity.CENTER);

		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		mContentContainer.addView(tv, params);
	}
}
