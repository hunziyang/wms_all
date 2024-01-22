package com.yang.cloud.wms_all.gateway.filter;

import com.yang.cloud.wms_all.common.dto.user.UserLoginDto;
import com.yang.cloud.wms_all.common.vo.user.UserJwtDetailVo;
import com.yang.cloud.wms_all.gateway.exception.InternalServerErrorException;
import com.yang.cloud.wms_all.gateway.feign.UserFeignService;
import com.yang.cloud.wms_all.gateway.result.Result;
import com.yang.cloud.wms_all.gateway.utils.MapUtil;
import com.yang.cloud.wms_all.gateway.utils.SpringUtil;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
@Order(1)
public class CustomGlobalFilter implements WebFilter {

    private static final String AUTHORIZATION = "Authorization";
    private static final String SLASH = "/";
    private static final Integer SLASH_INDEX = 1;
    private static final String ANONYMOUS = "/anonymous";
    private static final String USER_NAME = "USER_NAME";
    private static final String USER_ID = "USER_ID";
    private static final String JWT = "JWT";
    private static final String REQUEST_ID = "REQUEST_ID";

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String requestId = UUID.randomUUID().toString();
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().value();
        if (isAnonymousPath(path)) {
            request.mutate().headers(httpHeaders -> httpHeaders.add(REQUEST_ID, requestId));
            exchange.getResponse().getHeaders().add(REQUEST_ID, requestId);
            return chain.filter(exchange);
        }
        if (!request.getHeaders().containsKey(AUTHORIZATION)) {
            throw new InternalServerErrorException(MapUtil.getErrorMap("Authorization header not exists"));
        }
        String jwt = request.getHeaders().getFirst(AUTHORIZATION);
        UserFeignService userFeignService = SpringUtil.getBean(UserFeignService.class);
        CompletableFuture<Result<UserLoginDto>> resultCompletableFuture = CompletableFuture.supplyAsync(() ->
                userFeignService.jwtDetail(new UserJwtDetailVo().setJwt(jwt), requestId)
        );
        UserLoginDto userLoginDto = resultCompletableFuture.get().getBody();
        if (ObjectUtils.isEmpty(userLoginDto)) {
            throw new InternalServerErrorException(MapUtil.getErrorMap("Jwt has expired"));
        }
        List<String> permissions = userLoginDto.getPermissions().stream().filter(permission -> path.startsWith(permission)).collect(Collectors.toList());
        if (ObjectUtils.isEmpty(permissions)) {
            throw new InternalServerErrorException(MapUtil.getErrorMap("Permission denied"));
        }
        setRequestHeader(request, userLoginDto, requestId);
        exchange.getResponse().getHeaders().add(REQUEST_ID, requestId);
        return chain.filter(exchange);
    }

    private boolean isAnonymousPath(String path) {
        int index = path.indexOf(SLASH, SLASH_INDEX);
        return path.substring(index).startsWith(ANONYMOUS);
    }

    private void setRequestHeader(ServerHttpRequest request, UserLoginDto userLoginDto, String requestId) {
        request.mutate().headers(httpHeaders -> {
            httpHeaders.set(JWT, userLoginDto.getJwt());
            httpHeaders.set(REQUEST_ID, requestId);
            httpHeaders.set(USER_ID, userLoginDto.getUserId().toString());
            httpHeaders.set(USER_NAME, userLoginDto.getUserName());
        });
    }
}
