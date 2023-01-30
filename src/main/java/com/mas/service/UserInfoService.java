package com.mas.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mas.model.UserInfo;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserInfoService extends IService<UserInfo> {
    List<UserInfo> getAllUser();
}
