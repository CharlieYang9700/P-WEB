package com.mas.service.impl;



import cn.hutool.core.util.DesensitizedUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mas.mapper.UserInfoMapper;
import com.mas.model.UserInfo;
import com.mas.model.param.UserLoginParam;
import com.mas.model.param.UserRegisterParam;
import com.mas.model.vo.UserInfoVO;
import com.mas.mybatis.Pager;
import com.mas.model.param.UserInfoPageParam;
import com.mas.service.UserInfoService;
import com.mas.utils.*;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService  {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public List<UserInfo> getAllUser() {
        LambdaQueryWrapper<UserInfo> query = new LambdaQueryWrapper<>();
        query.isNotNull(UserInfo::getId);
        return this.list(query);
    }

    @Override
    public UserInfo getUserInfoByUsername(String username) {
        LambdaQueryWrapper<UserInfo> query = new LambdaQueryWrapper<>();
        query.eq(UserInfo::getUsername,username);
        query.last("limit 1");
        return userInfoMapper.selectOne(query);
    }

    @Override
    public Pager<UserInfoVO> page(UserInfoPageParam pageParam) {
        LambdaQueryWrapper<UserInfo> query = new LambdaQueryWrapper<>();
        query.ne(UserInfo::getAtTime,0);
        userInfoMapper.selectPage(Pager.transform(pageParam), query);
        log.info("pageParam={}",pageParam.getLimit());
        return Pager.transform(userInfoMapper.selectPage(Pager.transform(pageParam), query),this::transform);
    }


    @Override
    public UserInfoVO loginByPassword(UserLoginParam loginParam) throws BizException {
        //第一步 通过用户名查询数据库看用户是否存在
        UserInfo dbUserInfo = this.getUserInfoByUsername(loginParam.getUsername());
        if(Objects.isNull(dbUserInfo)){
            throw new BizException("用户不存在");
        }
        //第二部 若存在这取到用户数据进行比对
        if(!BCrypt.checkpw(loginParam.getPassword(),dbUserInfo.getPassword())){
            throw new BizException("用户名或者密码错误");
        }
        return this.afterHandleLogin(dbUserInfo);
    }

    @Override
    public UserInfoVO registerByUsernameAndPassword(UserRegisterParam registerParam) throws BizException {
        //查询数据库中是否存在用户名相同的用户
        UserInfo dbUser = this.getUserInfoByUsername(registerParam.getUsername());
        if(Objects.nonNull(dbUser)){
            throw new BizException("该用户名已经存在");
        }
        UserInfo userinfo = transform2(registerParam,BCrypt.gensalt());
        userInfoMapper.insert(userinfo);
        return this.transform(userinfo);
    }

    private UserInfoVO transform( UserInfo userInfo){
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setId(userInfo.getId());
        userInfoVO.setUsername(userInfo.getUsername());
        userInfoVO.setPhone(DesensitizedUtil.mobilePhone(userInfo.getPhone()));
        return userInfoVO;
    }

    private UserInfo transform2(UserRegisterParam registerParam,String salt){
        UserInfo userInfo = new UserInfo();
        userInfo.setId(IdWorker.getId());
        userInfo.setUsername(registerParam.getUsername());
        userInfo.setPassword(this.getPassword(registerParam.getPassword()));
        userInfo.setAtTime(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        userInfo.setSalt(salt);
        return userInfo;
    }

    /**
     * 使用BCrypt加密后的密码
     * @param inputPassword
     * @return
     */
    private String getPassword(String inputPassword){
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(inputPassword, salt);
    }

    private UserInfoVO afterHandleLogin(UserInfo userInfo){
        String token = JwtUtils.createToken(userInfo.getId().toString());
        log.info("Token={}",token);
        redisTemplate.opsForValue().set(String.format("%s_%s", Constants.AUTHORIZATION,token), JSON.toJSONString(userInfo),60*2, TimeUnit.MINUTES);
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setId(userInfo.getId());
        userInfoVO.setUsername(userInfo.getUsername());
        userInfoVO.setToken(token);
        return userInfoVO;
    }

}
