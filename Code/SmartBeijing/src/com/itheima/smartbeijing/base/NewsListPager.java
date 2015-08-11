package com.itheima.smartbeijing.base;

import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.itheima.smartbeijing.R;
import com.itheima.smartbeijing.bean.NewsCenterBean.NewsCenterNewsItemBean;
import com.itheima.smartbeijing.bean.NewsListBean;
import com.itheima.smartbeijing.bean.NewsListBean.NewsListPagerNewsBean;
import com.itheima.smartbeijing.bean.NewsListBean.NewsListPagerTopnewsBean;
import com.itheima.smartbeijing.utils.CacheUtils;
import com.itheima.smartbeijing.utils.Constans;
import com.itheima.smartbeijing.widget.HorizontalScrollViewPager;
import com.itheima.smartbeijing.widget.RefreshListView;
import com.itheima.smartbeijing.widget.RefreshListView.OnRefreshListener;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @����:com.itheima.smartbeijing.base
 * @����:NewsListPager
 * @����:�»��
 * @ʱ��:2015-8-9 ����11:05:31
 * 
 * @����:����ҳ���Ӧ��list
 * 
 * @SVN�汾��:$Rev: 28 $
 * @������:$Author: chj $
 * @��������:TODO
 * 
 */
public class NewsListPager extends NewCenterBaseMenu implements OnPageChangeListener, OnRefreshListener
{
	protected static final String			TAG	= "NewsListPager";

	@ViewInject(R.id.news_list_pager)
	private HorizontalScrollViewPager		mPager;				// �Զ����viewPager

	@ViewInject(R.id.news_list_pic_title)
	private TextView						mTvTitle;				// ͼƬtitle

	@ViewInject(R.id.news_list_point_container)
	private LinearLayout					mPointContainer;		// װ�������

	@ViewInject(R.id.news_list_item_list)
	private RefreshListView					mListView;				// listView
	private NewsAdapter						mNewsAdapter;			// �����Զ�������������

	private NewsCenterNewsItemBean			mData;

	private List<NewsListPagerTopnewsBean>	mPicDatas;

	private BitmapUtils						mBitmapUtils;

	private AutoSwitchPicTask				mSwitchPicTask;

	private List<NewsListPagerNewsBean>		mNewsDatas;

	private String							mMoreUrl;

	public NewsListPager(Context context, NewsCenterNewsItemBean data) {
		super(context);

		this.mData = data;

		mBitmapUtils = new BitmapUtils(mContext);
	}

	@Override
	protected View initView()
	{
		View view = View.inflate(mContext, R.layout.news_list_pager, null);

		// ע��
		ViewUtils.inject(this, view);

		View topNewsView = View.inflate(mContext, R.layout.news_top, null);
		ViewUtils.inject(this, topNewsView);

		// ��listView���HeaderView
		// mListView.addHeaderView(topNewsView);
		mListView.addCustomHeaderView(topNewsView);

		// ����ˢ�µļ���
		mListView.setOnRefreshListener(this);

		return view;
	}

	@Override
	public void initData()
	{
		// ȥ�����������
		loadNetData();
	}

