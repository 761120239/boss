package com.itheima.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.base.Courier;

/**  
 * ClassName:CourierService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午8:23:47 <br/>       
 */
public interface CourierService {

	void save(Courier courier);

	Page<Courier> findAll(Pageable pageable);

	void batchDel(String ids);

	List<Courier> findAvalible();

}
  
