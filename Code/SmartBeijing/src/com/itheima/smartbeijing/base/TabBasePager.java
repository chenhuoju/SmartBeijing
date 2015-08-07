package com.itheima.smartbeijing.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.itheima.smartbeijing.R;

/**
 * @����:com.itheima.smartbeijing.base
 * @����:TabBasePager
 * @����:�»��
 * @ʱ��:2015-8-7 ����9:42:36
 * 
 * 
 * @����:viewPager��ÿһ����ҳ���controller(������)�Ļ���
 */
public abstract class TabBasePager
{
	protected Context		mContext;			// ������
	protected View			mRootView;			// ����ͼ
	protected TextView		mTvTitle;			// title
	protected ImageButton	mIconMenu;			// menu
	protected FrameLayout	mContentContainer;	// ��������

	public TabBasePager(Context context) {
		this.mContext = context;
		mRootView = initView();
	}

	/**
	 * ��ʼ����ͼ
	 * 
	 * @return
	 */
	protected View initView()
	{
		View view = View.inflate(mContext, R.layout.tab_base_pager, null);

		// ʵ�ֲ���view
		mTvTitle = (TextView) view.findViewById(R.id.title_bar_tv_title);
		mIconMenu = (ImageButton) view.findViewById(R.id.title_bar_icon_menu);
		mContentContainer = (FrameLayout) view.findViewById(R.id.tab_base_content_container);

		return view;
	}

	/**
	 * ���ݼ��صķ������������Ҫʵ�����ݼ��أ�����Ҫ��ϰ�������
	 * 
	 * @return
	 */
	public void initData()
	{
	}

	/**
	 * ��ȡ����ͼ
	 * 
	 * @return
	 */
	public View getRootView()
	{
		return mRootView;
	}
}
