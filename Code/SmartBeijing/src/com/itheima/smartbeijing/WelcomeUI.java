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
 * @包名:com.itheima.wisdombeijing
 * @类名:WelcomeUI
 * @作者:陈火炬
 * @时间:2015-8-5 下午12:03:49
 * 
 * 
 * @描述:欢迎界面
 */
public class WelcomeUI extends Activity
{
	private final static long	DURATION		= 1500;		// 动画持续时间
	private View				welcome_container;				// 外侧容器
	private AnimationSet		set;							// 创建动画集合
	public final static String	KEY_IS_FIRST	= "is_first";	// 第一次登录的标记

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		initView();
		initAnimation();
		initAnimationListener();
	}

	/**
	 * 初始化界面
	 */
	private void initView()
	{
		setContentView(R.layout.welcome);

		welcome_container = findViewById(R.id.welcome_container);
	}

	/**
	 * 实现动画效果
	 * 
	 */
	private void initAnimation()
	{
		set = new AnimationSet(false);

		// 1.旋转
		RotateAnimation rotateAnimation = new RotateAnimation(0,// 开始角度
																360,// 结束角度
																Animation.RELATIVE_TO_SELF, // 相对自己
																0.5f,// x轴中心
																Animation.RELATIVE_TO_SELF,// 相对自己
																0.5f// y轴中心
		);
		rotateAnimation.setFillEnabled(true);// 是否保持动画状态
		rotateAnimation.setFillAfter(true);// 保存为旋转之后的状态
		rotateAnimation.setDuration(DURATION);// 设置动画旋转持续时间

		// 2.缩放
		ScaleAnimation scaleAnimation = new ScaleAnimation(0f, // x方向的缩放比例
															1f,
															0f,// y方向的缩放比例
															1f,
															Animation.RELATIVE_TO_SELF, // 相对自己
															0.5f, // x轴中心
															Animation.RELATIVE_TO_SELF,// 相对自己
															0.5f// x轴中心
		);
		scaleAnimation.setFillEnabled(true);// 是否保持动画状态
		scaleAnimation.setFillAfter(true);// 保存为旋转之后的状态
		scaleAnimation.setDuration(DURATION);// 设置动画旋转持续时间

		// 3.透明渐变
		AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);// 0-1f:透明到不透明
		alphaAnimation.setFillEnabled(true);// 是否保持动画状态
		alphaAnimation.setFillAfter(true);// 保存为旋转之后的状态
		alphaAnimation.setDuration(DURATION);// 设置动画旋转持续时间

		// 添加动画到动画集合中
		set.addAnimation(rotateAnimation);
		set.addAnimation(scaleAnimation);
		set.addAnimation(alphaAnimation);

		// 开启动画
		welcome_container.startAnimation(set);
	}

	/**
	 * 设置动画的侦听
	 */
	private void initAnimationListener()
	{
		set.setAnimationListener(new WelcomeAnimationListener());
	}

	/**
	 * 自定义动画侦听类
	 */
	class WelcomeAnimationListener implements AnimationListener
	{

		/**
		 * 当动画结束时调用
		 */
		@Override
		public void onAnimationEnd(Animation animation)
		{
			// 实现页面跳转

			boolean isFirst = CacheUtils.getBoolean(WelcomeUI.this, KEY_IS_FIRST, true);// 默认第一次打开应用

			if (isFirst)
			{
				// 当应用程序第一次进入时，需要跳转到-->引导页面
				Intent intent = new Intent(WelcomeUI.this, GuideUI.class);
				startActivity(intent);
			}
			else
			{
				// 否则需要跳转到-->主页面:TODO
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
