package com.yang.cloud.wms_all.common.vo.trade;

import com.yang.cloud.wms_all.common.utils.DateRange;
import com.yang.cloud.wms_all.common.utils.Pagination;
import lombok.Data;

@Data
public class TradeSelectVo {

    private String number;
    private Boolean tradeType;
    private DateRange dateRange;
    private Pagination pagination = new Pagination();
}
