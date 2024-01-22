package com.yang.cloud.wms_all.trade.mapper;

import com.yang.cloud.wms_all.common.vo.trade.TradeSelectVo;
import com.yang.cloud.wms_all.core.mybatis.WmsMapper;
import com.yang.cloud.wms_all.trade.dto.TradeDetailDto;
import com.yang.cloud.wms_all.trade.entity.Trade;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TradeMapper extends WmsMapper<Trade> {
    List<TradeDetailDto> select(@Param("query") TradeSelectVo tradeSelectVo, @Param("offset") Integer offset, @Param("size") Integer pageSize);

    Long selectCount(@Param("query") TradeSelectVo tradeSelectVo);
}
