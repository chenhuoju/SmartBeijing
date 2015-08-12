package com.itheima.smartbeijing;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.itheima.smartbeijing.utils.CacheUtils;
import com.itheima.smartbeijing.utils.ShareUtils;

/**
 * @����:com.itheima.smartbeijing
 * @����:NewsDetailUI
 * @����:�»��
 * @ʱ��:2015-8-11 ����8:46:32
 * 
 * @����:��������ҳ��
 * 
 * @SVN�汾��:$Rev$
 * @������:$Author$
 * @��������:TODO
 * 
 */
@SuppressWarnings("deprecation")
public class NewsDetailUI extends Activity implements OnClickListener
{
	private static final int		LARGEST			= 0;				// ���������
	private static final int		LARGER			= 1;				// �������
	private static final int		NORMAL			= 2;				// ��������
	private static final int		SMALLER			= 3;				// С������
	private static final int		SMALLEST		= 4;				// ��С������

	public static final String		KEY_URL			= "url";

	protected static final String	TAG				= "NewsDetailUI";

	protected static final String	KEY_TEXT_SIZE	= "text_size";

	private WebView					mWebView;
	private ProgressBar				mPBar;
	private ImageButton				mIvBack;							// ���˰�ť
	private ImageButton				mIvTextSize;						// �ı��ı䰴ť
	private ImageButton				mIvShare;							// ����ť

	private int						mCurrentItem	= NORMAL;			// ���õ�ǰѡ�е�item,Ĭ��Ϊ2,����ֵ

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// ��ʼ��UI����ʾ
		initView();
	}

	/**
	 * ��ʼ������
	 */
	private void initView()
	{
		// ȥ������title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.detail);

		mIvBack = (ImageButton) findViewById(R.id.title_bar_icon_back);
		mIvTextSize = (ImageButton) findViewById(R.id.title_bar_icon_textsize);
		mIvShare = (ImageButton) findViewById(R.id.title_bar_icon_share);

		mWebView = (WebView) findViewById(R.id.detail_webView);
		mPBar = (ProgressBar) findViewById(R.id.detail_pBar);

		// �������غ���ʾ
		findViewById(R.id.title_bar_tv_title).setVisibility(View.GONE);
		findViewById(R.id.title_bar_icon_menu).setVisibility(View.GONE);
		mIvBack.setVisibility(View.VISIBLE);
		mIvTextSize.setVisibility(View.VISIBLE);
		mIvShare.setVisibility(View.VISIBLE);

		// �����ı�����İ�ť�����¼�
		mIvBack.setOnClickListener(this);
		mIvTextSize.setOnClickListener(this);
		mIvShare.setOnClickListener(this);

		// ��webView��������
		String url = getIntent().getStringExtra(KEY_URL);
		// String url = "http://www.itheima.com";
		mWebView.loadUrl(url);

		// ��webView���ò���
		WebSettings settings = mWebView.getSettings();// ��ȡwebView������
		settings.setJavaScriptEnabled(true);// ����js�Ƿ����
		settings.setBuiltInZoomControls(true);// ���÷Ŵ����С
		settings.setUseWideViewPort(true);// �������ƵķŴ����С

		// ��ȡ����,Ȼ�������ı�����
		String i = CacheUtils.getString(this, KEY_TEXT_SIZE);
		if (!TextUtils.isEmpty(i))
		{
			mCurrentItem = Integer.parseInt(i);
			initsetTextSize();
		}

		// ���ü���
		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url)
			{
				// ���ݼ������ʱ�ĵ���
				mPBar.setVisibility(View.GONE);
			}
		});

		// google��chrome
		mWebView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress)
			{
				// ������ʾ
				Log.e(TAG, "��ǰ������ʾ��" + newProgress);
			}
		});
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.title_bar_icon_back:// ����
				doBack();
				break;
			case R.id.title_bar_icon_textsize:// �ı��ı�
				doTextSizeChange();
				break;
			case R.id.title_bar_icon_share:// ����
				doShare();
				break;
			default:
				break;
		}

	}

	/**
	 * �������ť�¼��ķ���
	 */
	private void doShare()
	{
		// TODO:
		ShareUtils.showShare(this);
	}

	/**
	 * �����ı��ı䰴ť�¼��ķ���
	 */
	private void doTextSizeChange()
	{
		// 1.����dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("������ʾ�����С");// ����title

		String[] items = new String[] { "���������", "�������", "��������", "С������", "��С������" };

		builder.setSingleChoiceItems(items, mCurrentItem, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				// ���ø�ֵ
				mCurrentItem = which;
			}
		});

		// ����ȷ����ť
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();

				// �洢��ǰ��textsizeֵ
				CacheUtils.setString(NewsDetailUI.this, KEY_TEXT_SIZE, mCurrentItem + "");

				// UI�ı�
				initsetTextSize();
			}
		});

		// ����ȡ����ť
		builder.setNeutralButton("ȡ��", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				mCurrentItem = Integer.parseInt(CacheUtils.getString(NewsDetailUI.this, KEY_TEXT_SIZE));
			}
		});
		// show
		builder.show();
	}

	/**
	 * ������˰�ť�¼��ķ���
	 */
	private void doBack()
	{
		finish();
	}

	/**
	 * ��ʼ�������ı���С
	 */
	private void initsetTextSize()
	{
		TextSize ts = null;

		switch (mCurrentItem)
		{
			case LARGEST:
				ts = TextSize.LARGEST;
				break;
			case LARGER:
				ts = TextSize.LARGER;
				break;
			case NORMAL:
				ts = TextSize.NORMAL;
				break;
			case SMALLER:
				ts = TextSize.SMALLER;
				break;
			case SMALLEST:
				ts = TextSize.SMALLEST;
				break;
			default:
				break;
		}
		// ����textSize
		mWebView.getSettings().setTextSize(ts);
		// mWebView.getSettings().setTextZoom(0);
	}
}
