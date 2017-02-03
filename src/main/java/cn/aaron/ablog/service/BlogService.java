package cn.aaron.ablog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public BlogObj find(Long id,boolean withContent){
		BlogObj obj = null;
		if(withContent){
			obj = blogDao.getWithContent(id);
		}else{
			obj = blogDao.get(id);
		}
		return obj;
	}
	
}
