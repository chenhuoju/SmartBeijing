package com.itheima.smartbeijing.utils;

/**
 * @包名:com.itheima.smartbeijing.utils
 * @类名:Constans
 * @作者:陈火炬
 * @时间:2015-8-7 下午8:51:28
 * 
 * 
 * @描述:TODO
 * 
 * @SVN版本号:$Rev: 36 $
 * @更新人:$Author: chj $
 * @更新描述:TODO
 * 
 */
public interface Constans
{
	// 服务器的地址
	// 本机ip:192.168.89.1(49.122.47.185)，虚拟ip:10.0.2.2
	String	SERVER_URL		= "http://49.122.47.187:8080/zhbj";
	String	NEW_CENTER_URL	= SERVER_URL + "/categories.json";
	String	PHOTOS_URL	= SERVER_URL + "/photos/photos_1.json";
}
