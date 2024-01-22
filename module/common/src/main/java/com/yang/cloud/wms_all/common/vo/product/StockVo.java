package com.yang.cloud.wms_all.common.vo.product;

import com.yang.cloud.wms_all.common.vo.trade.ProductDetail;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Accessors(chain = true)
public class StockVo {


    @NotEmpty(message = "productDetails 不能为空")
    private List<@Valid ProductDetail> productDetails;

    @NotBlank(message = "trade 不能为空")
    private String number;
}
