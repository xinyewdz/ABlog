package cn.aaron.ablog.dao.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
*TODO
*@author Aaron
*@date 2017年2月3日
*/
public abstract class BaseDao<T,PK extends Serializable> {

	private Logger log = Logger.getLogger(BaseDao.class);

	private SqlSessionFactory sqlSessionFactory;

	private String nameSpace;
	
	@Autowired
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	public SqlSession getSqlSession() {
		return sqlSessionFactory.openSession();
	}

	public String getNameSpace() {
		return nameSpace;
	}

	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}
	
	public List<T> find(Map<String, Object> map) {
		SqlSession session = getSqlSession();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("whereMap", map);
		return session.selectList(getNameSpace() + ".find", paraMap);
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