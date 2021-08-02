package com.atguigu.springcloud.alibaba.service.impl;

import com.atguigu.springcloud.alibaba.service.StorageService;

import javax.annotation.Resource;

public class StorageServiceImpl implements StorageService {

    @Resource
    private StorageService storageService;

    @Override
    public void decrease(Long productId, Integer count) {
        storageService.decrease(productId, count);
    }
}
