package cn.aaron.ablog.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.utils.ApiResponse;
import org.utils.ApiResponseCode;
import org.utils.HttpRequestUtil;
import org.utils.Pagination;
import org.utils.RequestParameterException;
import org.utils.gson.GsonUtil;

import cn.aaron.ablog.base.impl.BasePage;
import cn.aaron.ablog.obj.BlogCommentObj;
import cn.aaron.ablog.service.BlogService;

/**
*TODO
*@author Aaron
*@date 2017年2月15日
*/
@Controller
public class BlogCommentAction extends BasePage {
	
	@Override
	public boolean checkPermission(HttpServletRequest request, HttpServletResponse response) {
		return true;
	}
	
	private Logger log = Logger.getLogger(BlogCommentAction.class);
	@Autowired
	private BlogService blogService;
	
	@RequestMapping("/blog/comment/save")
	@ResponseBody
	public ApiResponse<Object> addComment(HttpServletRequest request) throws RequestParameterException{
		ApiResponse<Object> apiResponse = new ApiResponse<Object>();
		Long blogId = HttpRequestUtil.getLong(request, "blogId");
		String nickName = HttpRequestUtil.getString(request, "nickName");
		String email = HttpRequestUtil.getString(request, "email");
		String content = HttpRequestUtil.getString(request, "content");
		if(HttpRequestUtil.valideNull(blogId,nickName,content)){
			apiResponse.error(ApiResponseCode.request_parameter_error);
			return apiResponse;
		}
		if(content.length()>200){
			apiResponse.error("1000", "内容太长.");
			return apiResponse;
		}
		BlogCommentObj obj = new BlogCommentObj();
		obj.setBlogId(blogId);
		obj.setContent(content);
		obj.setEmail(email);
		obj.setNickName(nickName);
		Date now = new Date();
		obj.setCreatedTime(now);
		obj.setUpdatedTime(now);
		blogService.saveComment(obj);
		apiResponse.success(obj);
		return apiResponse;
	}
	
	@RequestMapping("/blog/comment/list")
	@ResponseBody
	public ApiResponse<Object> listComment(HttpServletRequest request) throws RequestParameterException{
		ApiResponse<Object> apiResponse = new ApiResponse<Object>();
		Pagination pagination = HttpRequestUtil.getPagination(request);
		pagination.setSortName("created_time");
		pagination.setSortOrder(Pagination.SORT_ORDER_DESC);
		Long blogId = HttpRequestUtil.getLong(request, "blogId");
		List<BlogCommentObj> list = blogService.listComment(blogId, pagination);
		apiResponse.success(list);
		return apiResponse;
	}

}
