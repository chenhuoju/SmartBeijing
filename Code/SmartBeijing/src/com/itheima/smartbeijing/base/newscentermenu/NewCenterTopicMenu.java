package com.itheima.smartbeijing.base.newscentermenu;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.itheima.smartbeijing.base.NewCenterBaseMenu;

/**
 * 
 * @����:com.itheima.smartbeijing.base.newscentermenu
 * @����:NewCenterTopicMenu
 * @����:�»��
 * @ʱ��:2015-8-7 ����8:01:30
 * 
 * 
 * @����:��������-->ҳ����-->ר��˵��ж�Ӧ������ҳ��
 * 
 * @SVN�汾��:$Rev: 12 $
 * @������:$Author: chj $
 * @��������:TODO
 * 
 */
public class NewCenterTopicMenu extends NewCenterBaseMenu
{

	public NewCenterTopicMenu(Context context) {
		super(context);
	}

	@Override
	protected View initView()
	{
		TextView tv = new TextView(mContext);

		tv.setText("ר��ҳ�����������");
		tv.setTextSize(24);
		tv.setTextColor(Color.RED);
		tv.setGravity(Gravity.CENTER);

		return tv;
	}
}
