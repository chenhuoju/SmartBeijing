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
 * @����:com.itheima.smartbeijing.base.tab
 * @����:TabHomePager
 * @����:�»��
 * @ʱ��:2015-8-7 ����10:13:38
 * 
 * 
 * @����:��ҳ������tab��Ӧ��������������
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
		// 1.title�������ݵ�����
		mTvTitle.setText("����");
		mIconMenu.setVisibility(View.VISIBLE);

		// 2.�����������ݵ����� TODO:
		// TextView tv = new TextView(mContext);
		// tv.setText("������������");
		// tv.setTextColor(Color.RED);
		// tv.setTextSize(24);
		// tv.setGravity(Gravity.CENTER);
		//
		// LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
		// LayoutParams.MATCH_PARENT);
		// mContentContainer.addView(tv, params);

		// ͨ������ȥ��ȡ���ݣ������ݼ��ص�ҳ������
		// ģ��������
		mPagerList = new ArrayList<TextView>();
		for (int i = 0; i < 4; i++)
		{
			TextView tv = new TextView(mContext);
			tv.setText("�˵�" + (i + 1) + "��ҳ��");

			mPagerList.add(tv);
		}

		// ��������������ͼ��չʾĬ��ֵ
		switchPager(0);

	}

	/**
	 * ��������������ͼ��չʾ
	 * 
	 * @param i
	 */
	public void switchPager(int i)
	{
		// ������ݵ�����
		mContentContainer.removeAllViews();

		// TODO:α���룬����չʾ�õ�
		TextView tv = mPagerList.get(i);
		tv.setTextColor(Color.RED);
		tv.setTextSize(24);
		tv.setGravity(Gravity.CENTER);

		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
												LayoutParams.MATCH_PARENT);
		mContentContainer.addView(tv, params);
	}
}
