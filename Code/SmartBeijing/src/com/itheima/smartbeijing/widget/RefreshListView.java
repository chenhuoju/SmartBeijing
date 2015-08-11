package com.itheima.smartbeijing.widget;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.itheima.smartbeijing.R;

/**
 * @����:com.itheima.smartbeijing.widget
 * @����:RefreshListView
 * @����:�»��
 * @ʱ��:2015-8-10 ����9:56:54
 * 
 * @����:����ˢ�£���������
 * 
 * @SVN�汾��:$Rev$
 * @������:$Author$
 * @��������:TODO
 * 
 */
public class RefreshListView extends ListView implements OnScrollListener
{

	private static final String	TAG						= "RefreshListView";
	private static final int	STATE_PULL_DOWN_REFRESH	= 0;						// ����ˢ��״̬
	private static final int	STATE_RELEASE_REFRESH	= 1;						// �ͷ�ˢ��״̬
	private static final int	STATE_RELEASEING		= 2;						// ����ˢ��״̬

	private LinearLayout		mHeaderLayout;										// ����ˢ�µ�view���Զ����headerView
	private View				mCustomHeaderView;									// �Զ����headerView
	private View				mRefreshView;										// ˢ�µ�view
	private int					mRefreshViewHeight;								// ˢ��view�ĸ߶�

	private float				mDownY;											// ��ʼλ�õ�����
	
	private int					mCurrentState			= STATE_PULL_DOWN_REFRESH;	// ��ǰ״̬��Ĭ��ֵ

	private ProgressBar			mPBar;												// ����
	private ImageView			mIvArrow;											// ��ͷ
	private TextView			mTvState;											// ״̬��ʾ
	private TextView			mTvUpdateTime;										// ʱ����ʾ

	private View				mFooterLayout;										// �ײ����ظ���Ĳ���
	private int					mFooterHeight;										// �ײ����صĸ߶�
	private static boolean		isLoadingMore			= false;					// ����Ƿ���ظ���

	private RotateAnimation		mDownToUpAnimation;								// ���ͷ�ˢ��׼����
	private RotateAnimation		mUpToDownAnimation;								// ������ˢ��׼����
	private static final long	DURATION				= 300;						// ��������ʱ��

