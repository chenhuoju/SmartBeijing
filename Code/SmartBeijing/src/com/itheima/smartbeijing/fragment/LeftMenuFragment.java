package com.itheima.smartbeijing.fragment;

import java.util.List;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.itheima.smartbeijing.R;
import com.itheima.smartbeijing.base.BaseFragment;
import com.itheima.smartbeijing.bean.NewsCenterBean.NewsCenterMenuListBean;

/**
 * @����:com.itheima.wisdombeijing.fragment
 * @����:LeftMenuFragment
 * @����:�»��
 * @ʱ��:2015-8-6 ����7:56:08
 * 
 * 
 * @����:���˵�
 */
public class LeftMenuFragment extends BaseFragment
{

	private List<NewsCenterMenuListBean>	mMenuDatas;	// �˵���Ӧ������
	private ListView						mListView;		// ҳ���listView
	private int								mCurrentItem;	// ��ǰѡ����

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
		mListView.setAdapter(new LeftMenuFragmentAdapter());
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
}
