package cn.aaron.ablog.dao.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.utils.Pagination;

/**
*TODO
*@author Aaron
*@date 2017年2月3日
*/
public abstract class BaseDao<T,PK extends Serializable>{

	private Logger log = Logger.getLogger(BaseDao.class);

	private SqlSessionTemplate sqlSessionTemplate;

	private String nameSpace;
	
	@Autowired
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	public SqlSession getSqlSession() {
		return sqlSessionTemplate;
	}

	public String getNameSpace() {
		return nameSpace;
	}

	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}
	
	public List<T> find(Map<String, Object> whereMap,Pagination pagination) {
		SqlSession session = getSqlSession();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("whereMap", whereMap);
		if(pagination!=null){
			paraMap.put("pagination", pagination);
		}
		return session.selectList(getNameSpace() + ".find", paraMap);
	}
	
	public List<T> find(Map<String, Object> whereMap) {
		return find(whereMap, null);
	}
	
	public int update(T t) {
		return getSqlSession().update(getNameSpace()+".update", t);
	}
	
	public T get(PK pk) {
		return getSqlSession().selectOne(getNameSpace()+".getByPK", pk);
	}
	
	public int insert(T t) {
		return getSqlSession().insert(getNameSpace()+".insert", t);
	}
	
	public int delete(PK pk) {
		return getSqlSession().delete(getNameSpace()+".delete", pk);
	}
	
}
