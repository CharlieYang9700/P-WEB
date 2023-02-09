package com.mas.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mas.model.UserInfo;
import com.mas.model.param.UserLoginParam;
import com.mas.model.param.UserRegisterParam;
import com.mas.model.vo.UserInfoVO;
import com.mas.mybatis.Pager;
import com.mas.model.param.UserInfoPageParam;
import com.mas.utils.BizException;

import java.util.List;


public interface UserInfoService extends IService<UserInfo> {
    List<UserInfo> getAllUser();

    /***
     * 通过用户名获取用户信息
     * @param username
     * @return
     */
    UserInfo getUserInfoByUsername(String username);

    Pager<UserInfoVO> page (UserInfoPageParam param);

    /**
     * 用户密码登陆
     * @param loginParam
     * @return
     * @throws BizException
     */
    UserInfoVO loginByPassword(UserLoginParam loginParam) throws BizException;

    UserInfoVO registerByUsernameAndPassword(UserRegisterParam registerParam) throws BizException;
}
