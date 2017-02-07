package cn.aaron.ablog.action;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.utils.FileUtil;
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
		obj.setContent(content);
		int count = blogService.save(obj);
		if(count==0){
			setErrMsg(request, "保存失败");
			return "/blog/save";
		}else{
			log.info("doSave blog success.id="+id);
			id = obj.getId();
			//生成markdown
			Parser parser = Parser.builder().build();
			Node document = parser.parse(content);
			HtmlRenderer renderer = HtmlRenderer.builder().build();
			String htmlContent = renderer.render(document);
			String path = blogService.getBlogHtmlPath(id);
			try {
				FileUtil.writeFile(path, htmlContent.getBytes("UTF-8"));
				log.info("write html file success.id="+id);
			} catch (IOException e) {
				log.error("write html file error.", e);
			}
		}
		return "redirect:/p/"+id;
	}
	
}
