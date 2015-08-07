package com.itheima.smartbeijing;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.itheima.smartbeijing.utils.CacheUtils;

/**
 * @包名:com.itheima.wisdombeijing
 * @类名:GuideUI
 * @作者:陈火炬
 * @时间:2015-8-5 下午6:01:55
 * 
 * 
 * @描述:用户引导页面
 */
public class GuideUI extends Activity implements OnPageChangeListener, OnClickListener
{
	private ViewPager			mPager;			// viewPager对象
	private Button				mButStart;			// 开始体验按钮
	private LinearLayout		mPointContainer;	// 装静态点的容器
	private View				mSelectedPoint;	// 选中的点
	private int					mSpace;			// 点与点之间的距离

	private GuidePagerAdapter	adapter;			// 自定义适配器对象

	private List<ImageView>		mImgList;			// 存储viewPager中的imageView集合

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		initView();

	}

	/**
	 * 初始化视图
	 */
	private void initView()
	{
		// 去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 设置布局
		setContentView(R.layout.guide);

		mPager = (ViewPager) findViewById(R.id.guide_pager);
		mButStart = (Button) findViewById(R.id.btn_start);
		mPointContainer = (LinearLayout) findViewById(R.id.guide_point_container);
		mSelectedPoint = findViewById(R.id.guide_point_selected);

		initData();
		initListenre();
	}

	/**
	 * 初始化数据,进行数据加载
	 */
	private void initData()
	{
		int[] imgRes = new int[] {
				R.drawable.guide_1,
				R.drawable.guide_2,
				R.drawable.guide_3
		};

		// 完善list
		mImgList = new ArrayList<ImageView>();
		for (int i = 0; i < imgRes.length; i++)
		{
			// 新建imageView
			ImageView img = new ImageView(this);
			img.setImageResource(imgRes[i]);
			img.setScaleType(ScaleType.FIT_XY);

			// 给list集合添加img
			mImgList.add(img);

			// 动态添加点
			View point = new View(this);
			point.setBackgroundResource(R.drawable.guide_point_normal);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);// 10px的点
			if (i != 0)
			{
				params.leftMargin = 10;// 点与点之间的间距
			}
			mPointContainer.addView(point, params);
		}

		// 给viewPager数据--->adapter--->list
		adapter = new GuidePagerAdapter();
		mPager.setAdapter(adapter);
	}

	/**
	 * 初始化侦听事件
	 */
	private void initListenre()
	{
		// 对viewPager进行侦听
		mPager.setOnPageChangeListener(this);

		// 对button进行侦听,实现按钮点击跳转事件
		mButStart.setOnClickListener(this);

		// 对viewTreeObserver视图的层次结构进行侦听
		// 计算点与点之间的距离
		mSelectedPoint.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout()
			{
				// 当UI的树布局改变时调用
				if (mImgList == null) {
				return;
				}

				mSelectedPoint.getViewTreeObserver().removeOnGlobalLayoutListener(this);

				mSpace = mPointContainer.getChildAt(1).getLeft() - mPointContainer.getChildAt(0).getLeft();
			}
		});
	}

	/**
	 * 自定义适配器类
	 */
	class GuidePagerAdapter extends PagerAdapter
	{

		/**
		 * 返回视图页面的数量
		 */
		@Override
		public int getCount()
		{
			if (mImgList != null) { return mImgList.size(); }
			return 0;
		}

		@Override
		public boolean isViewFromObject(View view, Object object)
		{
			return view == object;
		}

		/**
		 * 相当于getView方法，返回一个视图页面
		 */
		@Override
		public Object instantiateItem(ViewGroup container, int position)
		{
			ImageView iv = mImgList.get(position);

			// 添加到viewPager中,以下两种效果是一样的
			// mPager.addView(iv);
			container.addView(iv);

			// 需要返回的是显示的imageView

			return iv;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object)
		{
			// 从viewPager中移除imageView
			// ImageView iv = mImgList.get(position);
			// mPager.addView(iv);
			container.removeView((View) object);
		}

	}

	/**
	 * 当viewPager正在滑动时的回调
	 * 
	 * @position:当前所处的页面
	 * @positionOffset:指的是百分比
	 * @positionOffsetPixels:实际滑动的距离(px值)
	 */
	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
	{
		// 1.去对滑动的点做操作
		// 2.设置marginleft
		RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) mSelectedPoint.getLayoutParams();
		// 值的设置
		params.leftMargin = (int) (mSpace * position + mSpace * positionOffset + 0.5f);// 四舍五入

		mSelectedPoint.setLayoutParams(params);
	}

	/**
	 * 当viewPager某个页面选中时的回调
	 * 
	 * @position:当前选中的位置
	 */
	@Override
	public void onPageSelected(int position)
	{
		// if (position == mImgList.size() - 1)
		// {
		// // 显示button
		// btn_start.setVisibility(View.VISIBLE);
		// }
		// else
		// {
		// // 隐藏button
		// btn_start.setVisibility(View.GONE);
		// }

		// 优化后的代码
		mButStart.setVisibility(position == mImgList.size() - 1 ? View.VISIBLE : View.GONE);
	}

	/**
	 * 当viewPager的滑动状态改变时的回调
	 * 
	 * @state:状态值
	 */
	@Override
	public void onPageScrollStateChanged(int state)
	{

	}

	/**
	 * 按钮点击事件
	 */
	@Override
	public void onClick(View view)
	{
		if (view == mButStart)
		{
			// 跳转到主界面
			gotoMain();
		}
	}

	/**
	 * 跳转方法，进入主界面
	 */
	private void gotoMain()
	{
		// 保存已经不是第一次登录
		CacheUtils.setBoolean(this, WelcomeUI.KEY_IS_FIRST, false);

		Intent intent = new Intent(GuideUI.this, MainUI.class);
		startActivity(intent);

		finish();
	}
}
