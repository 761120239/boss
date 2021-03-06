package com.itheima.bos.web.action.base;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.StandardService;
import com.itheima.bos.web.action.BaseAction;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**  
 * ClassName:StandarAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午4:06:18 <br/>       
 */

@Namespace("/") // 等价于struts.xml文件中package节点namespace属性
@ParentPackage("struts-default") // 等价于struts.xml文件中package节点extends属性
@Controller // spring 的注解,控制层代码
@Scope("prototype") // spring 的注解,多例
public class StandardAction extends BaseAction<Standard>{
	
	@Autowired //注解注入
	private StandardService standardService;
	
	 // value : // 等价于struts.xml文件中action节点中的name属性
    // 多个结果就使用@Result注解标识
    // name : 结果
    // location: 跳转页面路径
    // type : 使用的方式,重定向或转发
	
	@Action(value = "standardAction_save", results= {@Result(name = "success",location = "/pages/base/standard.html",type = "redirect")})
	public String save(){
		
		standardService.save(getModel());
		return SUCCESS;
	}
	

	
	// Ajax请求不需要跳转页面
	@Action(value = "standardAction_pageQuery")  //value的值是指方法
	public String pageQuery () throws IOException{
		// EasyUI的页码是从1开始的
        // SPringDataJPA的页码是从0开始的
        // 所以要-1
		Pageable pageable = new PageRequest(page-1, rows);
		
		Page<Standard> page = standardService.findAll(pageable);
		
		page2json(page, null);
		
		return NONE;
	}
	
	// 查询所有的派送标准
	@Action(value = "standard_findAll")
	public String findAll() throws IOException{
		//查询数据
		Page<Standard> page = standardService.findAll(null);
		//获取页面数据
		List<Standard> list = page.getContent();
		//转换数据Json传回页面
		String json = JSONArray.fromObject(list).toString();
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(json);
		
		return NONE;
	}
	
}
  
