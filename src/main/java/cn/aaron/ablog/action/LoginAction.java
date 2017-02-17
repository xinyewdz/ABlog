package cn.aaron.ablog.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.util.NoneUtil;
import org.util.SignatureUtil;

import cn.aaron.ablog.base.impl.BasePage;
import cn.aaron.ablog.obj.UserObj;
import cn.aaron.ablog.service.UserService;

/**
*TODO
*@author Aaron
*@date 2017年2月7日
*/
@Controller
public class LoginAction extends BasePage {
	
	private Logger log = Logger.getLogger(LoginAction.class);
	
	@Autowired
	private UserService userService;

	@RequestMapping("/login")
	public String login(HttpServletRequest request){
		return "/login";
	}

	@RequestMapping("/doLogin")
	public String doLogin(HttpServletRequest request){
		String login = request.getParameter("loginId");
		String password = request.getParameter("password");
		if(NoneUtil.isBlank(login)||NoneUtil.isBlank(password)){
			setErrMsg(request, "用户名或密码不能为空");
			log.info("用户名或密码不能为空");
			return "/login";
		}
		String md5Password = SignatureUtil.md5(password);
		UserObj userObj = userService.find(login);
		if(userObj!=null){
			if(userObj.getPassword().equals(md5Password)){
				request.getSession().setAttribute(BasePage.USER_SESSION, userObj);
				log.info("用户登录成功。login="+login);
				return "redirect:/";
			}else{
				userObj = null;
			}
		}
		if(userObj==null){
			log.info("用户名或密码错误");
			setErrMsg(request, "用户名或密码错误");
		}
		return "/login";
	}
	
}
