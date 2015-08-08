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
 * @����:com.itheima.smartbeijing.base.newscentermenu
 * @����:NewCenterNewsMenu
 * @����:�»��
 * @ʱ��:2015-8-7 ����6:54:15
 * 
 * 
 * @����:��������-->ҳ����-->���Ų˵��ж�Ӧ������ҳ��
 * 
 * @SVN�汾��:$Rev: 12 $
 * @������:$Author: chj $
 * @��������:TODO
 * 
 */
public class NewCenterNewsMenu extends NewCenterBaseMenu
{

	@ViewInject(R.id.newscenter_news_indicator)
	private TabPageIndicator				mIndicator;

	@ViewInject(R.id.newscenter_news_pager)
	private ViewPager						mPager;

	private NewsCenterMenuListBean			mMenuData;		// �˵�����

	private List<NewsCenterNewsItemBean>	mPagerDatas;	// viewPager��Ӧ������

	public NewCenterNewsMenu(Context context, NewsCenterMenuListBean data) {
		super(context);

		this.mMenuData = data;
		mPagerDatas = this.mMenuData.children;
	}

	@Override
	protected View initView()
	{
		// TextView tv = new TextView(mContext);
		// tv.setText("����ҳ�����������");
		// tv.setTextSize(24);
		// tv.setTextColor(Color.RED);
		// tv.setGravity(Gravity.CENTER);
		// return tv;

		View view = View.inflate(mContext, R.layout.newscenter_news, null);

		// ViewUtilsע��
		ViewUtils.inject(this, view);

		return view;
	}

	@Override
	public void initData()
	{
		// ����list����

		// ��viewPager����adapter-->list
		mPager.setAdapter(new NewPagerAdapter());

		// ��viewPagerIndicator����viewPager
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

			// �����õ�,ҳ����ʱ��ʾ
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
		 * ����viewPager��Ӧ��title
		 */
		@Override
		public CharSequence getPageTitle(int position)
		{
			if (mPagerDatas != null) { return mPagerDatas.get(position).title; }
			return super.getPageTitle(position);
		}
	}
}
