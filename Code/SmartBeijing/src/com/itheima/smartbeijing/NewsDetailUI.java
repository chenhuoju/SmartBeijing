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
 * @包名:com.itheima.smartbeijing
 * @类名:NewsDetailUI
 * @作者:陈火炬
 * @时间:2015-8-11 下午8:46:32
 * 
 * @描述:新闻详情页面
 * 
 * @SVN版本号:$Rev$
 * @更新人:$Author$
 * @更新描述:TODO
 * 
 */
@SuppressWarnings("deprecation")
public class NewsDetailUI extends Activity implements OnClickListener
{
	private static final int		LARGEST			= 0;				// 超大号字体
	private static final int		LARGER			= 1;				// 大号字体
	private static final int		NORMAL			= 2;				// 正常字体
	private static final int		SMALLER			= 3;				// 小号字体
	private static final int		SMALLEST		= 4;				// 超小号字体

	public static final String		KEY_URL			= "url";

	protected static final String	TAG				= "NewsDetailUI";

	protected static final String	KEY_TEXT_SIZE	= "text_size";

	private WebView					mWebView;
	private ProgressBar				mPBar;
	private ImageButton				mIvBack;							// 回退按钮
	private ImageButton				mIvTextSize;						// 文本改变按钮
	private ImageButton				mIvShare;							// 分享按钮

	private int						mCurrentItem	= NORMAL;			// 设置当前选中的item,默认为2,正常值

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// 初始化UI的显示
		initView();
	}

	/**
	 * 初始化界面
	 */
	private void initView()
	{
		// 去掉标题title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.detail);

		mIvBack = (ImageButton) findViewById(R.id.title_bar_icon_back);
		mIvTextSize = (ImageButton) findViewById(R.id.title_bar_icon_textsize);
		mIvShare = (ImageButton) findViewById(R.id.title_bar_icon_share);

		mWebView = (WebView) findViewById(R.id.detail_webView);
		mPBar = (ProgressBar) findViewById(R.id.detail_pBar);

		// 设置隐藏和显示
		findViewById(R.id.title_bar_tv_title).setVisibility(View.GONE);
		findViewById(R.id.title_bar_icon_menu).setVisibility(View.GONE);
		mIvBack.setVisibility(View.VISIBLE);
		mIvTextSize.setVisibility(View.VISIBLE);
		mIvShare.setVisibility(View.VISIBLE);

		// 设置文本界面的按钮监听事件
		mIvBack.setOnClickListener(this);
		mIvTextSize.setOnClickListener(this);
		mIvShare.setOnClickListener(this);

		// 给webView加载数据
		String url = getIntent().getStringExtra(KEY_URL);
		// String url = "http://www.itheima.com";
		mWebView.loadUrl(url);

		// 给webView设置参数
		WebSettings settings = mWebView.getSettings();// 获取webView的设置
		settings.setJavaScriptEnabled(true);// 设置js是否可用
		settings.setBuiltInZoomControls(true);// 设置放大和缩小
		settings.setUseWideViewPort(true);// 设置手势的放大和缩小

		// 获取缓存,然后设置文本字体
		String i = CacheUtils.getString(this, KEY_TEXT_SIZE);
		if (!TextUtils.isEmpty(i))
		{
			mCurrentItem = Integer.parseInt(i);
			initsetTextSize();
		}

		// 设置监听
		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url)
			{
				// 数据加载完成时的调用
				mPBar.setVisibility(View.GONE);
			}
		});

		// google的chrome
		mWebView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress)
			{
				// 进度显示
				Log.e(TAG, "当前进度显示：" + newProgress);
			}
		});
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.title_bar_icon_back:// 回退
				doBack();
				break;
			case R.id.title_bar_icon_textsize:// 文本改变
				doTextSizeChange();
				break;
			case R.id.title_bar_icon_share:// 分享
				doShare();
				break;
			default:
				break;
		}

	}

	/**
	 * 处理分享按钮事件的方法
	 */
	private void doShare()
	{
		// TODO:
		ShareUtils.showShare(this);
	}

	/**
	 * 处理文本改变按钮事件的方法
	 */
	private void doTextSizeChange()
	{
		// 1.弹出dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("设置显示字体大小");// 设置title

		String[] items = new String[] { "超大号字体", "大号字体", "正常字体", "小号字体", "超小号字体" };

		builder.setSingleChoiceItems(items, mCurrentItem, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				// 设置赋值
				mCurrentItem = which;
			}
		});

		// 设置确定按钮
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();

				// 存储当前的textsize值
				CacheUtils.setString(NewsDetailUI.this, KEY_TEXT_SIZE, mCurrentItem + "");

				// UI改变
				initsetTextSize();
			}
		});

		// 设置取消按钮
		builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {

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
	 * 处理回退按钮事件的方法
	 */
	private void doBack()
	{
		finish();
	}

	/**
	 * 初始化设置文本大小
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
		// 设置textSize
		mWebView.getSettings().setTextSize(ts);
		// mWebView.getSettings().setTextZoom(0);
	}
}
