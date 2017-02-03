package cn.aaron.ablog.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.aaron.ablog.base.impl.BasePage;

/**
* TODO
* @author Aaron
* @date 2017年2月3日
*/
@Controller
public class ContentAction extends BasePage {

	private Logger log = Logger.getLogger(ContentAction.class);
	
	@RequestMapping("/p/{name}")
	public String printContent(HttpServletRequest request,@PathVariable("name") String name){
		return "printContent";
	}
}
