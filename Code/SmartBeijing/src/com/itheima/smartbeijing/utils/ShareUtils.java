package com.itheima.smartbeijing.utils;

import android.content.Context;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.itheima.smartbeijing.R;

/**
 * @����:com.itheima.smartbeijing.utils
 * @����:ShareUtils
 * @����:�»��
 * @ʱ��:2015-8-12 ����2:21:19
 * 
 * @����:TODO
 * 
 * @SVN�汾��:$Rev$
 * @������:$Author$
 * @��������:TODO
 * 
 */
public class ShareUtils
{
	public static void showShare(Context context)
	{
		ShareSDK.initSDK(context);
		OnekeyShare oks = new OnekeyShare();
		// �ر�sso��Ȩ
		oks.disableSSOWhenAuthorize();
		oks.setTitle(context.getString(R.string.share));
		oks.setText("���Ƿ����ı�");
		// oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/05/21/oESpJ78_533x800.jpg");
		oks.setImageUrl("http://pica.nipic.com/2008-03-28/200832810200350_2.jpg");
		// ��������GUI
		oks.show(context);

		// ShareSDK.initSDK(context);
		// OnekeyShare oks = new OnekeyShare();
		// // �ر�sso��Ȩ
		// oks.disableSSOWhenAuthorize();
		//
		// // ����ʱNotification��ͼ������� 2.5.9�Ժ�İ汾�����ô˷���
		// // oks.setNotification(R.drawable.ic_launcher,
		// // getString(R.string.app_name));
		// // title���⣬ӡ��ʼǡ����䡢��Ϣ��΢�š���������QQ�ռ�ʹ��
		// oks.setTitle(context.getString(R.string.share));
		// // titleUrl�Ǳ�����������ӣ�������������QQ�ռ�ʹ��
		// // oks.setTitleUrl("http://sharesdk.cn");
		// // text�Ƿ����ı�������ƽ̨����Ҫ����ֶ�
		// oks.setText("���Ƿ����ı�");
		// // imagePath��ͼƬ�ı���·����Linked-In�����ƽ̨��֧�ִ˲���
		// oks.setImagePath("/sdcard/test.jpg");// ȷ��SDcard������ڴ���ͼƬ
		// // url����΢�ţ��������Ѻ�����Ȧ����ʹ��
		// // oks.setUrl("http://sharesdk.cn");
		// // comment���Ҷ�������������ۣ�������������QQ�ռ�ʹ��
		// // oks.setComment("���ǲ��������ı�");
		// // site�Ƿ�������ݵ���վ���ƣ�����QQ�ռ�ʹ��
		// // oks.setSite(context.getString(R.string.app_name));
		// // siteUrl�Ƿ�������ݵ���վ��ַ������QQ�ռ�ʹ��
		// // oks.setSiteUrl("http://sharesdk.cn");
		//
		// // ��������GUI
		// oks.show(context);
	}
}
