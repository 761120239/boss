package com.itheima.bos.web.action.system;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.system.User;
import com.itheima.bos.web.action.BaseAction;

/**
 * ClassName:UserAction <br/>
 * Function: <br/>
 * Date: 2018年3月26日 下午4:50:50 <br/>
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User> {
	
	//属性驱动封装 用户输入的验证码
	private String checkcode;
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}

	@Action(value = "userAction_login", results = {
			@Result(name = "success", location = "/index.html", type = "redirect"),
			@Result(name = "login", location = "/login.html", type = "redirect") })
	public String login() {
		
		String serverCode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		if(StringUtils.isNotEmpty(serverCode)&&StringUtils.isNotEmpty(checkcode)&&serverCode.equals(checkcode)){
			
			// 代表当前用户
			Subject subject = SecurityUtils.getSubject();
			AuthenticationToken token = new UsernamePasswordToken(getModel().getUsername(), getModel().getPassword());
			try {
				subject.login(token);
				
				// 方法的返回值由Realm中doGetAuthenticationInfo方法定义SimpleAuthenticationInfo对象的时候,第一个参数决定的
				User user = (User) subject.getPrincipals();
				ServletActionContext.getRequest().getSession().setAttribute("user", user);
				return SUCCESS;
		    } catch (UnknownAccountException e) {
                // 用户名写错了
                e.printStackTrace();
                System.out.println("用户名错误");
            } catch (IncorrectCredentialsException e) {
                // 用户名写错了
                e.printStackTrace();
                System.out.println("密码错误");
            } catch (Exception e) {
                // 用户名写错了
                e.printStackTrace();
                System.out.println("其他错误");
            }
			
			return SUCCESS;
		}

		
		
		return LOGIN;
	}
}
