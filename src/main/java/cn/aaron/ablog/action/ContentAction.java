package cn.aaron.ablog.action;

import java.io.IOException;

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
import org.utils.NoneUtil;

import cn.aaron.ablog.base.impl.BasePage;
import cn.aaron.ablog.obj.BlogObj;
import cn.aaron.ablog.service.BlogService;

/**
* TODO
* @author Aaron
* @date 2017年2月3日
*/
@Controller
public class ContentAction extends BasePage {

	private Logger log = Logger.getLogger(ContentAction.class);
	
	@Autowired
	private BlogService blogService;
	
	@RequestMapping("/p/{id}")
	public String printContent(HttpServletRequest request,@PathVariable("id") String id){
		log.info("print blog content.id="+id);
		if(!NoneUtil.isBlank(id)){
			Long _id = -1l;
			try {
				_id = Long.parseLong(id);
			} catch (NumberFormatException e) {
				log.error("parse id error", e);
			}
			if(_id>-1){
				BlogObj blog = blogService.find(_id, false);
				log.info("find blog success.id="+_id);
				if(blog!=null){
					String path = blogService.getBlogHtmlPath(_id);
					try {
						byte[] contentBuff = FileUtil.readFile(path);
						if(contentBuff==null){
							log.info("can't find blog html,will create it now.blogid="+_id);
							//生成markdown
							Parser parser = Parser.builder().build();
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
						String blogHtmlContent = new String(contentBuff,"UTF-8");
						request.setAttribute("blogHtml", blogHtmlContent);
						request.setAttribute("blog", blog);
						log.info("find blog content success.id="+_id);
					} catch (IOException e) {
						log.error("error", e);
					}
				}
			}
		}
		return "/printContent";
	}
	
}
