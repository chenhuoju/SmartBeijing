package com.itheima.smartbeijing.fragment;

import java.util.List;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.itheima.smartbeijing.MainUI;
import com.itheima.smartbeijing.R;
import com.itheima.smartbeijing.base.BaseFragment;
import com.itheima.smartbeijing.bean.NewsCenterBean.NewsCenterMenuListBean;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * @����:com.itheima.wisdombeijing.fragment
 * @����:LeftMenuFragment
 * @����:�»��
 * @ʱ��:2015-8-6 ����7:56:08
 * 
 * 
 * @����:���˵�
 */
public class LeftMenuFragment extends BaseFragment implements OnItemClickListener
{

	private List<NewsCenterMenuListBean>	mMenuDatas;	// �˵���Ӧ������
	private ListView						mListView;		// ҳ���listView
	private int								mCurrentItem;	// ��ǰѡ����
	private LeftMenuFragmentAdapter			mMenuadapter;	// �����Զ������˵�����������

	@Override
	protected View initView()
	{
		// TextView tv = new TextView(mActivity);
		//
		// tv.setText("�˵�����");
		//
		// return tv;

		mListView = new ListView(mActivity);

		// ��listView������ʽ
		mListView.setBackgroundColor(Color.BLACK);// ��������
		mListView.setDividerHeight(0);// ȥ���ָ���
		mListView.setPadding(0, 120, 0, 0);// ����PaddingTop(40)
		mListView.setCacheColorHint(android.R.color.transparent);// ȥ��������ɫ
		mListView.setSelector(android.R.color.transparent);// ȥ��selector

		// ��listView����item����¼�
		mListView.setOnItemClickListener(this);

		return mListView;
	}

	/**
	 * ���˵���������
	 * 
	 * @param datas
	 */
	public void setMenuData(List<NewsCenterMenuListBean> datas)
	{
		// ����Ĭ��ѡ�е�һ��item
		this.mCurrentItem = 0;

		// ���ݽ���
		this.mMenuDatas = datas;

		// ��������-->adapter-->list
		mMenuadapter = new LeftMenuFragmentAdapter();
		mListView.setAdapter(mMenuadapter);
	}

	/**
	 * �Զ������˵�������
	 */
	class LeftMenuFragmentAdapter extends BaseAdapter
	{

		@Override
		public int getCount()
		{
			if (mMenuDatas != null) { return mMenuDatas.size(); }
			return 0;
		}

		@Override
		public Object getItem(int position)
		{
			if (mMenuDatas != null) { return mMenuDatas.get(position); }
			return null;
		}

		@Override
		public long getItemId(int position)
		{
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			if (convertView == null)
			{
				// û�и���ʱ
				convertView = View.inflate(mActivity, R.layout.item_menu, null);
			}
			// else
			// {
			// // ����ʱ
			// }

			TextView tv = (TextView) convertView;

			// ����ͼ��������
			NewsCenterMenuListBean bean = mMenuDatas.get(position);
			tv.setText(bean.title);

			// �ж�Ĭ��ѡ����
			// if (mCurrentItem == position)
			// {
			// // ��textView enable
			// tv.setEnabled(true);
			// }
			// else
			// {
			// // ��textView disable
			// tv.setEnabled(false);
			// }
			tv.setEnabled(mCurrentItem == position);

			// ������ʾ����View
			return tv;
		}
	}

	/**
	 * listView��item�ĵ���¼�
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		// ��������
		if (mCurrentItem == position) { return; }

		// 1.ѡ�ж�Ӧ����
		this.mCurrentItem = position;
		// UI����
		mMenuadapter.notifyDataSetChanged();

		MainUI ui = (MainUI) mActivity;

		// 2.����˵�
		SlidingMenu slidingMenu = ui.getSlidingMenu();
		slidingMenu.toggle();

		// 3.�Ҳ���������ı� TODO
		ContentFragment contentFragment = ui.getContentFragment();
		contentFragment.swithcMenuPager(mCurrentItem);
	}
}
