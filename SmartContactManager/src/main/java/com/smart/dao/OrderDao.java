package com.smart.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart.entity.MyOrder;

public interface OrderDao extends JpaRepository<MyOrder, Integer>{
	
	public MyOrder findByOrderId(String orderId);

}
