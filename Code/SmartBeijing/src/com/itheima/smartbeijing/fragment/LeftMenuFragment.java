package com.itheima.smartbeijing.fragment;

import android.view.View;
import android.widget.TextView;

import com.itheima.smartbeijing.base.BaseFragment;

/**
 * @����:com.itheima.wisdombeijing.fragment
 * @����:LeftMenuFragment
 * @����:�»��
 * @ʱ��:2015-8-6 ����7:56:08
 * 
 * 
 * @����:���˵�
 */
public class LeftMenuFragment extends BaseFragment
{

	@Override
	protected View initView()
	{
		TextView tv = new TextView(mActivity);

		tv.setText("�˵�����");

		return tv;
	}
}
