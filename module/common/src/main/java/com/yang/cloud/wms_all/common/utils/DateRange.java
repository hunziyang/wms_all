package com.yang.cloud.wms_all.common.utils;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Data
public class DateRange {

    @NotNull(message = "startAt")
    private ZonedDateTime startAt;

    @NotNull(message = "endAt")
    private ZonedDateTime endAt;
}
