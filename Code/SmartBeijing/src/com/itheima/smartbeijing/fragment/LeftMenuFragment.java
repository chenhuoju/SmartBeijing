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
 * @包名:com.itheima.wisdombeijing.fragment
 * @类名:LeftMenuFragment
 * @作者:陈火炬
 * @时间:2015-8-6 下午7:56:08
 * 
 * 
 * @描述:左侧菜单
 */
public class LeftMenuFragment extends BaseFragment implements OnItemClickListener
{

	private List<NewsCenterMenuListBean>	mMenuDatas;	// 菜单对应的数据
	private ListView						mListView;		// 页面的listView
	private int								mCurrentItem;	// 当前选中项
	private LeftMenuFragmentAdapter			mMenuadapter;	// 创建自定义左侧菜单适配器对象

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

		// 给listView设置item点击事件
		mListView.setOnItemClickListener(this);

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
		mMenuadapter = new LeftMenuFragmentAdapter();
		mListView.setAdapter(mMenuadapter);
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

	/**
	 * listView中item的点击事件
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		// 不做处理
		if (mCurrentItem == position) { return; }

		// 1.选中对应的项
		this.mCurrentItem = position;
		// UI更新
		mMenuadapter.notifyDataSetChanged();

		MainUI ui = (MainUI) mActivity;

		// 2.收起菜单
		SlidingMenu slidingMenu = ui.getSlidingMenu();
		slidingMenu.toggle();

		// 3.右侧内容区域改变 TODO
		ContentFragment contentFragment = ui.getContentFragment();
		contentFragment.swithcMenuPager(mCurrentItem);
	}
}
