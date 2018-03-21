package com.itheima.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.base.FixedArea;

/**  
 * ClassName:FixedAreaService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午8:58:47 <br/>       
 */
public interface FixedAreaService {

	void save(FixedArea standard);

	Page<FixedArea> findAll(Pageable pageable);

	void aassociationCourierToFixedArea(Long fixedAreaId, Long courierId, Long takeTimeId);

	void assignSubAreas2FixedArea(Long fixedAreaId, Long[] subAreaIds);

}
  
