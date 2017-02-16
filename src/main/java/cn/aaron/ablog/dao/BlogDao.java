package cn.aaron.ablog.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.utils.Pagination;

import cn.aaron.ablog.dao.base.BaseDao;
import cn.aaron.ablog.obj.BlogCommentObj;
import cn.aaron.ablog.obj.BlogObj;
import cn.aaron.ablog.obj.BlogStatisticsObj;

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
	
	public List<BlogObj> listBlogWithContent(Map<String, Object> whereMap,Pagination pagination){
		if(whereMap==null){
			whereMap = new HashMap<String,Object>();
		}
		whereMap.put("pagination", pagination);
		return getSqlSession().selectList(getNameSpace()+".findWithContent",whereMap);
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
	
	public int insertStatistics(BlogStatisticsObj obj){
		return getSqlSession().insert(getNameSpace()+".insertStatistics",obj);
	}
	
	public int updateViewStatistics(Integer viewCount,Long blogId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("viewCount", viewCount);
		map.put("blogId", blogId);
		return getSqlSession().update(getNameSpace()+".updateViewStatistics",map);
	}
	
	public int updateCommentStatistics(Integer commentCount,Long blogId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("commentCount", commentCount);
		map.put("blogId", blogId);
		return getSqlSession().update(getNameSpace()+".updateCommentStatistics",map);
	}
	
	public List<BlogStatisticsObj> listStatistics(Long[] blogIds){
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("blogIds", blogIds);
		return getSqlSession().selectList(getNameSpace()+".findStatistics",map);
	}
	
	public BlogStatisticsObj getStatistics(Long blogId){
		List<BlogStatisticsObj> list = listStatistics(new Long[]{blogId});
		BlogStatisticsObj obj = null;
		if(list!=null&&list.size()>0){
			obj = list.get(0);
		}
		return obj;
	}
	
}
