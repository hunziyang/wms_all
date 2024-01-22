package com.yang.cloud.wms_all.gateway.feign;

import com.yang.cloud.wms_all.common.dto.user.UserLoginDto;
import com.yang.cloud.wms_all.common.vo.user.UserJwtDetailVo;
import com.yang.cloud.wms_all.gateway.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service", path = "/user-service")
public interface UserFeignService {

    @PostMapping("/anonymous/user/jwtDetail")
    Result<UserLoginDto> jwtDetail(@Validated @RequestBody UserJwtDetailVo userJwtDetailVo, @RequestHeader("REQUEST_ID") String requestId);
}
