package cn.aaron.ablog.base.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.aaron.ablog.base.Page;
import cn.aaron.ablog.obj.UserObj;

/**
 *TODO
 *@author Aaron
 *@date 2016年9月2日
 */
public class BasePage implements Page {
	
	public static final String USER_SESSION = "__user";
	public static final String ERROR_MSG = "__errMsg";

	@Override
	public boolean checkPermission(HttpServletRequest request,HttpServletResponse response) {
		UserObj userObj = (UserObj) request.getSession().getAttribute(USER_SESSION);
		return userObj!=null;
	}
	/**
	 * 设置errorMsg
	 * @param request
	 * @param errMsg
	 */
	public void setErrMsg(HttpServletRequest request,String errMsg){
		request.setAttribute(ERROR_MSG, errMsg);
	}

}
