package com.itheima.smartbeijing;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.itheima.smartbeijing.utils.CacheUtils;

/**
 * @����:com.itheima.wisdombeijing
 * @����:GuideUI
 * @����:�»��
 * @ʱ��:2015-8-5 ����6:01:55
 * 
 * 
 * @����:�û�����ҳ��
 */
public class GuideUI extends Activity implements OnPageChangeListener, OnClickListener
{
	private ViewPager			mPager;			// viewPager����
	private Button				mButStart;			// ��ʼ���鰴ť
	private LinearLayout		mPointContainer;	// װ��̬�������
	private View				mSelectedPoint;	// ѡ�еĵ�
	private int					mSpace;			// �����֮��ľ���

	private GuidePagerAdapter	adapter;			// �Զ�������������

	private List<ImageView>		mImgList;			// �洢viewPager�е�imageView����

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		initView();

	}

	/**
	 * ��ʼ����ͼ
	 */
	private void initView()
	{
		// ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// ���ò���
		setContentView(R.layout.guide);

		mPager = (ViewPager) findViewById(R.id.guide_pager);
		mButStart = (Button) findViewById(R.id.btn_start);
		mPointContainer = (LinearLayout) findViewById(R.id.guide_point_container);
		mSelectedPoint = findViewById(R.id.guide_point_selected);

		initData();
		initListenre();
	}

	/**
	 * ��ʼ������,�������ݼ���
	 */
	private void initData()
	{
		int[] imgRes = new int[] {
				R.drawable.guide_1,
				R.drawable.guide_2,
				R.drawable.guide_3
		};

		// ����list
		mImgList = new ArrayList<ImageView>();
		for (int i = 0; i < imgRes.length; i++)
		{
			// �½�imageView
			ImageView img = new ImageView(this);
			img.setImageResource(imgRes[i]);
			img.setScaleType(ScaleType.FIT_XY);

			// ��list�������img
			mImgList.add(img);

			// ��̬��ӵ�
			View point = new View(this);
			point.setBackgroundResource(R.drawable.guide_point_normal);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);// 10px�ĵ�
			if (i != 0)
			{
				params.leftMargin = 10;// �����֮��ļ��
			}
			mPointContainer.addView(point, params);
		}

		// ��viewPager����--->adapter--->list
		adapter = new GuidePagerAdapter();
		mPager.setAdapter(adapter);
	}

	/**
	 * ��ʼ�������¼�
	 */
	private void initListenre()
	{
		// ��viewPager��������
		mPager.setOnPageChangeListener(this);

		// ��button��������,ʵ�ְ�ť�����ת�¼�
		mButStart.setOnClickListener(this);

		// ��viewTreeObserver��ͼ�Ĳ�νṹ��������
		// ��������֮��ľ���
		mSelectedPoint.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout()
			{
				// ��UI�������ָı�ʱ����
				if (mImgList == null) {
				return;
				}

				mSelectedPoint.getViewTreeObserver().removeOnGlobalLayoutListener(this);

				mSpace = mPointContainer.getChildAt(1).getLeft() - mPointContainer.getChildAt(0).getLeft();
			}
		});
	}

	/**
	 * �Զ�����������
	 */
	class GuidePagerAdapter extends PagerAdapter
	{

		/**
		 * ������ͼҳ�������
		 */
		@Override
		public int getCount()
		{
			if (mImgList != null) { return mImgList.size(); }
			return 0;
		}

		@Override
		public boolean isViewFromObject(View view, Object object)
		{
			return view == object;
		}

		/**
		 * �൱��getView����������һ����ͼҳ��
		 */
		@Override
		public Object instantiateItem(ViewGroup container, int position)
		{
			ImageView iv = mImgList.get(position);

			// ��ӵ�viewPager��,��������Ч����һ����
			// mPager.addView(iv);
			container.addView(iv);

			// ��Ҫ���ص�����ʾ��imageView

			return iv;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object)
		{
			// ��viewPager���Ƴ�imageView
			// ImageView iv = mImgList.get(position);
			// mPager.addView(iv);
			container.removeView((View) object);
		}

	}

	/**
	 * ��viewPager���ڻ���ʱ�Ļص�
	 * 
	 * @position:��ǰ������ҳ��
	 * @positionOffset:ָ���ǰٷֱ�
	 * @positionOffsetPixels:ʵ�ʻ����ľ���(pxֵ)
	 */
	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
	{
		// 1.ȥ�Ի����ĵ�������
		// 2.����marginleft
		RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) mSelectedPoint.getLayoutParams();
		// ֵ������
		params.leftMargin = (int) (mSpace * position + mSpace * positionOffset + 0.5f);// ��������

		mSelectedPoint.setLayoutParams(params);
	}

	/**
	 * ��viewPagerĳ��ҳ��ѡ��ʱ�Ļص�
	 * 
	 * @position:��ǰѡ�е�λ��
	 */
	@Override
	public void onPageSelected(int position)
	{
		// if (position == mImgList.size() - 1)
		// {
		// // ��ʾbutton
		// btn_start.setVisibility(View.VISIBLE);
		// }
		// else
		// {
		// // ����button
		// btn_start.setVisibility(View.GONE);
		// }

		// �Ż���Ĵ���
		mButStart.setVisibility(position == mImgList.size() - 1 ? View.VISIBLE : View.GONE);
	}

	/**
	 * ��viewPager�Ļ���״̬�ı�ʱ�Ļص�
	 * 
	 * @state:״ֵ̬
	 */
	@Override
	public void onPageScrollStateChanged(int state)
	{

	}

	/**
	 * ��ť����¼�
	 */
	@Override
	public void onClick(View view)
	{
		if (view == mButStart)
		{
			// ��ת��������
			gotoMain();
		}
	}

	/**
	 * ��ת����������������
	 */
	private void gotoMain()
	{
		// �����Ѿ����ǵ�һ�ε�¼
		CacheUtils.setBoolean(this, WelcomeUI.KEY_IS_FIRST, false);

		Intent intent = new Intent(GuideUI.this, MainUI.class);
		startActivity(intent);

		finish();
	}
}
