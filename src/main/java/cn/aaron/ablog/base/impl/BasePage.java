package cn.aaron.ablog.base.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.aaron.ablog.base.Page;

/**
 *TODO
 *@author Aaron
 *@date 2016年9月2日
 */
public class BasePage implements Page {
	
	public static final String SESSION_USER = "__user";

	@Override
	public boolean checkPermission(HttpServletRequest request,HttpServletResponse response) {
		return true;
	}

}