	public RefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);

		// ����ͷ����
		initHeaderLayout();
		// ����β����
		initFooterLayout();

		initAnimation();
	}

	public RefreshListView(Context context) {
		super(context);

		// ����ͷ����
		initHeaderLayout();
		// ����β����
		initFooterLayout();

		initAnimation();
	}

	/**
	 * ��ʼ��ͷ����
	 */
	private void initHeaderLayout()
	{
		// ����ͷ����
		mHeaderLayout = (LinearLayout) View.inflate(getContext(), R.layout.refresh_header_layout, null);

		// ��ӵ�listView��headerView��
		this.addHeaderView(mHeaderLayout);

		// ��Ҫ����ˢ�µ�view
		mRefreshView = mHeaderLayout.findViewById(R.id.refresh_header_refresh_part);
		mPBar = (ProgressBar) mHeaderLayout.findViewById(R.id.refresh_header_pBar);
		mIvArrow = (ImageView) mHeaderLayout.findViewById(R.id.refresh_header_arrow);
		mTvState = (TextView) mHeaderLayout.findViewById(R.id.refresh_header_tv_state);
		mTvUpdateTime = (TextView) mHeaderLayout.findViewById(R.id.refresh_header_tv_time);

		// ��ͷ��������PaddingTopΪ���������ؿؼ�
		mRefreshView.measure(0, 0);// ����ȥ�����ؼ��Ŀ��
		mRefreshViewHeight = mRefreshView.getMeasuredHeight();
		// Log.e(TAG, "�߶ȣ�" + mRefreshViewHeight);
		mHeaderLayout.setPadding(0, -mRefreshViewHeight, 0, 0);
	}

	/**
	 * ��ʼ��β����
	 * 
	 * @return
	 */
	private void initFooterLayout()
	{
		mFooterLayout = View.inflate(getContext(), R.layout.refresh_footer_layout, null);
		// ��ӵ�listView��footerView��
		this.addFooterView(mFooterLayout);
		// ����footerLayout
		mFooterLayout.measure(0, 0);
		mFooterHeight = mFooterLayout.getMeasuredHeight();
		mFooterLayout.setPadding(0, -mFooterHeight, 0, 0);
		// ���õ�listView����ʱ�ļ���
		this.setOnScrollListener(this);
	}

	/**
	 * ��ʼ������
	 */
	private void initAnimation()
	{
		mDownToUpAnimation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		mDownToUpAnimation.setDuration(DURATION);// ���ö�������ʱ��
		mDownToUpAnimation.setFillEnabled(true);
		mDownToUpAnimation.setFillAfter(true);// ���ֶ���������״̬

		mUpToDownAnimation = new RotateAnimation(180, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		mUpToDownAnimation.setDuration(DURATION);// ���ö�������ʱ��
		mUpToDownAnimation.setFillEnabled(true);
		mUpToDownAnimation.setFillAfter(true);// ���ֶ���������״̬
	}

	/**
	 * �û��Զ���headerView����
	 * 
	 * @param headerView
	 */
	public void addCustomHeaderView(View headerView)
	{
		this.mCustomHeaderView = headerView;
		mHeaderLayout.addView(headerView);
	}

	/**
	 * ��������Ļ�¼�����
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev)
	{
		switch (ev.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				mDownY = ev.getY();

				break;
			case MotionEvent.ACTION_MOVE:
				float moveY = ev.getY();

				float diffY = moveY - mDownY;

				// �����ǰ״̬������ˢ�£����ǾͲ�������
				if (mCurrentState == STATE_RELEASEING)
				{
					break;
				}

				// ���listView������
				int[] listViewLocation = new int[2];// 0:x 1:y
				getLocationOnScreen(listViewLocation);
				Log.e(TAG, "listView y:" + listViewLocation[1]);

				int[] customLocation = new int[2];
				mCustomHeaderView.getLocationOnScreen(customLocation);
				Log.e(TAG, "mCustomHeaderView y:" + customLocation[1]);

				if (customLocation[1] < listViewLocation[1])
				{
					// ����Ӧ�Լ����¼�
					break;
				}

				// diffY>0:����
				// diffY<0:s����
				// �����һitem�ɼ��ǣ��ſ������� && ������
				// int position = getFirstVisiblePosition();
				if (getFirstVisiblePosition() == 0 && diffY > 0)
				{
					// ��ͷ��������PaddingTop
					int hiddenHeight = (int) (mRefreshViewHeight - diffY + 0.5f);
					mHeaderLayout.setPadding(0, -hiddenHeight, 0, 0);

					if (diffY < mRefreshViewHeight && mCurrentState == STATE_RELEASE_REFRESH)
					{
						// diffY<mRefreshViewHeight:����ˢ��
						// ����״̬
						mCurrentState = STATE_PULL_DOWN_REFRESH;

						// UI����
						// Log.e(TAG, "����ˢ��");
						refreshUI();
					}
					else if (diffY >= mRefreshViewHeight && mCurrentState == STATE_PULL_DOWN_REFRESH)
					{
						// diffY>mRefreshViewHeight:�ͷ�ˢ��
						mCurrentState = STATE_RELEASE_REFRESH;

						// UI����
						// Log.e(TAG, "�ͷ�ˢ��");
						refreshUI();
					}
					// ��Ҫ�Լ���Ӧtouch
					return true;
				}
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				mDownY = 0;

				// �ͷź�Ĳ���
				if (mCurrentState == STATE_PULL_DOWN_REFRESH)
				{
					// ���������ˢ��״̬��������ˢ�µ�view
					mHeaderLayout.setPadding(0, -mRefreshViewHeight, 0, 0);
				}
				else if (mCurrentState == STATE_RELEASE_REFRESH)
				{
					// ������ͷ�ˢ��״̬���û���ϣ��ˢ������-->��������ˢ��״̬
					mCurrentState = STATE_RELEASEING;

					// ����PaddingTopΪ0

					mHeaderLayout.setPadding(0, 0, 0, 0);

					// UI����
					refreshUI();

					// ֪ͨ�����ߣ����ڴ�������ˢ��״̬
					if (listener != null)
					{
						listener.onRefreshing();
					}
				}
				break;
			default:
				break;
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * UI���·���
	 */
	private void refreshUI()
	{
		switch (mCurrentState)
		{
			case STATE_PULL_DOWN_REFRESH:// ����ˢ��
				// 1.��ͷҪ��ʾ������������
				mIvArrow.setVisibility(View.VISIBLE);
				mPBar.setVisibility(View.GONE);
				// 2.�ı�״̬��ʾ
				mTvState.setText("����ˢ��");
				// 3.��ͷ����
				mIvArrow.startAnimation(mUpToDownAnimation);
				break;
			case STATE_RELEASE_REFRESH:// �ɿ�ˢ��
				// 1.��ͷҪ��ʾ������������
				mIvArrow.setVisibility(View.VISIBLE);
				mPBar.setVisibility(View.GONE);
				// 2.�ı�״̬��ʾ
				mTvState.setText("�ɿ�ˢ��");
				// 3.��ͷ����
				mIvArrow.startAnimation(mDownToUpAnimation);
				break;
			case STATE_RELEASEING:// ����ˢ��
				// ��ն���
				mIvArrow.clearAnimation();
				// 1.��ͷҪ���أ���������ʾ
				mIvArrow.setVisibility(View.GONE);
				mPBar.setVisibility(View.VISIBLE);
				// 2.�ı�״̬��ʾ
				mTvState.setText("����ˢ��");
				break;
			default:
				break;
		}
	}

	/**
	 * ��֪listViewˢ�����
	 */
	public void refreshFinish()
	{
		if (isLoadingMore)
		{
			// ��������

			// ���ؼ��ظ����view
			mFooterLayout.setPadding(0, -mFooterHeight, 0, 0);

			isLoadingMore = false;
		}
		else
		{
			// ����ˢ��
			Log.e(TAG, "ˢ�½���");

			// ���õ�ǰ����ʱ��
			mTvUpdateTime.setText(getCurrentTimeString());

			// ����ˢ�µ�view
			mHeaderLayout.setPadding(0, -mRefreshViewHeight, 0, 0);

			// ״̬����
			mCurrentState = STATE_PULL_DOWN_REFRESH;

			// UI����
			refreshUI();
		}
	}

	private OnRefreshListener	listener;

	public void setOnRefreshListener(OnRefreshListener listener)
	{
		this.listener = listener;
	}

	/**
	 * ����ص��ӿڣ������涨��ص�����
	 * 
	 * �ص������������Լ�������ã����������������ʹ��
	 */
	public interface OnRefreshListener
	{
		/**
		 * ����ˢ��ʱ�Ļص�
		 */
		void onRefreshing();

		/**
		 * ���ظ���ʱ�Ļص�
		 */
		void onLoadingMore();
	}

	/**
	 * ��ȡ��ǰʱ��
	 * 
	 * @return
	 */
	private String getCurrentTimeString()
	{
		long time = System.currentTimeMillis();

		// ����ʱ����ʾ��ʽ
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		return date.format(new Date(time));

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState)
	{
		// ���һ���ɼ���ʱ��
		int lastVisiblePosition = getLastVisiblePosition();
		if (lastVisiblePosition == getAdapter().getCount() - 1)
		{
			if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
				|| scrollState == OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
			{
				if (!isLoadingMore)
				{
					// �������˵ײ�
					Log.e(TAG, "�������ײ�");

					// UI����
					mFooterLayout.setPadding(0, 0, 0, 0);

					// �Զ�Ĭ��ѡ��i
					setSelection(getAdapter().getCount());

					// �ǻ������ײ�
					isLoadingMore = true;

					// ֪ͨ�����ߣ�״̬�Ѿ��仯��,���ڴ��ڼ��ظ���״̬
					if (listener != null)
					{
						listener.onLoadingMore();
					}
				}
			}
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
	{

	}
}
