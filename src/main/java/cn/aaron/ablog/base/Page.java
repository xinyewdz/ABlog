package cn.aaron.ablog.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *TODO
 *@author Aaron
 *@date 2016年8月16日
 */
public interface Page {
	
	boolean checkPermission(HttpServletRequest request,HttpServletResponse response);

}
