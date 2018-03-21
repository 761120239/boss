package com.itheima.bos.service.base.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.CourierRepository;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.service.base.CourierService;

/**  
 * ClassName:CourierServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午8:29:05 <br/>       
 */
@Service
@Transactional
public class CourierServiceImpl implements CourierService {
	
	@Autowired
	private CourierRepository courierRepository;

	   // 保存
    @Override
    public void save(Courier courier) {

        courierRepository.save(courier);
    }


    @Override
    public Page<Courier> findAll(Pageable pageable) {
    	
    	return courierRepository.findAll(pageable);
    }

	@Override
	public void batchDel(String ids) {
		// 真实开发中只有逻辑删除
        // null " "
        // 判断数据是否为空
		if(StringUtils.isNotEmpty(ids)){
			//切割数据
			String[] split = ids.split(",");
			for (String id : split) {
				courierRepository.updateDelTagById(Long.parseLong(id));
			}
			
		}
		
	}


	@Override
	public List<Courier> findAvalible() {
		  
		return courierRepository.findByDeltagIsNull();
	}

    
    

}
  
