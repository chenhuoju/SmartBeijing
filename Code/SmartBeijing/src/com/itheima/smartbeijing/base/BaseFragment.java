package com.itheima.smartbeijing.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @����:com.itheima.wisdombeijing.base
 * @����:BaseFragment
 * @����:�»��
 * @ʱ��:2015-8-6 ����8:02:51
 * 
 * 
 * @����:fragment�Ļ���
 */
public abstract class BaseFragment extends Fragment
{
	protected Activity	mActivity;	// fragment������

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		this.mActivity = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return initView();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		// ���ݼ��صĲ���
		initData();
	}

	/**
	 * ���ݼ��صķ��������������Ҫ�������ݣ��͸�д�������
	 */
	protected void initData()
	{

	}

	/**
	 * ��ʼ����ͼ,����һ����ͼ����
	 * 
	 * @return
	 */
	protected abstract View initView();
}
