package com.itheima.smartbeijing.base.newscentermenu;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima.smartbeijing.MainUI;
import com.itheima.smartbeijing.R;
import com.itheima.smartbeijing.base.NewCenterBaseMenu;
import com.itheima.smartbeijing.bean.NewsCenterBean.NewsCenterMenuListBean;
import com.itheima.smartbeijing.bean.NewsCenterBean.NewsCenterNewsItemBean;
import com.itheima.smartbeijing.widget.TouchTabPageIndicator;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
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
 * @SVN�汾��:$Rev: 22 $
 * @������:$Author: chj $
 * @��������:TODO
 * 
 */
public class NewCenterNewsMenu extends NewCenterBaseMenu implements OnPageChangeListener
{

	@ViewInject(R.id.newscenter_news_indicator)
	private TouchTabPageIndicator			mIndicator;		// �Զ����indicator
	// private TabPageIndicator mIndicator;

	@ViewInject(R.id.newscenter_news_pager)
	private ViewPager						mPager;

	@ViewInject(R.id.newscenter_news_arrow)
	private ImageView						mIvArrow;		// ��ͷ

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

		// ��viewPager����ѡ�м���
		// ��viewPager��Indicator����ʹ��ʱ��Ҫ���ü�������������Indicator�ļ���������������viewPager�ļ���
		mIndicator.setOnPageChangeListener(this);
	}

	/**
	 * ע���������ť����¼�����ת��һ����Ŀ
	 */
	@OnClick(R.id.newscenter_news_arrow)
	public void clickArrow(View view)
	{
		// ��viewPagerѡ����һ��
		int item = mPager.getCurrentItem();
		mPager.setCurrentItem(++item);
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

	@Override
	public void onPageScrollStateChanged(int position)
	{

	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
	{
		MainUI ui = (MainUI) mContext;
		SlidingMenu slidingMenu = ui.getSlidingMenu();

		// if (position == 0)
		// {
		// // ��ѡ�е�һҳʱ��slidingMenu��touchModeӦ��Ϊfill_screen
		// slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		// }
		// else
		// {
		// // �������Ϊnone
		// slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		// }
		// �Ż�֮��
		slidingMenu.setTouchModeAbove(position == 0 ? SlidingMenu.TOUCHMODE_FULLSCREEN : SlidingMenu.TOUCHMODE_NONE);
	}

	@Override
	public void onPageSelected(int state)
	{

	}

}
