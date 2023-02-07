package com.mas.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mas.model.UserInfo;
import com.mas.model.vo.UserInfoVO;
import com.mas.mybatis.Pager;
import com.mas.param.UserInfoPageParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    Pager<UserInfoVO> page(Pager<UserInfo> transform, @Param("ew") Wrapper queryWrapper);
}
