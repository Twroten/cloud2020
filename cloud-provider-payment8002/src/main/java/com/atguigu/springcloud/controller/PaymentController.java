package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;//添加serverPort

    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment) {
        int result = paymentService.creat(payment);
        if (result > 0) {
            return new CommonResult(200, "新增成功！serverPort：" + serverPort, result);
        } else {
            return new CommonResult(444, "新增失败！", null);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("*****查询结果[payment]：" + payment);
        if (payment != null) {
            return new CommonResult(200, "查询成功！serverPort：" + serverPort, payment);
        } else {
            return new CommonResult(444, "没有对应记录，查询ID：" + id, null);
        }
    }

    @GetMapping(value = "/payment/lb")
    public String getPaymentLB() {
        return serverPort;
    }

}
