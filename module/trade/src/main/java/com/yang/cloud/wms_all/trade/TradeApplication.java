package com.yang.cloud.wms_all.trade;

import com.yang.cloud.wms_all.core.annontation.WmsApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@WmsApplication
@EnableFeignClients
public class TradeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TradeApplication.class, args);
    }
}
