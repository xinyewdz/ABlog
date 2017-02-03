package cn.aaron.ablog.base.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import cn.aaron.ablog.base.ApiPage;

/**
 *TODO
 *@author Aaron
 *@date 2016年8月16日
 */
@Component
public class BaseApiPage implements ApiPage {
	
	private Logger log = Logger.getLogger(BaseApiPage.class);
	
	@Override
	public boolean checkPermission(HttpServletRequest request,HttpServletResponse response) {
		return true;
	}

}
