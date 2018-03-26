package com.itheima.bos.dao.take_delivery;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itheima.bos.domain.take_delivery.Order;

/**  
 * ClassName:OrderRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月24日 下午2:38:43 <br/>       
 */
public interface OrderRepository extends JpaRepository<Order, Long>{

}
  