	/**
	 * ������������
	 */
	private void loadNetData()
	{
		final String url = Constans.SERVER_URL + mData.url;

		// �ӻ����ȼ�������
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

				// Log.e(TAG, "����������ȷ���أ�" + result);

				// �洢��������
				CacheUtils.setString(mContext, url, result);

				// ��������
				processData(result);
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				// Log.e(TAG, "��������ʧ�ܷ��أ�" + msg);
			}
		});
	}

	/**
	 * ������������,�����������ݽ���Json����
	 * 
	 * @param data
	 *            : ��ȡ����������
	 */
	private void processData(String json)
	{
		// 1.json������
		Gson gson = new Gson();
		NewsListBean bean = gson.fromJson(json, NewsListBean.class);
		mPicDatas = bean.data.topnews;// ��ȡtopnews����
		mNewsDatas = bean.data.news;// ��ȡnews����
		mMoreUrl = bean.data.more;

		// ��viewPager��������-->adapter-->list
		mPager.setAdapter(new NewsTopPicAdapter());

		// ��̬��ӵ�
		// 1.��յ�
		mPointContainer.removeAllViews();

		// 2.�ӵ�
		for (int i = 0; i < mPicDatas.size(); i++)
		{
			View point = new View(mContext);

			point.setBackgroundResource(R.drawable.dot_normal);// Ĭ��ͼƬ
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);// 5,5
			params.leftMargin = 10;

			if (i == 0)
			{
				mTvTitle.setText(mPicDatas.get(i).title);// ��ʼ��title
				point.setBackgroundResource(R.drawable.dot_focus);
			}

			mPointContainer.addView(point, params);
		}

		// 3.�л���
		mPager.setOnPageChangeListener(this);

		// 4.������ʱ�ֲ�
		if (mSwitchPicTask == null)
		{
			mSwitchPicTask = new AutoSwitchPicTask();
		}
		mSwitchPicTask.start();

		// 5.����viewPager��touch����
		mPager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				switch (event.getAction())
				{
					case MotionEvent.ACTION_DOWN:// ��ָ����
						// ֹͣ�ֲ�
						mSwitchPicTask.stop();
						break;
					case MotionEvent.ACTION_UP:// ��ָ�ɿ�
					case MotionEvent.ACTION_CANCEL:// ȡ��
						// ��ʼ�ֲ�
						mSwitchPicTask.start();
						break;
					default:
						break;
				}
				return false;
			}
		});

		// ��listView������
		mNewsAdapter = new NewsAdapter();
		mListView.setAdapter(mNewsAdapter);

	}

	/**
	 * �Զ���listView��������
	 * 
	 */
	class NewsAdapter extends BaseAdapter
	{

		@Override
		public int getCount()
		{
			if (mNewsDatas != null) { return mNewsDatas.size(); }
			return 0;
		}

		@Override
		public Object getItem(int position)
		{
			if (mNewsDatas != null) { return mNewsDatas.get(position); }
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
			ViewHolder holder = null;

			if (convertView == null)
			{
				// û�и���
				convertView = View.inflate(mContext, R.layout.item_news, null);
				holder = new ViewHolder();

				holder.iv_icon = (ImageView) convertView.findViewById(R.id.item_news_iv_icon);
				holder.tv_title = (TextView) convertView.findViewById(R.id.item_news_tv_title);
				holder.tv_time = (TextView) convertView.findViewById(R.id.item_news_tv_time);

				convertView.setTag(holder);
			}
			else
			{
				// �и���
				holder = (ViewHolder) convertView.getTag();
			}

			NewsListPagerNewsBean bean = mNewsDatas.get(position);

			// ���ñ���ͷ���ʱ��
			holder.tv_title.setText(bean.title);
			holder.tv_time.setText(bean.pubdate);

			// ����ͼ��
			mBitmapUtils.display(holder.iv_icon, bean.listimage);

			return convertView;
		}
	}

	/**
	 * view������,����ʹ�õ�.
	 */
	static class ViewHolder
	{
		ImageView	iv_icon;
		TextView	tv_title;
		TextView	tv_time;
	}

	/**
	 * ͼƬ�Զ���ʱ�ֲ�
	 * 
	 * ���д������
	 */
	class AutoSwitchPicTask extends Handler implements Runnable
	{
		private final static long	DELAYED	= 3000; // �л�ʱ��(2000)

		/**
		 * ��ʼ�ֲ�
		 */
		public void start()
		{
			stop();
			postDelayed(this, DELAYED);
		}

		@Override
		public void run()
		{
			// ��viewPagerѡ����һ��
			int item = mPager.getCurrentItem();
			// if (item == mPager.getAdapter().getCount() - 1)
			// {
			// mPager.setCurrentItem(0);
			// }
			// else
			// {
			// mPager.setCurrentItem(++item);
			// }
			// �Ż�֮��
			if (item == mPager.getAdapter().getCount() - 1)
			{
				item = -1;
			}
			mPager.setCurrentItem(++item);

			// �ٴ�ִ��
			postDelayed(this, DELAYED);
		}

		/**
		 * ֹͣ�ֲ�
		 */
		public void stop()
		{
			// ��ն���,run�����Ͳ���ִ��
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
			iv.setImageResource(R.drawable.pic_item_list_default);// ����Ĭ����Դ

			// ��������ͼƬ
			NewsListPagerTopnewsBean bean = mPicDatas.get(position);
			String imgUrl = bean.topimage;

			// �����ȡͼƬ����
			mBitmapUtils.display(iv, imgUrl);

			// ���iv
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
	 * ҳ��ѡ��ʱ�˷�������
	 */
	@Override
	public void onPageSelected(int position)
	{
		// ������л�
		int count = mPointContainer.getChildCount();
		for (int i = 0; i < count; i++)
		{
			View view = mPointContainer.getChildAt(i);
			view.setBackgroundResource(R.drawable.dot_normal);
		}
		mPointContainer.getChildAt(position).setBackgroundResource(R.drawable.dot_focus);

		// ����pic��title
		NewsListPagerTopnewsBean bean = mPicDatas.get(position);
		mTvTitle.setText(bean.title);
	}

	@Override
	public void onPageScrollStateChanged(int state)
	{

	}

	@Override
	public void onRefreshing()
	{
		// ������ȥ��ȡ����
		Log.e(TAG, "����ˢ��......");

		final String url = Constans.SERVER_URL + mData.url;

		// �ӻ����ȼ�������
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

				Log.e(TAG, "����������ȷ���أ�" + result);

				// �洢��������
				CacheUtils.setString(mContext, url, result);

				// ��������
				processData(result);

				// ����listView������ˢ�µ�view
				mListView.refreshFinish();
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				// ����listView������ˢ�µ�view
				mListView.refreshFinish();
			}
		});
	}

	@Override
	public void onLoadingMore()
	{
		// ȥ�����������
		if (TextUtils.isEmpty(mMoreUrl))
		{
			// ��֪listView�������
			mListView.refreshFinish();

			Toast.makeText(mContext, "û�и�������", 1).show();
			return;
		}

		String url = Constans.SERVER_URL + mMoreUrl;

		HttpUtils utils = new HttpUtils();

		utils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				String result = responseInfo.result;

				Log.e(TAG, "����������ȷ���أ�" + result);

				// ��mNewsData�������
				Gson gson = new Gson();
				NewsListBean bean = gson.fromJson(result, NewsListBean.class);
				List<NewsListPagerNewsBean> list = bean.data.news;
				mNewsDatas.addAll(list);

				// �������ݸ��£��͸���һ��,ӦΪmore : ""
				mMoreUrl = bean.data.more;

				// adapterˢ��,���Զ�ˢ��UI
				mNewsAdapter.notifyDataSetChanged();

				// ����listView������ˢ�µ�view
				mListView.refreshFinish();
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				// ����listView������ˢ�µ�view
				mListView.refreshFinish();
			}
		});
	}
}
