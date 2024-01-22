package com.yang.cloud.wms_all.trade.service;

import com.yang.cloud.wms_all.common.vo.trade.TradeCreateVo;
import com.yang.cloud.wms_all.common.vo.trade.TradeSelectVo;
import com.yang.cloud.wms_all.core.result.Result;
import com.yang.cloud.wms_all.core.util.PagedList;
import com.yang.cloud.wms_all.trade.dto.TradeDetailDto;

public interface TradeService {
    Result create(TradeCreateVo tradeCreateVo);

    PagedList<TradeDetailDto> select(TradeSelectVo tradeSelectVo);

}
