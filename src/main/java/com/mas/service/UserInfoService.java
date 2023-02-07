package com.mas.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mas.model.UserInfo;
import com.mas.model.vo.UserInfoVO;
import com.mas.mybatis.Pager;
import com.mas.param.UserInfoPageParam;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserInfoService extends IService<UserInfo> {
    List<UserInfo> getAllUser();

    Pager<UserInfoVO> page(UserInfoPageParam param);

    String getCode(String province);
}
