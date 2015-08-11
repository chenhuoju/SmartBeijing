package com.itheima.smartbeijing.bean;

import java.util.List;

/**
 * @����:com.itheima.smartbeijing.bean
 * @����:NewsListBean
 * @����:�»��
 * @ʱ��:2015-8-10 ����11:32:13
 * 
 * 
 * @����:newslistҳ���bean
 * 
 * @SVN�汾��:$Rev: 24 $
 * @������:$Author: chj $
 * @��������:TODO
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
