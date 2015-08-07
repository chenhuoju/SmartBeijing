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
 * @����:com.itheima.wisdombeijing.fragment
 * @����:ContentFragment
 * @����:�»��
 * @ʱ��:2015-8-6 ����7:58:03
 * 
 * 
 * @����:��ҳ��������
 */
public class ContentFragment extends BaseFragment implements OnCheckedChangeListener
{
	public static final String	TAG	= "ContentFragment";

	@ViewInject(R.id.content_viewPager)
	private NoScrollViewPager	mPager;					// �ϲ�������viewPager

	@ViewInject(R.id.content_radioGroup)
	private RadioGroup			mRadioGroup;				// �ײ�����radioGroup

	private List<TabBasePager>	mPagerList;				// ҳ������

	@Override
	protected View initView()
	{
		// TextView tv = new TextView(mActivity);
		//
		// tv.setText("��ҳ����");
		//
		// return tv;

		View view = View.inflate(mActivity, R.layout.content, null);

		// ViewUtils���ߵ�ע��
		ViewUtils.inject(this, view);

		return view;
	}

	@Override
	protected void initData()
	{
		// ��ʼ������
		mPagerList = new ArrayList<TabBasePager>();

		// ���ʵ�ʵ�ҳ��
		mPagerList.add(new TabHomePager(mActivity));
		mPagerList.add(new TabNewsCenterPager(mActivity));
		mPagerList.add(new TabSmartServerPager(mActivity));
		mPagerList.add(new TabGovPager(mActivity));
		mPagerList.add(new TabSettingPager(mActivity));

		// ��viewPager��������--->adapter--->list
		mPager.setAdapter(new ContentPagerAdapter());

		// �����ǵ�RadioGroup����������
		mRadioGroup.setOnCheckedChangeListener(this);

		// ���ó�ʼ��ѡ�еĽ���,����Ĭ��ѡ�еĽ���
		mRadioGroup.check(R.id.tab_home);
	}

	/**
	 * �Զ���ViewPager������
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
		 * ��ʼ������
		 */
		@Override
		public Object instantiateItem(ViewGroup container, int position)
		{
			// Ԥ����
			// Log.e(TAG, "���ص�" + position + "ҳ");

			TabBasePager pager = mPagerList.get(position);
			View view = pager.getRootView();

			// �����ͼ��viewPager��Ҫ����ͼ
			container.addView(view);

			// ��ҳ���������������
			pager.initData();

			return view;
		}

		/**
		 * ���ٷ���
		 */
		@Override
		public void destroyItem(ViewGroup container, int position, Object object)
		{
			// Log.e(TAG, "���ٵ�" + position + "ҳ");

			container.removeView((View) object);
		}

	}

	/**
	 * ѡ�еĵ�ѡ��ť�ı�ʱ����
	 * 
	 * @group:ָ����RadioGroup
	 * @checkedId:ָ����ѡ�е�RadioGroup��id
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId)
	{
		int currentIndex = -1;
		switch (checkedId)
		{
			case R.id.tab_home:// ��ҳ
				currentIndex = 0;
				// ���ò໬�˵����ɼ�
				setSlideMenuTouchEnable(false);
				break;
			case R.id.tab_newscenter:// ����
				setSlideMenuTouchEnable(true);
				currentIndex = 1;
				break;
			case R.id.tab_smartserver:// �ǻ۷���
				currentIndex = 2;
				setSlideMenuTouchEnable(true);
				break;
			case R.id.tab_gov:// ����
				currentIndex = 3;
				setSlideMenuTouchEnable(true);
				break;
			case R.id.tab_setting:// ����
				currentIndex = 4;
				setSlideMenuTouchEnable(false);
				break;
			default:
				break;
		}

		// ��viewPager����ѡ�е�ҳ��
		mPager.setCurrentItem(currentIndex);
	}

	/**
	 * ���ò໬�˵��Ƿ�ɼ�
	 * 
	 * @param enable
	 *            :true:�ɼ�;false:���ɼ�
	 */
	private void setSlideMenuTouchEnable(boolean enable)
	{
		MainUI ui = (MainUI) mActivity;
		SlidingMenu menu = ui.getSlidingMenu();
		menu.setTouchModeAbove(enable ? SlidingMenu.TOUCHMODE_FULLSCREEN : SlidingMenu.TOUCHMODE_NONE);
	}
}
