package cn.aaron.ablog.base.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.utils.ApiResponse;
import org.utils.ApiResponseCode;
import org.utils.gson.GsonUtil;

import cn.aaron.ablog.base.impl.BaseApiPage;
import cn.aaron.ablog.base.impl.BasePage;

/**
*TODO
*@author Aaron
*@date 2016年12月23日
*/
public class ExceptionHandler implements HandlerExceptionResolver{
	
	private Logger log = Logger.getLogger(ExceptionHandler.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,	Exception exception) {
		ModelAndView modelAndView = new ModelAndView();
		if(handler instanceof HandlerMethod){
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Object bean =  handlerMethod.getBean();
			log.error("error.",exception);
			if(bean instanceof BaseApiPage){
				ApiResponse<String> apiResp = new ApiResponse<String>();
				if(exception instanceof RequestParameterException){
					apiResp.error(ApiResponseCode.request_parameter_error);
				}else{
					apiResp.error(ApiResponseCode.system_error);
				}
				try {
					response.setContentType("application/json;charset=utf-8");
					PrintWriter pw = response.getWriter();
					pw.write(GsonUtil.toJson(apiResp));
					pw.flush();
				} catch (IOException e) {
					log.error("error", e);
				}
			}else if(bean instanceof BasePage){
				try {
					String contextPath = request.getContextPath();
					response.sendRedirect(contextPath+"/static/html/error.html");
				} catch (IOException e) {
					log.error("error", e);
				}
			}
		}
		
		return modelAndView;
	}

}
