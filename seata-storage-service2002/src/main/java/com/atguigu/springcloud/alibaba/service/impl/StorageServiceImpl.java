package com.atguigu.springcloud.alibaba.service.impl;

import com.atguigu.springcloud.alibaba.service.StorageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StorageServiceImpl implements StorageService {

    @Resource
    private StorageService storageService;

    @Override
    public void decrease(Long productId, Integer count) {
        storageService.decrease(productId, count);
    }
}
