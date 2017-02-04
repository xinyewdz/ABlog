package cn.aaron.ablog.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.utils.HttpRequestUtil;
import org.utils.NoneUtil;
import org.utils.RequestParameterException;

import cn.aaron.ablog.base.impl.BasePage;
import cn.aaron.ablog.obj.BlogObj;
import cn.aaron.ablog.service.BlogService;

/**
*TODO
*@author Aaron
*@date 2017年2月4日
*/
@Controller
public class BlogAction extends BasePage {

	private Logger log = Logger.getLogger(BlogAction.class);
	@Autowired
	private BlogService blogService;
	
	@RequestMapping("/blog/save")
	public String save(HttpServletRequest request) throws RequestParameterException{
		Long id = HttpRequestUtil.getLong(request, "id");
		log.info("save blog.id="+id);
		BlogObj obj = blogService.find(id, true);
		request.setAttribute("blog", obj);
		return "/blog/save";
	}
	
	@RequestMapping("/blog/doSave")
	public String doSave(HttpServletRequest request) throws RequestParameterException{
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		if(NoneUtil.isBlank(title)||NoneUtil.isBlank(content)){
			request.setAttribute("errMsg", "请求参数错误");
			request.setAttribute("title", title);
			request.setAttribute("content", content);
			log.error("doSave blog error.request parameter error");
			return "/blog/save";
		}
		Long id = HttpRequestUtil.getLong(request, "id");
		log.info("doSave blog .id="+id);
		BlogObj obj = blogService.find(id, false);
		Date now = new Date();
		if(obj==null){
			obj = new BlogObj();
			obj.setCreatedTime(now);
			obj.setTags("normal");
		}
		obj.setUpdatedTime(now);
		obj.setTitle(title);
		obj.setFullContent(content);
		int count = blogService.save(obj);
		if(count==0){
			request.setAttribute("errMsg", "保存失败");
			return "/blog/save";
		}else{
			id = obj.getId();
		}
		return "redirect:/p/"+id;
	}
	
}
