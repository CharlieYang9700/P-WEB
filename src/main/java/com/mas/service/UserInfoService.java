package com.mas.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mas.model.UserInfo;
import com.mas.model.param.UserLoginParam;
import com.mas.model.vo.UserInfoVO;
import com.mas.mybatis.Pager;
import com.mas.param.UserInfoPageParam;
import com.mas.utils.BizException;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserInfoService extends IService<UserInfo> {
    List<UserInfo> getAllUser();

    /***
     * 通过用户名获取用户信息
     * @param username
     * @return
     */
    UserInfo getUserInfoByUsername(String username);

    Pager<UserInfoVO> page(UserInfoPageParam param);

    String getCode(String province);

    UserInfoVO loginByPassword(UserLoginParam loginParam) throws BizException;
}
