package com.itheima.smartbeijing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;

import com.itheima.smartbeijing.utils.CacheUtils;

/**
 * 
 * @����:com.itheima.wisdombeijing
 * @����:WelcomeUI
 * @����:�»��
 * @ʱ��:2015-8-5 ����12:03:49
 * 
 * 
 * @����:��ӭ����
 */
public class WelcomeUI extends Activity
{
	private final static long	DURATION		= 1500;		// ��������ʱ��
	private View				welcome_container;				// �������
	private AnimationSet		set;							// ������������
	public final static String	KEY_IS_FIRST	= "is_first";	// ��һ�ε�¼�ı��

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		initView();
		initAnimation();
		initAnimationListener();
	}

	/**
	 * ��ʼ������
	 */
	private void initView()
	{
		setContentView(R.layout.welcome);

		welcome_container = findViewById(R.id.welcome_container);
	}

	/**
	 * ʵ�ֶ���Ч��
	 * 
	 */
	private void initAnimation()
	{
		set = new AnimationSet(false);

		// 1.��ת
		RotateAnimation rotateAnimation = new RotateAnimation(0,// ��ʼ�Ƕ�
																360,// �����Ƕ�
																Animation.RELATIVE_TO_SELF, // ����Լ�
																0.5f,// x������
																Animation.RELATIVE_TO_SELF,// ����Լ�
																0.5f// y������
		);
		rotateAnimation.setFillEnabled(true);// �Ƿ񱣳ֶ���״̬
		rotateAnimation.setFillAfter(true);// ����Ϊ��ת֮���״̬
		rotateAnimation.setDuration(DURATION);// ���ö�����ת����ʱ��

		// 2.����
		ScaleAnimation scaleAnimation = new ScaleAnimation(0f, // x��������ű���
															1f,
															0f,// y��������ű���
															1f,
															Animation.RELATIVE_TO_SELF, // ����Լ�
															0.5f, // x������
															Animation.RELATIVE_TO_SELF,// ����Լ�
															0.5f// x������
		);
		scaleAnimation.setFillEnabled(true);// �Ƿ񱣳ֶ���״̬
		scaleAnimation.setFillAfter(true);// ����Ϊ��ת֮���״̬
		scaleAnimation.setDuration(DURATION);// ���ö�����ת����ʱ��

		// 3.͸������
		AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);// 0-1f:͸������͸��
		alphaAnimation.setFillEnabled(true);// �Ƿ񱣳ֶ���״̬
		alphaAnimation.setFillAfter(true);// ����Ϊ��ת֮���״̬
		alphaAnimation.setDuration(DURATION);// ���ö�����ת����ʱ��

		// ��Ӷ���������������
		set.addAnimation(rotateAnimation);
		set.addAnimation(scaleAnimation);
		set.addAnimation(alphaAnimation);

		// ��������
		welcome_container.startAnimation(set);
	}

	/**
	 * ���ö���������
	 */
	private void initAnimationListener()
	{
		set.setAnimationListener(new WelcomeAnimationListener());
	}

	/**
	 * �Զ��嶯��������
	 */
	class WelcomeAnimationListener implements AnimationListener
	{

		/**
		 * ����������ʱ����
		 */
		@Override
		public void onAnimationEnd(Animation animation)
		{
			// ʵ��ҳ����ת

			boolean isFirst = CacheUtils.getBoolean(WelcomeUI.this, KEY_IS_FIRST, true);// Ĭ�ϵ�һ�δ�Ӧ��

			if (isFirst)
			{
				// ��Ӧ�ó����һ�ν���ʱ����Ҫ��ת��-->����ҳ��
				Intent intent = new Intent(WelcomeUI.this, GuideUI.class);
				startActivity(intent);
			}
			else
			{
				// ������Ҫ��ת��-->��ҳ��:TODO
				Intent intent = new Intent(WelcomeUI.this, MainUI.class);
				startActivity(intent);
			}

			finish();
		}

		@Override
		public void onAnimationStart(Animation animation)
		{

		}

		@Override
		public void onAnimationRepeat(Animation animation)
		{

		}
	}

}
