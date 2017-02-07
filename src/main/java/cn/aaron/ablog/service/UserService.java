package cn.aaron.ablog.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.aaron.ablog.dao.UserDao;
import cn.aaron.ablog.obj.UserObj;

/**
*TODO
*@author Aaron
*@date 2017年2月7日
*/
@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;

	public UserObj find(String login){
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("login", login);
		UserObj obj = userDao.findOne(whereMap);
		return obj;
	}
	
}
