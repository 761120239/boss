package com.itheima.bos.dao.base;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.base.Courier;

/**  
 * ClassName:CourierRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午8:30:49 <br/>       
 */
public interface CourierRepository extends JpaRepository<Courier, Long>{
	 // 根据ID更改删除的标志位
    @Modifying
    @Query("update Courier set deltag = 1 where id = ?")
	void updateDelTagById(long parseLong);

	List<Courier> findByDeltagIsNull();

	
}
  
