package cn.aaron.ablog.action;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.utils.FileUtil;
import org.utils.HttpRequestUtil;
import org.utils.NoneUtil;
import org.utils.Pagination;
import org.utils.RequestParameterException;

import cn.aaron.ablog.action.vo.BlogVO;
import cn.aaron.ablog.base.impl.BasePage;
import cn.aaron.ablog.obj.BlogCommentObj;
import cn.aaron.ablog.obj.BlogObj;
import cn.aaron.ablog.obj.BlogStatisticsObj;
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
		BlogObj obj = blogService.get(id, true);
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
		BlogObj obj = blogService.get(id, false);
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
	
	@RequestMapping("/p/{id}")
	public String printContent(HttpServletRequest request,@PathVariable("id") String id){
		log.info("print blog content.id="+id);
		Long _id = -1l;
		if(!NoneUtil.isBlank(id)){
			try {
				_id = Long.parseLong(id);
			} catch (NumberFormatException e) {
				log.error("parse id error", e);
			}
		}
		String page = "/printContent";
		if(_id<0){
			setErrMsg(request, "请检查内容地址是否正确!");
			return page;
		}
		BlogObj blog = blogService.get(_id, false);
		log.info("find blog success.id="+_id);
		if(blog==null){
			setErrMsg(request, "请求的内容不存在!");
			return page;
		}
		blogService.updateViewStatistics(_id, 1);
		String path = blogService.getBlogHtmlPath(_id);
		try {
			byte[] contentBuff = FileUtil.readFile(path);
			if(contentBuff==null){
				log.info("can't find blog html,will create it now.blogid="+_id);
				//生成markdown
				Parser parser = Parser.builder().build();
				blog = blogService.get(_id, true);
				Node document = parser.parse(blog.getContent());
				HtmlRenderer renderer = HtmlRenderer.builder().build();
				String htmlContent = renderer.render(document);
				try {
					byte[] content = htmlContent.getBytes("UTF-8");
					FileUtil.writeFile(path, content);
					log.info("write html file success.id="+id);
					contentBuff = content;
				} catch (IOException e) {
					log.error("write html file error.", e);
				}
			}
			//blog statistics
			List<BlogStatisticsObj> statisticsList = blogService.listStatistics(new Long[]{_id});
			BlogVO vo = new BlogVO();
			vo.setId(blog.getId());
			vo.setTitle(blog.getTitle());
			vo.setTags(blog.getTags());
			vo.setContent(blog.getContent());
			vo.setCreatedTime(blog.getCreatedTime());
			vo.setUpdatedTime(blog.getUpdatedTime());
			if(!NoneUtil.isEmpty(statisticsList)){
				BlogStatisticsObj statisticsObj = statisticsList.get(0);
				vo.setViewCount(statisticsObj.getViewCount());
				vo.setCommentCount(statisticsObj.getCommentCount());
			}
			String blogHtmlContent = new String(contentBuff,"UTF-8");
			request.setAttribute("blogHtml", blogHtmlContent);
			request.setAttribute("blog", vo);
			//blog comment
			Pagination pagination = new Pagination();
			pagination.setPageSize(10);
			pagination.setRowStart(0);
			List<BlogCommentObj> commentList = blogService.listComment(_id, pagination);
			request.setAttribute("commentList", commentList);
			log.info("find blog content success.id="+_id);
		} catch (IOException e) {
			log.error("error", e);
		}
		return "/printContent";
	}
	
}
