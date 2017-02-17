package cn.aaron.ablog.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.util.NoneUtil;
import org.util.Pagination;

import cn.aaron.ablog.dao.BlogCommentDao;
import cn.aaron.ablog.dao.BlogDao;
import cn.aaron.ablog.obj.BlogCommentObj;
import cn.aaron.ablog.obj.BlogObj;
import cn.aaron.ablog.obj.BlogStatisticsObj;

/**
*TODO
*@author Aaron
*@date 2017年2月3日
*/
@Service
public class BlogService {
	
	private Logger log = Logger.getLogger(BlogService.class);
	
	@Autowired
	private BlogDao blogDao;
	
	@Autowired
	private BlogCommentDao blogCommentDao;
	
	@Autowired
	private SystemPropertyService systemPropertyService;
	
	public List<BlogObj> listBlog(Pagination pagination,boolean withContent){
		List<BlogObj> list = null;
		if(withContent){
			list = blogDao.listBlogWithContent(null, pagination);
		}else{
			list = blogDao.find(null, pagination);
		}
		return list;
	}

	public BlogObj get(Long id,boolean withContent){
		BlogObj obj = null;
		if(withContent){
			obj = blogDao.getWithContent(id);
		}else{
			obj = blogDao.get(id);
		}
		return obj;
	}
	
	public Long findCount(){
		long total = blogDao.findCount();
		return total;
	}

	public Integer save(BlogObj obj) {
		if(obj==null){
			return 0;
		}
		int count = 0;
		if(obj.getId()==null||obj.getId()<0){
			count = blogDao.insert(obj);
			BlogStatisticsObj statisticsObj = new BlogStatisticsObj();
			statisticsObj.setBlogId(obj.getId());
			statisticsObj.setCommentCount(0);
			statisticsObj.setViewCount(0);
			blogDao.insertStatistics(statisticsObj);
		}else{
			count = blogDao.updateWithContent(obj);
		}
		return count;
	}

	public String getBlogHtmlPath(Long id){
		return systemPropertyService.getProperty("blogHtml")+File.separator+id;
	}
	
	public List<BlogStatisticsObj> listStatistics(Long[] blogIds){
		if(NoneUtil.isEmpty(blogIds)){
			log.info("listStatistics blogIds is null");
			return null;
		}
		return blogDao.listStatistics(blogIds);
	}
	
	public void updateViewStatistics(Long blogId,Integer view){
		if(blogId==null){
			return;
		}
		BlogStatisticsObj statisticsObj = blogDao.getStatistics(blogId);
		if(statisticsObj==null){
			statisticsObj = new BlogStatisticsObj();
			statisticsObj.setBlogId(blogId);
			statisticsObj.setViewCount(1);
			statisticsObj.setCommentCount(0);
			blogDao.insertStatistics(statisticsObj);
		}else{
			blogDao.updateViewStatistics(view, blogId);
		}
		log.info("updateViewStatistics success.blogId="+blogId);
	}
	
	public Integer saveComment(BlogCommentObj obj){
		int count = blogCommentDao.insert(obj);
		if(count==1){
			Long blogId = obj.getBlogId();
			BlogStatisticsObj statisticsObj = blogDao.getStatistics(blogId);
			if(statisticsObj==null){
				statisticsObj = new BlogStatisticsObj();
				statisticsObj.setBlogId(obj.getId());
				statisticsObj.setViewCount(0);
				statisticsObj.setCommentCount(1);
				blogDao.insertStatistics(statisticsObj);
			}else{
				blogDao.updateCommentStatistics(1, blogId);
			}
		}
		log.info("saveComment success.blogId="+obj.getBlogId());
		return count;
	}
	
	public List<BlogCommentObj> listComment(Long blogId,Pagination pagination){
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("blog_id", blogId);
		return blogCommentDao.find(whereMap,pagination);
	}
	
}
