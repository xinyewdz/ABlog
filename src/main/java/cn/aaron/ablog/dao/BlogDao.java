package cn.aaron.ablog.dao;

import org.springframework.stereotype.Repository;

import cn.aaron.ablog.dao.base.BaseDao;
import cn.aaron.ablog.obj.BlogObj;

/**
*TODO
*@author Aaron
*@date 2017年2月3日
*/
@Repository
public class BlogDao extends BaseDao<BlogObj, Long>{

	public BlogDao(){
		setNameSpace("mapper.BlogObjMapper");
	}
	
	public BlogObj getWithContent(Long id){
		return getSqlSession().selectOne(getNameSpace()+".getByPKWithContent", id);
	}
	
	public int updateWithContent(BlogObj obj){
		return getSqlSession().update(getNameSpace()+".updateWithContent", obj);
	}

	public long findCount() {
		return getSqlSession().selectOne(getNameSpace()+".findCount");
	}
	
}
