package com.atguigu.springcloud.alibaba.service;

import com.atguigu.springcloud.alibaba.domain.Order;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {

    // 创建订单
    void create(Order order);

}
