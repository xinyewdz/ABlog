package cn.aaron.ablog.dao;

import org.springframework.stereotype.Repository;

import cn.aaron.ablog.dao.base.BaseDao;
import cn.aaron.ablog.obj.BlogCommentObj;

/**
*TODO
*@author Aaron
*@date 2017年2月15日
*/
@Repository
public class BlogCommentDao extends BaseDao<BlogCommentObj, Long> {
	
	public BlogCommentDao(){
		setNameSpace("mapper.BlogCommentObjMapper");
	}
	
}
