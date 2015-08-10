package com.itheima.smartbeijing.base;

import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
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
import com.itheima.smartbeijing.utils.CacheUtils;
import com.itheima.smartbeijing.utils.Constans;
import com.itheima.smartbeijing.widget.HorizontalScrollViewPager;
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
 * @SVN版本号:$Rev: 25 $
 * @更新人:$Author: chj $
 * @更新描述:TODO
 * 
 */
public class NewsListPager extends NewCenterBaseMenu implements OnPageChangeListener
{
	protected static final String			TAG	= "NewsListPager";

	@ViewInject(R.id.news_list_pager)
	private HorizontalScrollViewPager		mPager;				// 自定义的viewPager

	@ViewInject(R.id.news_list_pic_title)
	private TextView						mTvTitle;				// 图片title

	@ViewInject(R.id.news_list_point_container)
	private LinearLayout					mPointContainer;		// 装点的容器

	private NewsCenterNewsItemBean			mData;

	private List<NewsListPagerTopnewsBean>	mPicDatas;

	private BitmapUtils						mBitmapUtils;

	private AutoSwitchPicTask				mSwitchPicTask;

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
		final String url = Constans.SERVER_URL + mData.url;

		// 从缓存先加载数据
		String json = CacheUtils.getString(mContext, url);
		if (!TextUtils.isEmpty(json))
		{
			processData(json);
		}

		HttpUtils utils = new HttpUtils();

		utils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				String result = responseInfo.result;

				// Toast.makeText(mContext, result, 1).show();
				// Log.e(TAG, "网络数据正确返回：" + result);

				//存储缓存数据
				CacheUtils.setString(mContext, url, result);
				
				// 处理数据
				processData(result);
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				// Log.e(TAG, "网络数据失败返回：" + msg);
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

		// 动态添加点
		// 1.清空点
		mPointContainer.removeAllViews();

		// 2.加点
		for (int i = 0; i < mPicDatas.size(); i++)
		{
			View point = new View(mContext);

			point.setBackgroundResource(R.drawable.dot_normal);// 默认图片
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);// 5,5
			params.leftMargin = 10;

			if (i == 0)
			{
				mTvTitle.setText(mPicDatas.get(i).title);// 初始化title
				point.setBackgroundResource(R.drawable.dot_focus);
			}

			mPointContainer.addView(point, params);
		}

		// 3.切换点
		mPager.setOnPageChangeListener(this);

		// 4.处理延时轮播
		if (mSwitchPicTask == null)
		{
			mSwitchPicTask = new AutoSwitchPicTask();
		}
		mSwitchPicTask.start();

		// 5.设置viewPager的touch监听
		mPager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				switch (event.getAction())
				{
					case MotionEvent.ACTION_DOWN:// 手指按下
						// 停止轮播
						mSwitchPicTask.stop();
						break;
					case MotionEvent.ACTION_UP:// 手指松开
					case MotionEvent.ACTION_CANCEL:// 取消
						// 开始轮播
						mSwitchPicTask.start();
						break;
					default:
						break;
				}
				return false;
			}
		});
	}

	/**
	 * 图片自动延时轮播
	 * 
	 * 进行打包处理
	 */
	class AutoSwitchPicTask extends Handler implements Runnable
	{
		private final static long	DELAYED	= 2000;

		/**
		 * 开始轮播
		 */
		public void start()
		{
			stop();
			postDelayed(this, DELAYED);
		}

		@Override
		public void run()
		{
			// 让viewPager选中下一个
			int item = mPager.getCurrentItem();
			// if (item == mPager.getAdapter().getCount() - 1)
			// {
			// mPager.setCurrentItem(0);
			// }
			// else
			// {
			// mPager.setCurrentItem(++item);
			// }
			// 优化之后
			if (item == mPager.getAdapter().getCount() - 1)
			{
				item = -1;
			}
			mPager.setCurrentItem(++item);

			// 再次执行
			postDelayed(this, DELAYED);
		}

		/**
		 * 停止轮播
		 */
		public void stop()
		{
			// 清空队列,run方法就不在执行
			removeCallbacksAndMessages(null);
		}

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

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
	{

	}

	/**
	 * 页面选中时此方法调用
	 */
	@Override
	public void onPageSelected(int position)
	{
		// 点进行切换
		int count = mPointContainer.getChildCount();
		for (int i = 0; i < count; i++)
		{
			View view = mPointContainer.getChildAt(i);
			view.setBackgroundResource(R.drawable.dot_normal);
		}
		mPointContainer.getChildAt(position).setBackgroundResource(R.drawable.dot_focus);

		// 设置pic的title
		NewsListPagerTopnewsBean bean = mPicDatas.get(position);
		mTvTitle.setText(bean.title);
	}

	@Override
	public void onPageScrollStateChanged(int state)
	{

	}
}
