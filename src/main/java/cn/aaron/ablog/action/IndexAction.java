package cn.aaron.ablog.action;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.aaron.ablog.base.impl.BasePage;
import cn.aaron.ablog.service.BlogService;

/**
*TODO
*@author Aaron
*@date 2017年2月3日
*/
@Controller
public class IndexAction extends BasePage {

	private static Logger log = Logger.getLogger(IndexAction.class);
	
	@Autowired
	private BlogService blogService;
	
	@RequestMapping("/")
	public String index(){
		blogService.find(1l, false);
		return "/index";
	}
}
