package com.atguigu.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class ConfigServerController {
    @Value("${server.port}")
    private String serverPort;

// 服务启动时为什么拉不到GitHub上的配置属性值
//    @Value("${config.info}")
//    private String configInfo;

    @GetMapping("/configInfo")
    public String getConfigInfo() {
        return "serverPort: " + serverPort + "\t\n\n configInfo: ";
    }
}
