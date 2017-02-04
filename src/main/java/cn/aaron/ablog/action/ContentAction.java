package cn.aaron.ablog.action;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.utils.FileUtil;
import org.utils.NoneUtil;

import cn.aaron.ablog.base.impl.BasePage;
import cn.aaron.ablog.obj.BlogObj;
import cn.aaron.ablog.service.BlogService;
import cn.aaron.ablog.service.SystemPropertyService;

/**
* TODO
* @author Aaron
* @date 2017年2月3日
*/
@Controller
public class ContentAction extends BasePage {

	private Logger log = Logger.getLogger(ContentAction.class);
	
	@Autowired
	private SystemPropertyService systemPropertiesService;
	
	@Autowired
	private BlogService blogService;
	
	public String getBlogHtmlPath(){
		return systemPropertiesService.getProperty("blogHtml");
	}
	
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
				if(blog!=null){
					String path = getBlogHtmlPath()+File.separator+id;
					try {
						byte[] contentBuff = FileUtil.readFile(path);
						if(contentBuff!=null){
							String blogHtmlContent = new String(contentBuff,"UTF-8");
							request.setAttribute("blogHtml", blogHtmlContent);
							request.setAttribute("blog", blog);
						}
					} catch (IOException e) {
						log.error("error", e);
					}
				}
			}
		}
		return "printContent";
	}
}
