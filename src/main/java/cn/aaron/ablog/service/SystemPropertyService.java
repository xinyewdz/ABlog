package cn.aaron.ablog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.aaron.ablog.base.SystemProperty;

/**
*TODO
*@author Aaron
*@date 2017年2月4日
*/
@Service
public class SystemPropertyService {

	@Autowired
	private SystemProperty systemProperty;
	
	public String getProperty(String key){
		return systemProperty.getProperty(key);
	}
	
}
