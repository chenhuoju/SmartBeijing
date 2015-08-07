package com.itheima.smartbeijing.fragment;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.itheima.smartbeijing.MainUI;
import com.itheima.smartbeijing.R;
import com.itheima.smartbeijing.base.BaseFragment;
import com.itheima.smartbeijing.base.TabBasePager;
import com.itheima.smartbeijing.base.tab.TabGovPager;
import com.itheima.smartbeijing.base.tab.TabHomePager;
import com.itheima.smartbeijing.base.tab.TabNewsCenterPager;
import com.itheima.smartbeijing.base.tab.TabSettingPager;
import com.itheima.smartbeijing.base.tab.TabSmartServerPager;
import com.itheima.smartbeijing.widget.NoScrollViewPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @包名:com.itheima.wisdombeijing.fragment
 * @类名:ContentFragment
 * @作者:陈火炬
 * @时间:2015-8-6 下午7:58:03
 * 
 * 
 * @描述:主页内容区域
 */
public class ContentFragment extends BaseFragment implements OnCheckedChangeListener
{
	public static final String	TAG	= "ContentFragment";

	@ViewInject(R.id.content_viewPager)
	private NoScrollViewPager	mPager;					// 上部分容器viewPager

	@ViewInject(R.id.content_radioGroup)
	private RadioGroup			mRadioGroup;				// 底部容器radioGroup

	private List<TabBasePager>	mPagerList;				// 页面数据

	@Override
	protected View initView()
	{
		// TextView tv = new TextView(mActivity);
		//
		// tv.setText("主页内容");
		//
		// return tv;

		View view = View.inflate(mActivity, R.layout.content, null);

		// ViewUtils工具的注入
		ViewUtils.inject(this, view);

		return view;
	}

	@Override
	protected void initData()
	{
		// 初始化数据
		mPagerList = new ArrayList<TabBasePager>();

		// 添加实际的页面
		mPagerList.add(new TabHomePager(mActivity));
		mPagerList.add(new TabNewsCenterPager(mActivity));
		mPagerList.add(new TabSmartServerPager(mActivity));
		mPagerList.add(new TabGovPager(mActivity));
		mPagerList.add(new TabSettingPager(mActivity));

		// 给viewPager加载数据--->adapter--->list
		mPager.setAdapter(new ContentPagerAdapter());

		// 给我们的RadioGroup，设置侦听
		mRadioGroup.setOnCheckedChangeListener(this);

		// 设置初始化选中的界面,就是默认选中的界面
		mRadioGroup.check(R.id.tab_home);
	}

	/**
	 * 自定义ViewPager适配器
	 */
	class ContentPagerAdapter extends PagerAdapter
	{

		@Override
		public int getCount()
		{
			if (mPagerList != null) { return mPagerList.size(); }
			return 0;
		}

		@Override
		public boolean isViewFromObject(View view, Object object)
		{
			return view == object;
		}

		/**
		 * 初始化方法
		 */
		@Override
		public Object instantiateItem(ViewGroup container, int position)
		{
			// 预加载
			// Log.e(TAG, "加载第" + position + "页");

			TabBasePager pager = mPagerList.get(position);
			View view = pager.getRootView();

			// 添加视图，viewPager需要放视图
			container.addView(view);

			// 给页面控制器加载数据
			pager.initData();

			return view;
		}

		/**
		 * 销毁方法
		 */
		@Override
		public void destroyItem(ViewGroup container, int position, Object object)
		{
			// Log.e(TAG, "销毁第" + position + "页");

			container.removeView((View) object);
		}

	}

	/**
	 * 选中的单选按钮改变时调用
	 * 
	 * @group:指的是RadioGroup
	 * @checkedId:指的是选中的RadioGroup的id
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId)
	{
		int currentIndex = -1;
		switch (checkedId)
		{
			case R.id.tab_home:// 首页
				currentIndex = 0;
				// 设置侧滑菜单不可见
				setSlideMenuTouchEnable(false);
				break;
			case R.id.tab_newscenter:// 新闻
				setSlideMenuTouchEnable(true);
				currentIndex = 1;
				break;
			case R.id.tab_smartserver:// 智慧服务
				currentIndex = 2;
				setSlideMenuTouchEnable(true);
				break;
			case R.id.tab_gov:// 政务
				currentIndex = 3;
				setSlideMenuTouchEnable(true);
				break;
			case R.id.tab_setting:// 设置
				currentIndex = 4;
				setSlideMenuTouchEnable(false);
				break;
			default:
				break;
		}

		// 给viewPager设置选中的页面
		mPager.setCurrentItem(currentIndex);
	}

	/**
	 * 设置侧滑菜单是否可见
	 * 
	 * @param enable
	 *            :true:可见;false:不可见
	 */
	private void setSlideMenuTouchEnable(boolean enable)
	{
		MainUI ui = (MainUI) mActivity;
		SlidingMenu menu = ui.getSlidingMenu();
		menu.setTouchModeAbove(enable ? SlidingMenu.TOUCHMODE_FULLSCREEN : SlidingMenu.TOUCHMODE_NONE);
	}
}
