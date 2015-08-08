package com.itheima.smartbeijing.bean;

import java.util.List;

/**
 * @����:com.itheima.smartbeijing.bean
 * @����:NewsCenterBean
 * @����:�»��
 * @ʱ��:2015-8-8 ����9:10:03
 * 
 * 
 * @����:����ҳ�� ���緵�ص�����bean
 * 
 * @SVN�汾��:$Rev: 13 $
 * @������:$Author: chj $
 * @��������:TODO
 * 
 */
public class NewsCenterBean
{
	public List<NewsCenterMenuListBean>	data;
	public List<Long>					extend;
	public int							retcode;

	public class NewsCenterMenuListBean
	{
		public List<NewsCenterNewsItemBean>	children;
		public long							id;
		public String						title;
		public int							type;

		public String						url;
		public String						url1;

		public String						dayurl;
		public String						excurl;
		public String						weekurl;
	}

	public class NewsCenterNewsItemBean
	{
		public long		id;
		public String	title;
		public int		type;
		public String	url;
	}
}
