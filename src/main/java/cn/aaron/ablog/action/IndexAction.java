package cn.aaron.ablog.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.utils.HttpRequestUtil;
import org.utils.Pagination;
import org.utils.RequestParameterException;

import cn.aaron.ablog.base.impl.BasePage;
import cn.aaron.ablog.obj.BlogObj;
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
	public String index(HttpServletRequest request) throws RequestParameterException{
		int _rowStart = HttpRequestUtil.getInt(request, "_rowStart",0);
		Pagination pagination = new Pagination();
		pagination.setSortName("updated_time").setSortOrder(Pagination.SORT_ORDER_DESC);
		pagination.setRowStart(_rowStart).setPageSize(10);
		List<BlogObj> list = blogService.findBlogList(pagination);
		long count = blogService.findCount();
		request.setAttribute("count", count);
		request.setAttribute("blogList", list);
		return "/index";
	}
	
}
