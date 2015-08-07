package com.itheima.smartbeijing;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.itheima.smartbeijing.fragment.ContentFragment;
import com.itheima.smartbeijing.fragment.LeftMenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * @����:com.itheima.wisdombeijing
 * @����:MainUI
 * @����:�»��
 * @ʱ��:2015-8-6 ����10:28:52
 * 
 * 
 * @����:������
 */
public class MainUI extends SlidingFragmentActivity
{
	private final static String	TAG_CONTENT			= "content";	// ��������ı��
	private final static String	TAG_LEFT_CONTENT	= "left_menu";	// �������ı��

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		initView();
	}

	/**
	 * ��ʼ������
	 */
	private void initView()
	{
		// ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// ������������
		setContentView(R.layout.main);

		// ����behind����(left,right)
		setBehindContentView(R.layout.main_left);

		// ���SlidingMenuʵ��
		SlidingMenu menu = getSlidingMenu();
		// ����ģʽ
		menu.setMode(SlidingMenu.LEFT);
		// ָ���ǲ˵��ı�Ե����Ļ��Ե�ľ���
		// menu.setBehindOffset(180);
		// �˵����
		menu.setBehindWidth(180);// 120
		// ����SlidingMenu����ģʽ:ȫ������TOUCHMODE_FULLSCREEN(TOUCHMODE_MARGIN,TOUCHMODE_NONE)
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		// ͨ��fragment�ķ�������ҳ��
		initFragment();
	}

	/**
	 * ��ʼ��֡����,����ҳ��
	 */
	private void initFragment()
	{
		// ����fragment������
		FragmentManager fm = getSupportFragmentManager();

		// 1.��������
		FragmentTransaction transaction = fm.beginTransaction();

		// �����ҳfragment
		transaction.replace(R.id.main_container, new ContentFragment(), TAG_CONTENT);

		// ������fragment
		transaction.replace(R.id.main_left_container, new LeftMenuFragment(), TAG_LEFT_CONTENT);

		// �ύ����
		transaction.commit();
	}
}
