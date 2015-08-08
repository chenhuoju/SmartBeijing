package com.itheima.smartbeijing.base.newscentermenu;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itheima.smartbeijing.R;
import com.itheima.smartbeijing.base.NewCenterBaseMenu;
import com.itheima.smartbeijing.bean.NewsCenterBean.NewsCenterMenuListBean;
import com.itheima.smartbeijing.bean.NewsCenterBean.NewsCenterNewsItemBean;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.viewpagerindicator.TabPageIndicator;

/**
 * @包名:com.itheima.smartbeijing.base.newscentermenu
 * @类名:NewCenterNewsMenu
 * @作者:陈火炬
 * @时间:2015-8-7 下午6:54:15
 * 
 * 
 * @描述:新闻中心-->页面中-->新闻菜单中对应的内容页面
 * 
 * @SVN版本号:$Rev: 12 $
 * @更新人:$Author: chj $
 * @更新描述:TODO
 * 
 */
public class NewCenterNewsMenu extends NewCenterBaseMenu
{

	@ViewInject(R.id.newscenter_news_indicator)
	private TabPageIndicator				mIndicator;

	@ViewInject(R.id.newscenter_news_pager)
	private ViewPager						mPager;

	private NewsCenterMenuListBean			mMenuData;		// 菜单数据

	private List<NewsCenterNewsItemBean>	mPagerDatas;	// viewPager对应的数据

	public NewCenterNewsMenu(Context context, NewsCenterMenuListBean data) {
		super(context);

		this.mMenuData = data;
		mPagerDatas = this.mMenuData.children;
	}

	@Override
	protected View initView()
	{
		// TextView tv = new TextView(mContext);
		// tv.setText("新闻页面的内容区域");
		// tv.setTextSize(24);
		// tv.setTextColor(Color.RED);
		// tv.setGravity(Gravity.CENTER);
		// return tv;

		View view = View.inflate(mContext, R.layout.newscenter_news, null);

		// ViewUtils注入
		ViewUtils.inject(this, view);

		return view;
	}

	@Override
	public void initData()
	{
		// 加载list数据

		// 给viewPager设置adapter-->list
		mPager.setAdapter(new NewPagerAdapter());

		// 给viewPagerIndicator设置viewPager
		mIndicator.setViewPager(mPager);
	}

	class NewPagerAdapter extends PagerAdapter
	{

		@Override
		public int getCount()
		{
			if (mPagerDatas != null) { return mPagerDatas.size(); }
			return 0;
		}

		@Override
		public boolean isViewFromObject(View view, Object object)
		{
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position)
		{
			// TODO:
			NewsCenterNewsItemBean bean = mPagerDatas.get(position);

			// 测试用的,页面临时显示
			TextView tv = new TextView(mContext);
			tv.setText(bean.title);
			tv.setTextSize(24);
			tv.setTextColor(Color.RED);
			tv.setGravity(Gravity.CENTER);

			container.addView(tv);
			return tv;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object)
		{
			container.removeView((View) object);
		}

		/**
		 * 返回viewPager对应的title
		 */
		@Override
		public CharSequence getPageTitle(int position)
		{
			if (mPagerDatas != null) { return mPagerDatas.get(position).title; }
			return super.getPageTitle(position);
		}
	}
}
