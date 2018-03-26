package com.itheima.bos.service.realms;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itheima.bos.dao.system.UserRepository;
import com.itheima.bos.domain.system.User;

/**  
 * ClassName:UserRealm <br/>  
 * Function:  <br/>  
 * Date:     2018年3月26日 下午5:40:32 <br/>       
 */
@Component
public class UserRealm extends AuthorizingRealm{
	@Autowired
	private UserRepository userRepository;
	
	//授权方法
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
		  
		SimpleAuthorizationInfo info = new	SimpleAuthorizationInfo ();
		// 授权
		info.addStringPermission("courierAction_pageQuery");
		
		 //授予角色
        info.addRole("admin");
		return null;
	}
	
	//认证方法
	 // 参数中的token就是subject.login(token)方法中传入的参数
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		  
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
		String username = usernamePasswordToken.getUsername();
		
		// 根据用户名查找用户
		User user = userRepository.findByUsername(username);
		

        /**
         * @param principal 当事人,主体.通常是从数据库中查询到的用户
         * @param credentials 凭证,密码.是从数据库中查询出来的密码
         * @param realmName
         */
		if(user != null){
			// 找到 ->比对密码
			AuthenticationInfo info = new SimpleAuthenticationInfo(user,user.getPassword(),getName());
			
			return info;
		}
		return null;
	}

}
  
