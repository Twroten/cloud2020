package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

@Service
public interface PaymentService {

    public int creat(Payment payment);

    public Payment getPaymentById(@Param("id") Long id);
}
