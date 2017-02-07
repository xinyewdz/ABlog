package cn.aaron.ablog.base;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.utils.ApiResponse;
import org.utils.ApiResponseCode;
import org.utils.gson.GsonUtil;

import cn.aaron.ablog.base.annotation.Permission;
import cn.aaron.ablog.base.impl.BasePage;

/**
 *TODO
 *@author Aaron
 *@date 2016年8月16日
 */
public class PermissionInterceptor extends HandlerInterceptorAdapter{
	
	private Logger log = Logger.getLogger(PermissionInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		boolean permissionFlag = true;
		if(handler instanceof HandlerMethod){
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Object bean =  handlerMethod.getBean();
			if(bean instanceof Page){
				Page page = (Page) bean;
				permissionFlag = page.checkPermission(request,response);
				if(!permissionFlag){
					Permission permission = handlerMethod.getMethodAnnotation(Permission.class);
					if(permission!=null){
						permissionFlag = permission.value();
					}
				}
				String path = request.getRequestURI();
				log.info("PermissionInterceptor path="+path+" checkPermission="+permissionFlag);
				if (!permissionFlag) {
					if (page instanceof ApiPage) {
						ApiResponse<String> apiResp = new ApiResponse<String>();
						apiResp.error(ApiResponseCode.access_deny);
						response.setContentType("application/json; charset=utf-8");
						PrintWriter pw = response.getWriter();
						pw.write(GsonUtil.toJson(apiResp));
						pw.flush();
					}else if(page instanceof BasePage){
						response.setContentType("text/html;charset=UTF-8");  
				        response.setHeader("Location", request.getContextPath()+"/login");  
				        response.setStatus(303);
					}
				}
			}
		}
		return permissionFlag;
	}


}
