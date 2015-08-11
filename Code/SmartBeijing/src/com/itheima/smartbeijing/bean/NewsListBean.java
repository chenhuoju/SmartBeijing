package com.itheima.smartbeijing.bean;

import java.util.List;

/**
 * @包名:com.itheima.smartbeijing.bean
 * @类名:NewsListBean
 * @作者:陈火炬
 * @时间:2015-8-10 上午11:32:13
 * 
 * 
 * @描述:newslist页面的bean
 * 
 * @SVN版本号:$Rev: 24 $
 * @更新人:$Author: chj $
 * @更新描述:TODO
 * 
 */
public class NewsListBean
{
	public NewsListPagerBean	data;
	public int					retcode;

	public class NewsListPagerBean
	{
		public String							countcommenturl;
		public String							more;
		public List<NewsListPagerNewsBean>		news;
		public String							title;
		public List<NewsListPagerTopicBean>		topic;
		public List<NewsListPagerTopnewsBean>	topnews;
	}

	public class NewsListPagerNewsBean
	{
		public boolean	comment;
		public String	commentlist;
		public String	commenturl;
		public long		id;
		public String	listimage;
		public String	pubdate;
		public String	title;
		public String	type;
		public String	url;
	}

	public class NewsListPagerTopicBean
	{
		public String	description;
		public long		id;
		public String	listimage;
		public int		sort;
		public String	title;
		public String	url;
	}

	public class NewsListPagerTopnewsBean
	{
		public boolean	comment;
		public String	commentlist;
		public String	commenturl;
		public long		id;
		public String	pubdate;
		public String	title;
		public String	topimage;
		public String	type;
		public String	url;
	}
}
