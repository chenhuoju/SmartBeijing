package com.itheima.smartbeijing.bean;

import java.util.List;

/**
 * @包名:com.itheima.smartbeijing.bean
 * @类名:NewsCenterBean
 * @作者:陈火炬
 * @时间:2015-8-8 上午9:10:03
 * 
 * 
 * @描述:新闻页面 网络返回的数据bean
 * 
 * @SVN版本号:$Rev: 13 $
 * @更新人:$Author: chj $
 * @更新描述:TODO
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
