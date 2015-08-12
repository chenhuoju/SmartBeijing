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
 * @����:com.itheima.smartbeijing.base.newscentermenu
 * @����:NewCenterPicMenu
 * @����:�»��
 * @ʱ��:2015-8-7 ����8:02:58
 * 
 * 
 * @����:��������-->ҳ����-->��ͼ�˵��ж�Ӧ������ҳ��
 * 
 * @SVN�汾��:$Rev: 37 $
 * @������:$Author: chj $
 * @��������:TODO
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

	private List<NewsListPagerNewsBean>	mNewsDatas;							// ��������

	// private BitmapUtils mBitmapUtils;//���˷�װ��xUtils������ͼƬ��ʾ
	private ImageHelper					mHelper;								// �Լ���װ����������ͼƬ��ʾ

	private ImageButton					mIbListOrGrid;
	private boolean						isGrid;								// Ĭ����listView��ʾ

	private static final String			KEY_PHOTOS_URL	= "photos_url";		// ����ͼƬ�ļ���

	public NewCenterPicMenu(Context context) {
		super(context);

		// mBitmapUtils = new BitmapUtils(mContext);
		mHelper = new ImageHelper(context);
	}

	@Override
	protected View initView()
	{
		// TextView tv = new TextView(mContext);
		// tv.setText("��ͼҳ�����������");
		// tv.setTextSize(24);
		// tv.setTextColor(Color.RED);
		// tv.setGravity(Gravity.CENTER);
		// return tv;

		View view = View.inflate(mContext, R.layout.newscenter_pic, null);

		// ע��
		ViewUtils.inject(this, view);

		return view;
	}

	@Override
	public void initData()
	{
		// �����棬�����ػ�ȡ����
		String json = CacheUtils.getString(mContext, KEY_PHOTOS_URL);
		if (!TextUtils.isEmpty(json))
		{
			processData(json);
		}

		// ���ݼ��أ�ͨ�������ȡ
		HttpUtils utils = new HttpUtils();

		utils.send(HttpMethod.GET, Constans.PHOTOS_URL, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				String result = responseInfo.result;
				Log.e(TAG, "������ʳɹ�:" + result);

				// д���棬���������ݻ��浽����
				CacheUtils.setString(mContext, KEY_PHOTOS_URL, result);

				// ��������
				processData(result);
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				Log.e(TAG, "�������ʧ�ܣ�");
			}
		});
	}

	/**
	 * ������������
	 * 
	 * @param json
	 */
	private void processData(String json)
	{
		// 1.����json����
		Gson gson = new Gson();
		NewsListBean bean = gson.fromJson(json, NewsListBean.class);
		mNewsDatas = bean.data.news;

		// 2.������չʾ��listView��gridView��
		// ��listView��gridView��������-->adapter-->list
		mAdapter = new PicAdapter();
		mListView.setAdapter(mAdapter);
		mGridView.setAdapter(mAdapter);
	}

	/**
	 * �Զ�����ͼ��������
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
				// û�и���
				convertView = View.inflate(mContext, R.layout.pic_item, null);
				holder = new ViewHolder();
				holder.iv_photo = (ImageView) convertView.findViewById(R.id.pic_iv_photo);
				holder.tv_title = (TextView) convertView.findViewById(R.id.pic_tv_title);
				convertView.setTag(holder);
			}
			else
			{
				// �и���
				holder = (ViewHolder) convertView.getTag();
			}

			NewsListPagerNewsBean bean = mNewsDatas.get(position);

			// ����ͼ������
			holder.tv_title.setText(bean.title);

			// ͼƬ��ʾ
			// mBitmapUtils.display(holder.iv_photo, bean.listimage);
			mHelper.display(holder.iv_photo, bean.listimage);

			return convertView;
		}
	}

	/**
	 * view������
	 */
	static class ViewHolder
	{
		ImageView	iv_photo;
		TextView	tv_title;
	}

	/**
	 * list��grid���л�����
	 * 
	 * @param ib
	 */
	public void setSwitchButton(ImageButton ib)
	{
		this.mIbListOrGrid = ib;

		// ���ü���
		mIbListOrGrid.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		isGrid = !isGrid;

		// 1.listView��gridView��ʾЧ���л�
		mListView.setVisibility(isGrid ? View.GONE : View.VISIBLE);
		mGridView.setVisibility(isGrid ? View.VISIBLE : View.GONE);

		// 2.�л���button����ʽҪ�ı�
		mIbListOrGrid.setImageResource(isGrid ? R.drawable.icon_pic_grid_type : R.drawable.icon_pic_list_type);
	}
}
