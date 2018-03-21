package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.CourierService;
import com.itheima.bos.web.action.BaseAction;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * ClassName:CourierAction <br/>
 * Function: <br/>
 * Date: 2018年3月14日 下午8:14:57 <br/>
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("protoType")
public class CourierAction extends BaseAction<Courier> {
	@Autowired
	private CourierService courierService;

    @Action(value = "courierAction_save", results = {@Result(name = "success",
            location = "/pages/base/courier.html", type = "redirect")})
    public String save() {

        courierService.save(getModel());
        return SUCCESS;
    }



    @Action("courierAction_pageQuery")
    public String pageQuery() throws IOException {

        Specification<Courier> specification = new Specification<Courier>() {
            /**
             * 创建一个查询的where语句
             * 
             * @param root : 根对象.可以简单的认为就是泛型对象
             * @param cb : 构建查询条件
             * @return a {@link Predicate}, must not be {@literal null}.
             */
            @Override
            public Predicate toPredicate(Root<Courier> root,
                    CriteriaQuery<?> query, CriteriaBuilder cb) {

                String courierNum = getModel().getCourierNum();
                String company = getModel().getCompany();
                String type = getModel().getType();
                Standard standard = getModel().getStandard();
                // 存储条件的集合
                List<Predicate> list = new ArrayList<>();
                if (StringUtils.isNotEmpty(courierNum)) {
                    // 如果工号不为空,构建一个等值查询条件
                    // where courierNum = "001"
                    // 参数二 : 具体的要比较的值
                    Predicate p1 =
                            cb.equal(root.get("courierNum").as(String.class),
                                    courierNum);
                    list.add(p1);
                }
                if (StringUtils.isNotEmpty(company)) {
                    // 如果公司不为空,构建一个模糊查询条件
                    // where company like "001"
                    // 参数二 : 具体的要比较的值
                    Predicate p2 = cb.like(root.get("company").as(String.class),
                            "%" + company + "%");
                    list.add(p2);
                }
                if (StringUtils.isNotEmpty(type)) {
                    // 如果类型不为空,构建一个等值查询条件
                    // where courierNum = "001"
                    // 参数二 : 具体的要比较的值
                    Predicate p3 =
                            cb.equal(root.get("type").as(String.class), type);
                    list.add(p3);
                }

                if (standard != null) {
                    String name = standard.getName();
                    if (StringUtils.isNotEmpty(name)) {
                        // 连表查询,查询标准的名字
                        Join<Object, Object> join = root.join("standard");
                        Predicate p4 = cb
                                .equal(join.get("name").as(String.class), name);
                        list.add(p4);
                    }
                }
                // 用户没有输入查询条件
                if (list.size() == 0) {
                    return null;
                }

                // 用户输入了查询条件
                // 将多个条件进行组合
                Predicate[] arr = new Predicate[list.size()];
                list.toArray(arr);
                // 用户输入了多少个条件,就让多少个条件同时都满足
                Predicate predicate = cb.and(arr);

                return predicate;
            }
        };

        Pageable pageable = new PageRequest(page - 1, rows);

        Page<Courier> page = courierService.findAll(pageable);

        
        //处理懒加载问题  灵活控制输出的内容
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"fixedAreas", "takeTime"});
        
        page2json(page, jsonConfig);

        

        return NONE;
    }

    // 使用属性驱动获取要删除的快递员的Id
    private String ids;

    public void setIds(String ids) {
        this.ids = ids;
    }

    // 批量删除
    @Action(value = "courierAction_batchDel",
            results = {@Result(name = "success",
                    location = "/pages/base/courier.html", type = "redirect")})
    public String batchDel() {
        courierService.batchDel(ids);
        return SUCCESS;
    }
    
    @Action(value = "courierAction_findAvalible")
    public String findAvalible() throws IOException{
    	
    	List<Courier> list = courierService.findAvalible();
    	JsonConfig jsonConfig = new JsonConfig();
    	// 指定在生成json数据的时候要忽略的字段
		jsonConfig.setExcludes(new String[] { "fixedAreas", "takeTime" });

		list2json(list, jsonConfig);

		return NONE;
    	
    }

}
