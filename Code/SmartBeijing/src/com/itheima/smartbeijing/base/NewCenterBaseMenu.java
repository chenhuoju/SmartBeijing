package com.itheima.smartbeijing.base;

import android.content.Context;
import android.view.View;

/**
 * @����:com.itheima.smartbeijing.base
 * @����:NewCenterBaseMenu
 * @����:�»��
 * @ʱ��:2015-8-7 ����3:58:28
 * 
 * 
 * @����:�������� ��������Ļ���
 */
public abstract class NewCenterBaseMenu
{
	public Context	mContext;	// ������
	protected View	mRootView;	// ����ͼ

	public NewCenterBaseMenu(Context context) {
		this.mContext = context;

		mRootView = initView();
	}

	/**
	 * ��ʼ����ͼ,������ȥʵ���Լ���ʲô����
	 * 
	 * @return
	 */
	protected abstract View initView();

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
