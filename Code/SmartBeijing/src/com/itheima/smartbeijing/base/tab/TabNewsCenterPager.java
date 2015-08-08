package com.itheima.smartbeijing.base.tab;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.itheima.smartbeijing.MainUI;
import com.itheima.smartbeijing.base.NewCenterBaseMenu;
import com.itheima.smartbeijing.base.TabBasePager;
import com.itheima.smartbeijing.bean.NewsCenterBean;
import com.itheima.smartbeijing.bean.NewsCenterBean.NewsCenterMenuListBean;
import com.itheima.smartbeijing.fragment.LeftMenuFragment;
import com.itheima.smartbeijing.utils.Constans;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * @����:com.itheima.smartbeijing.base.tab
 * @����:TabHomePager
 * @����:�»��
 * @ʱ��:2015-8-7 ����10:13:38
 * 
 * 
 * @����:��ҳ������tab��Ӧ��������������
 */
public class TabNewsCenterPager extends TabBasePager
{

	protected static final String			TAG	= "TabNewsCenterPager";

	// private List<TextView> mPagerList;
	private List<NewCenterBaseMenu>			mPagerList;				// �˵�ҳ��ļ���

	private NewsCenterBean					mData;						// ҳ����������

	private List<NewsCenterMenuListBean>	mMenuData;					// �˵���Ӧ�����ݼ���

	public TabNewsCenterPager(Context context) {
		super(context);
	}

	@Override
	public void initData()
	{
		// 1.title�������ݵ�����
		mTvTitle.setText("����");
		mIconMenu.setVisibility(View.VISIBLE);

		// 2.�����������ݵ����� TODO:
		// TextView tv = new TextView(mContext);
		// tv.setText("������������");
		// tv.setTextColor(Color.RED);
		// tv.setTextSize(24);
		// tv.setGravity(Gravity.CENTER);
		//
		// LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
		// LayoutParams.MATCH_PARENT);
		// mContentContainer.addView(tv, params);

		// ͨ������ȥ��ȡ���ݣ������ݼ��ص�ҳ������
		HttpUtils utils = new HttpUtils();

		// RequestParams params = new RequestParams();
		// 1.��Ϣͷ
		// params.addHeader("", "");
		// 2.�������
		// post����:
		// NameValuePair nameValuePair = new BasicNameValuePair("", "");
		// params.addBodyParameter(nameValuePair);
		// get����:
		// NameValuePair nameValuePair = new BasicNameValuePair("name", "chj");
		// params.addQueryStringParameter(nameValuePair);
		// utils.send(HttpMethod.GET, Constans.NEW_CENTER_URL, params,
		// callBack);

		utils.send(HttpMethod.GET, Constans.NEW_CENTER_URL, new RequestCallBack<String>() {

			// ��������ɹ���Ļص�
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				// ȡ�����ֵ
				String result = responseInfo.result;

				Log.e(TAG, "��������ɹ�:" + result);

				// �����ݽ��н��������ҽ����չʾ��ҳ����
				processData(result);
			}

			// ���������ʧ�ܺ�Ļص�
			@Override
			public void onFailure(HttpException error, String msg)
			{
				error.printStackTrace();
				Log.e(TAG, "��������ɹ�:" + msg);
			}
		});

		// ģ��������
		// mPagerList = new ArrayList<TextView>();
		// for (int i = 0; i < 4; i++)
		// {
		// TextView tv = new TextView(mContext);
		// tv.setText("�˵�" + (i + 1) + "��ҳ��");
		//
		// mPagerList.add(tv);
		// }

		// ��������������ͼ��չʾĬ��ֵ
		// switchPager(0);

	}

	/**
	 * jsons���ݽ���,ͬʱ��������ʾ��ҳ����
	 * 
	 * @Gson��:toJson����:��java������json��;fromJson����:��json�����java����
	 */
	protected void processData(String json)
	{
		// 1.json���Ľ���
		Gson gson = new Gson();
		mData = gson.fromJson(json, NewsCenterBean.class);
		mMenuData = mData.data;

		// 2.������չʾ��ҳ����
		// 2.1 չʾ�����˵�
		// ��ȡ���˵���fragment
		MainUI ui = (MainUI) mContext;
		LeftMenuFragment leftFragment = ui.getLeftFragment();
		// ��������
		leftFragment.setMenuData(mMenuData);

		// 2.2 չʾ���������� TODO:
	}

	/**
	 * ��������������ͼ��չʾ
	 * 
	 */
	public void switchPager(int i)
	{
		// ������ݵ�����
		mContentContainer.removeAllViews();

		// TODO:α���룬����չʾ�õ�
		// TextView tv = mPagerList.get(i);
		// tv.setTextColor(Color.RED);
		// tv.setTextSize(24);
		// tv.setGravity(Gravity.CENTER);
		//
		// LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
		// LayoutParams.MATCH_PARENT);
		// mContentContainer.addView(tv, params);
	}
}
