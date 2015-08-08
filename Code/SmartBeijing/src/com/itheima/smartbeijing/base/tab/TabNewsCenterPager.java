package com.itheima.smartbeijing.base.tab;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.itheima.smartbeijing.MainUI;
import com.itheima.smartbeijing.base.NewCenterBaseMenu;
import com.itheima.smartbeijing.base.TabBasePager;
import com.itheima.smartbeijing.base.newscentermenu.NewCenterInteractMenu;
import com.itheima.smartbeijing.base.newscentermenu.NewCenterNewsMenu;
import com.itheima.smartbeijing.base.newscentermenu.NewCenterPicMenu;
import com.itheima.smartbeijing.base.newscentermenu.NewCenterTopicMenu;
import com.itheima.smartbeijing.bean.NewsCenterBean;
import com.itheima.smartbeijing.bean.NewsCenterBean.NewsCenterMenuListBean;
import com.itheima.smartbeijing.fragment.LeftMenuFragment;
import com.itheima.smartbeijing.utils.CacheUtils;
import com.itheima.smartbeijing.utils.Constans;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * @包名:com.itheima.smartbeijing.base.tab
 * @类名:TabHomePager
 * @作者:陈火炬
 * @时间:2015-8-7 上午10:13:38
 * 
 * 
 * @描述:主页界面中tab对应的新闻中心设置，用来管理菜单页面的显示
 */
public class TabNewsCenterPager extends TabBasePager
{

	protected static final String			TAG	= "TabNewsCenterPager";

	// private List<TextView> mPagerList;
	private List<NewCenterBaseMenu>			mPagerList;				// 菜单页面的集合

	private NewsCenterBean					mData;						// 页面对象的数据

	private List<NewsCenterMenuListBean>	mMenuData;					// 菜单对应的数据集合

	public TabNewsCenterPager(Context context) {
		super(context);
	}

	@Override
	public void initData()
	{
		// 1.title部分数据的设置
		mTvTitle.setText("新闻");
		mIconMenu.setVisibility(View.VISIBLE);

		// 2.内容区域数据的设置 TODO:
		// TextView tv = new TextView(mContext);
		// tv.setText("新闻内容区域");
		// tv.setTextColor(Color.RED);
		// tv.setTextSize(24);
		// tv.setGravity(Gravity.CENTER);
		//
		// LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
		// LayoutParams.MATCH_PARENT);
		// mContentContainer.addView(tv, params);

		// 读取缓存数据
		String json = CacheUtils.getString(mContext, Constans.NEW_CENTER_URL);
		if (!TextUtils.isEmpty(json))
		{
			// 有缓冲数据
			Log.e(TAG, "读取本地缓存");
			processData(json);
		}

		// 通过网络去获取数据，将数据加载到页面上来
		HttpUtils utils = new HttpUtils();

		// RequestParams params = new RequestParams();
		// 1.消息头
		// params.addHeader("", "");
		// 2.请求参数
		// post请求:
		// NameValuePair nameValuePair = new BasicNameValuePair("", "");
		// params.addBodyParameter(nameValuePair);
		// get请求:
		// NameValuePair nameValuePair = new BasicNameValuePair("name", "chj");
		// params.addQueryStringParameter(nameValuePair);
		// utils.send(HttpMethod.GET, Constans.NEW_CENTER_URL, params,
		// callBack);

		utils.send(HttpMethod.GET, Constans.NEW_CENTER_URL, new RequestCallBack<String>() {

			// 访问网络成功后的回调
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				// 取出结果值
				String result = responseInfo.result;

				Log.e(TAG, "访问网络成功:" + result);

				// 缓存数据
				CacheUtils.setString(mContext, Constans.NEW_CENTER_URL, result);

				Log.e(TAG, "读取网络缓存");

				// 对数据进行解析，并且将结果展示到页面上
				processData(result);
			}

			// 访问网络成失败后的回调
			@Override
			public void onFailure(HttpException error, String msg)
			{
				error.printStackTrace();
				Log.e(TAG, "访问网络成功:" + msg);
			}
		});

		// 模拟死数据
		// mPagerList = new ArrayList<TextView>();
		// for (int i = 0; i < 4; i++)
		// {
		// TextView tv = new TextView(mContext);
		// tv.setText("菜单" + (i + 1) + "的页面");
		//
		// mPagerList.add(tv);
		// }

		// 设置内容区域视图的展示默认值
		// switchPager(0);

	}

	/**
	 * jsons数据解析,同时把数据显示至页面上
	 * 
	 * @Gson类:toJson方法:将java对象变成json串;fromJson方法:将json串变成java对象
	 */
	protected void processData(String json)
	{
		// 1.json串的解析
		Gson gson = new Gson();
		mData = gson.fromJson(json, NewsCenterBean.class);
		mMenuData = mData.data;

		// 2.将数据展示到页面上
		// 2.1 展示到左侧菜单
		// 获取左侧菜单的fragment
		MainUI ui = (MainUI) mContext;
		LeftMenuFragment leftFragment = ui.getLeftFragment();
		// 设置数据
		leftFragment.setMenuData(mMenuData);

		// 2.2 展示到内容区域 TODO:
		mPagerList = new ArrayList<NewCenterBaseMenu>();
		for (int i = 0; i < mMenuData.size(); i++)
		{
			NewsCenterMenuListBean bean = mMenuData.get(i);
			NewCenterBaseMenu menuPager = null;
			switch (bean.type)
			{
				case 1:// 新闻
					menuPager = new NewCenterNewsMenu(mContext, bean);
					break;
				case 10:// 专题
					menuPager = new NewCenterTopicMenu(mContext);
					break;
				case 2:// 组图
					menuPager = new NewCenterPicMenu(mContext);
					break;
				case 3:// 互动
					menuPager = new NewCenterInteractMenu(mContext);
					break;
				default:
					break;
			}
			mPagerList.add(menuPager);
		}

		// 设置内容区域视图的展示默认值
		switchMenuPager(0);
	}

	/**
	 * 设置内容区域视图的展示---->测试使用的
	 * 
	 */
	// private void switchPager(int i)
	// {
	// // 清空内容的数据
	// mContentContainer.removeAllViews();
	//
	// // TODO:伪代码，用来展示用的
	// // TextView tv = mPagerList.get(i);
	// // tv.setTextColor(Color.RED);
	// // tv.setTextSize(24);
	// // tv.setGravity(Gravity.CENTER);
	// //
	// // LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
	// // LayoutParams.MATCH_PARENT);
	// // mContentContainer.addView(tv, params);
	//
	// Log.e(TAG, "切换到第" + i + "菜单");
	//
	// }

	@Override
	public void switchMenuPager(int position)
	{
		Log.e(TAG, "切换到第" + position + "菜单");

		// 清空内容的数据
		mContentContainer.removeAllViews();

		// 设置title显示
		NewsCenterMenuListBean bean = mMenuData.get(position);
		mTvTitle.setText(bean.title);

		// 页面切换
		NewCenterBaseMenu menuPager = mPagerList.get(position);
		View rootView = menuPager.getRootView();

		mContentContainer.addView(rootView);

		// 加载数据
		menuPager.initData();

	}
}
