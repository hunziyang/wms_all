package com.yang.cloud.wms_all.user.service.impl;

import com.yang.cloud.wms_all.common.dto.user.UserLoginDto;
import com.yang.cloud.wms_all.user.entity.Permission;
import com.yang.cloud.wms_all.user.entity.User;
import com.yang.cloud.wms_all.user.entity.UserRole;
import com.yang.cloud.wms_all.common.vo.user.UserJwtDetailVo;
import com.yang.cloud.wms_all.common.vo.user.UserLoginVo;
import com.yang.cloud.wms_all.common.vo.user.UserRegisterVo;
import com.yang.cloud.wms_all.core.exception.BaseException;
import com.yang.cloud.wms_all.user.mapper.PermissionMapper;
import com.yang.cloud.wms_all.user.mapper.UserMapper;
import com.yang.cloud.wms_all.user.mapper.UserRoleMapper;
import com.yang.cloud.wms_all.user.result.ResultUserCode;
import com.yang.cloud.wms_all.user.service.UserService;
import com.yang.cloud.wms_all.user.utils.JwtUtil;
import com.yang.cloud.wms_all.user.utils.SaleUtil;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    private static final Long DEFAULT_ROLE = 1L;

    @Override
    @Transactional
    public void register(UserRegisterVo userRegisterVo) {
        User user = new User();
        BeanUtils.copyProperties(userRegisterVo, user);
        String salt = SaleUtil.getSalt();
        String password = Md5Crypt.md5Crypt(
                user.getPassword().getBytes(StandardCharsets.UTF_8),
                String.format("%s%s", SaleUtil.SALT_PREFIX, salt),
                SaleUtil.SALT_PREFIX);
        user.setPassword(password)
                .setSalt(salt);
        userMapper.insert(user);
        UserRole userRole = new UserRole()
                .setRoleId(DEFAULT_ROLE)
                .setUserId(user.getId());
        userRoleMapper.insert(userRole);
    }

    @Override
    public UserLoginDto login(UserLoginVo userLoginVo) {
        User user = userMapper.selectByUsername(userLoginVo.getUsername());
        if (user.getIsLocked()) {
            throw new BaseException(ResultUserCode.USER_LOCKED);
        }
        String password = Md5Crypt.md5Crypt(
                userLoginVo.getPassword().getBytes(StandardCharsets.UTF_8),
                String.format("%s%s", SaleUtil.SALT_PREFIX, user.getSalt()),
                SaleUtil.SALT_PREFIX);
        if (!user.getPassword().equals(password)) {
            throw new BaseException(ResultUserCode.WRONG_PASSWORD);
        }
        List<String> permissions = permissionMapper.getPermissionsByUserId(user.getId()).stream().map(Permission::getUrl).collect(Collectors.toList());
        String jwt = JwtUtil.sign(user.getUsername());
        UserLoginDto userLoginDto =  new UserLoginDto()
                .setJwt(jwt)
                .setUserName(user.getName())
                .setUserId(user.getId())
                .setPermissions(permissions);
        redisTemplate.opsForValue().set(jwt,userLoginDto, Duration.ofHours(8));
        return userLoginDto;
    }

    @Override
    public UserLoginDto jwtDetail(UserJwtDetailVo userJwtDetailVo) {
        return (UserLoginDto) redisTemplate.opsForValue().get(userJwtDetailVo.getJwt());
    }
}
