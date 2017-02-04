package cn.aaron.ablog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.utils.NoneUtil;

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
			if(obj!=null){
				String content = obj.getContent();
				String content1 = obj.getContent1();
				if(!NoneUtil.isBlank(content1)){
					content +=content1;
				}
				obj.setFullContent(content);
			}
		}else{
			obj = blogDao.get(id);
		}
		return obj;
	}

	public int save(BlogObj obj) {
		String content = obj.getFullContent();
		int contentLen = content.length();
		if(contentLen>=BlogObj.MAX_CONTENT_LENGTH){
			obj.setContent(content.substring(0, BlogObj.MAX_CONTENT_LENGTH));
			obj.setContent1(content.substring(BlogObj.MAX_CONTENT_LENGTH, contentLen*2));
		}else{
			obj.setContent(content);
		}
		int count = 0;
		if(obj.getId()==null||obj.getId()<0){
			count = blogDao.insert(obj);
		}else{
			count = blogDao.updateWithContent(obj);
		}
		return count;
	}
	
}
