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
 * @包名:com.itheima.wisdombeijing.fragment
 * @类名:LeftMenuFragment
 * @作者:陈火炬
 * @时间:2015-8-6 下午7:56:08
 * 
 * 
 * @描述:左侧菜单
 */
public class LeftMenuFragment extends BaseFragment
{

	private List<NewsCenterMenuListBean>	mMenuDatas;	// 菜单对应的数据
	private ListView						mListView;		// 页面的listView
	private int								mCurrentItem;	// 当前选中项

	@Override
	protected View initView()
	{
		// TextView tv = new TextView(mActivity);
		//
		// tv.setText("菜单内容");
		//
		// return tv;

		mListView = new ListView(mActivity);

		// 给listView设置样式
		mListView.setBackgroundColor(Color.BLACK);// 背景设置
		mListView.setDividerHeight(0);// 去除分割线
		mListView.setPadding(0, 120, 0, 0);// 设置PaddingTop(40)
		mListView.setCacheColorHint(android.R.color.transparent);// 去掉缓存颜色
		mListView.setSelector(android.R.color.transparent);// 去掉selector

		return mListView;
	}

	/**
	 * 给菜单设置数据
	 * 
	 * @param datas
	 */
	public void setMenuData(List<NewsCenterMenuListBean> datas)
	{
		// 设置默认选中第一个item
		this.mCurrentItem = 0;

		// 数据接收
		this.mMenuDatas = datas;

		// 数据设置-->adapter-->list
		mListView.setAdapter(new LeftMenuFragmentAdapter());
	}

	/**
	 * 自定义左侧菜单适配器
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
				// 没有复用时
				convertView = View.inflate(mActivity, R.layout.item_menu, null);
			}
			// else
			// {
			// // 复用时
			// }

			TextView tv = (TextView) convertView;

			// 给视图加载数据
			NewsCenterMenuListBean bean = mMenuDatas.get(position);
			tv.setText(bean.title);

			// 判断默认选中项
			// if (mCurrentItem == position)
			// {
			// // 让textView enable
			// tv.setEnabled(true);
			// }
			// else
			// {
			// // 让textView disable
			// tv.setEnabled(false);
			// }
			tv.setEnabled(mCurrentItem == position);

			// 返回显示数据View
			return tv;
		}
	}
}
