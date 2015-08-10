package com.itheima.smartbeijing.base;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.itheima.smartbeijing.R;
import com.itheima.smartbeijing.bean.NewsCenterBean.NewsCenterNewsItemBean;
import com.itheima.smartbeijing.bean.NewsListBean;
import com.itheima.smartbeijing.bean.NewsListBean.NewsListPagerTopnewsBean;
import com.itheima.smartbeijing.utils.Constans;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @包名:com.itheima.smartbeijing.base
 * @类名:NewsListPager
 * @作者:陈火炬
 * @时间:2015-8-9 上午11:05:31
 * 
 * @描述:新闻页面对应的list
 * 
 * @SVN版本号:$Rev: 24 $
 * @更新人:$Author: chj $
 * @更新描述:TODO
 * 
 */
public class NewsListPager extends NewCenterBaseMenu
{
	protected static final String			TAG	= "NewsListPager";

	@ViewInject(R.id.news_list_pager)
	private ViewPager						mPager;				// viewPager

	@ViewInject(R.id.news_list_pic_title)
	private TextView						mTvTitle;				// 图片title

	@ViewInject(R.id.news_list_point_container)
	private LinearLayout					mPointContainer;		// 装点的容器

	private NewsCenterNewsItemBean			mData;

	private List<NewsListPagerTopnewsBean>	mPicDatas;

	private BitmapUtils						mBitmapUtils;

	public NewsListPager(Context context, NewsCenterNewsItemBean data) {
		super(context);

		this.mData = data;

		mBitmapUtils = new BitmapUtils(mContext);
	}

	@Override
	protected View initView()
	{
		View view = View.inflate(mContext, R.layout.news_list_pager, null);

		// 注入
		ViewUtils.inject(this, view);

		return view;
	}

	@Override
	public void initData()
	{
		// 去网络加载数据
		loadNetData();

	}

	/**
	 * 加载网络数据
	 */
	private void loadNetData()
	{
		String url = Constans.SERVER_URL + mData.url;

		HttpUtils utils = new HttpUtils();

		utils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				String result = responseInfo.result;

				// Toast.makeText(mContext, result, 1).show();
				Log.e(TAG, "网络数据正确返回：" + result);

				// 处理数据
				processData(result);
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				Log.e(TAG, "网络数据失败返回：" + msg);
			}
		});

	}

	/**
	 * 处理网络数据,即对网络数据进行Json解析
	 * 
	 * @param data
	 *            : 获取的网络数据
	 */
	private void processData(String json)
	{
		// 1.json串解析
		Gson gson = new Gson();
		NewsListBean bean = gson.fromJson(json, NewsListBean.class);
		mPicDatas = bean.data.topnews;

		// 给viewPager加载数据-->adapter-->list
		mPager.setAdapter(new NewsTopPicAdapter());
	}

	class NewsTopPicAdapter extends PagerAdapter
	{
		@Override
		public int getCount()
		{
			if (mPicDatas != null) { return mPicDatas.size(); }
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
			ImageView iv = new ImageView(mContext);
			iv.setScaleType(ScaleType.FIT_XY);
			iv.setImageResource(R.drawable.pic_item_list_default);// 设置默认资源

			// 设置网络图片
			NewsListPagerTopnewsBean bean = mPicDatas.get(position);
			String imgUrl = bean.topimage;

			// 网络获取图片数据
			mBitmapUtils.display(iv, imgUrl);

			// 添加iv
			container.addView(iv);
			return iv;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object)
		{
			container.removeView((View) object);
		}
	}
}
