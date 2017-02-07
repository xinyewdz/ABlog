package cn.aaron.ablog.dao;

import org.springframework.stereotype.Repository;

import cn.aaron.ablog.dao.base.BaseDao;
import cn.aaron.ablog.obj.UserObj;

/**
*TODO
*@author Aaron
*@date 2017年2月7日
*/
@Repository
public class UserDao extends BaseDao<UserObj, Long> {
	
	public UserDao(){
		setNameSpace("mapper.UserObjMapper");
	}

}
