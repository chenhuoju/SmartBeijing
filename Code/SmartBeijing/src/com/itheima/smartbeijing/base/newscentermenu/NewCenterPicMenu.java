package com.itheima.smartbeijing.base.newscentermenu;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.itheima.smartbeijing.R;
import com.itheima.smartbeijing.base.NewCenterBaseMenu;
import com.itheima.smartbeijing.bean.NewsListBean;
import com.itheima.smartbeijing.bean.NewsListBean.NewsListPagerNewsBean;
import com.itheima.smartbeijing.utils.CacheUtils;
import com.itheima.smartbeijing.utils.Constans;
import com.itheima.smartbeijing.utils.ImageHelper;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 
 * @包名:com.itheima.smartbeijing.base.newscentermenu
 * @类名:NewCenterPicMenu
 * @作者:陈火炬
 * @时间:2015-8-7 下午8:02:58
 * 
 * 
 * @描述:新闻中心-->页面中-->组图菜单中对应的内容页面
 * 
 * @SVN版本号:$Rev: 37 $
 * @更新人:$Author: chj $
 * @更新描述:TODO
 * 
 */
public class NewCenterPicMenu extends NewCenterBaseMenu implements OnClickListener
{
	protected static final String		TAG				= "NewCenterPicMenu";

	@ViewInject(R.id.pic_list_view)
	private ListView					mListView;

	@ViewInject(R.id.pic_grid_view)
	private GridView					mGridView;

	private PicAdapter					mAdapter;

	private List<NewsListPagerNewsBean>	mNewsDatas;							// 新闻数据

	// private BitmapUtils mBitmapUtils;//别人封装的xUtils来处理图片显示
	private ImageHelper					mHelper;								// 自己封装的类来处理图片显示

	private ImageButton					mIbListOrGrid;
	private boolean						isGrid;								// 默认是listView显示

	private static final String			KEY_PHOTOS_URL	= "photos_url";		// 缓存图片的键名

	public NewCenterPicMenu(Context context) {
		super(context);

		// mBitmapUtils = new BitmapUtils(mContext);
		mHelper = new ImageHelper(context);
	}

	@Override
	protected View initView()
	{
		// TextView tv = new TextView(mContext);
		// tv.setText("组图页面的内容区域");
		// tv.setTextSize(24);
		// tv.setTextColor(Color.RED);
		// tv.setGravity(Gravity.CENTER);
		// return tv;

		View view = View.inflate(mContext, R.layout.newscenter_pic, null);

		// 注入
		ViewUtils.inject(this, view);

		return view;
	}

	@Override
	public void initData()
	{
		// 读缓存，到本地获取数据
		String json = CacheUtils.getString(mContext, KEY_PHOTOS_URL);
		if (!TextUtils.isEmpty(json))
		{
			processData(json);
		}

		// 数据加载，通过网络获取
		HttpUtils utils = new HttpUtils();

		utils.send(HttpMethod.GET, Constans.PHOTOS_URL, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				String result = responseInfo.result;
				Log.e(TAG, "网络访问成功:" + result);

				// 写缓存，把网络数据缓存到本地
				CacheUtils.setString(mContext, KEY_PHOTOS_URL, result);

				// 处理数据
				processData(result);
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				Log.e(TAG, "网络访问失败！");
			}
		});
	}

	/**
	 * 处理网络数据
	 * 
	 * @param json
	 */
	private void processData(String json)
	{
		// 1.解析json数据
		Gson gson = new Gson();
		NewsListBean bean = gson.fromJson(json, NewsListBean.class);
		mNewsDatas = bean.data.news;

		// 2.将数据展示到listView和gridView上
		// 给listView和gridView设置数据-->adapter-->list
		mAdapter = new PicAdapter();
		mListView.setAdapter(mAdapter);
		mGridView.setAdapter(mAdapter);
	}

	/**
	 * 自定义组图适配器类
	 */
	class PicAdapter extends BaseAdapter
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
			ViewHolder holder;

			if (convertView == null)
			{
				// 没有复用
				convertView = View.inflate(mContext, R.layout.pic_item, null);
				holder = new ViewHolder();
				holder.iv_photo = (ImageView) convertView.findViewById(R.id.pic_iv_photo);
				holder.tv_title = (TextView) convertView.findViewById(R.id.pic_tv_title);
				convertView.setTag(holder);
			}
			else
			{
				// 有复用
				holder = (ViewHolder) convertView.getTag();
			}

			NewsListPagerNewsBean bean = mNewsDatas.get(position);

			// 给视图铺数据
			holder.tv_title.setText(bean.title);

			// 图片显示
			// mBitmapUtils.display(holder.iv_photo, bean.listimage);
			mHelper.display(holder.iv_photo, bean.listimage);

			return convertView;
		}
	}

	/**
	 * view持有者
	 */
	static class ViewHolder
	{
		ImageView	iv_photo;
		TextView	tv_title;
	}

	/**
	 * list和grid的切换方法
	 * 
	 * @param ib
	 */
	public void setSwitchButton(ImageButton ib)
	{
		this.mIbListOrGrid = ib;

		// 设置监听
		mIbListOrGrid.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		isGrid = !isGrid;

		// 1.listView和gridView显示效果切换
		mListView.setVisibility(isGrid ? View.GONE : View.VISIBLE);
		mGridView.setVisibility(isGrid ? View.VISIBLE : View.GONE);

		// 2.切换的button的样式要改变
		mIbListOrGrid.setImageResource(isGrid ? R.drawable.icon_pic_grid_type : R.drawable.icon_pic_list_type);
	}
}
