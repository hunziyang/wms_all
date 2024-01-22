package com.yang.cloud.wms_all.common.vo.trade;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class TradeCreateVo {

    @NotEmpty(message = "productDetails 不能为空")
    List<@Valid ProductDetail> productDetails;

    private Boolean tradeType = Boolean.FALSE;
}
