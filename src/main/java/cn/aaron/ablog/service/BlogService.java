package cn.aaron.ablog.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.utils.Pagination;

import cn.aaron.ablog.dao.BlogDao;
import cn.aaron.ablog.obj.BlogObj;

/**
*TODO
*@author Aaron
*@date 2017年2月3日
*/
@Service
public class BlogService {
	
	@Autowired
	private BlogDao blogDao;
	
	@Autowired
	private SystemPropertyService systemPropertyService;
	
	public List<BlogObj> findBlogList(Pagination pagination){
		return blogDao.find(null,pagination);
	}

	public BlogObj find(Long id,boolean withContent){
		BlogObj obj = null;
		if(withContent){
			obj = blogDao.getWithContent(id);
		}else{
			obj = blogDao.get(id);
		}
		return obj;
	}
	
	public long findCount(){
		long total = blogDao.findCount();
		return total;
	}

	public int save(BlogObj obj) {
		int count = 0;
		if(obj.getId()==null||obj.getId()<0){
			count = blogDao.insert(obj);
		}else{
			count = blogDao.updateWithContent(obj);
		}
		return count;
	}

	public String getBlogHtmlPath(Long id){
		return systemPropertyService.getProperty("blogHtml")+File.separator+id;
	}
	
}
