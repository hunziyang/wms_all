package com.yang.cloud.wms_all.trade.dto;

import com.yang.cloud.wms_all.trade.entity.Trade;
import com.yang.cloud.wms_all.trade.entity.TradeDetail;
import lombok.Data;

import java.util.List;

@Data
public class TradeDetailDto extends Trade {

    private List<TradeDetail> tradeDetails;
}
