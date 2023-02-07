package com.mas.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mas.mapper.UserInfoMapper;
import com.mas.model.UserInfo;
import com.mas.model.vo.UserInfoVO;
import com.mas.mybatis.Pager;
import com.mas.param.UserInfoPageParam;
import com.mas.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService  {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public List<UserInfo> getAllUser() {
        LambdaQueryWrapper<UserInfo> query = new LambdaQueryWrapper<>();
        query.isNotNull(UserInfo::getId);
        return this.list(query);
    }

    @Override
    public Pager<UserInfoVO> page(UserInfoPageParam param) {
        LambdaQueryWrapper<UserInfo> query = new LambdaQueryWrapper<>();
        query.isNotNull(UserInfo::getId);
        userInfoMapper.page(Pager.transform(param),query);
//        return Pager.transform(userInfoMapper.page(Pager.transform(param),query),this::transform);
        return null;
    }

    private UserInfoVO transform(UserInfo userInfo){
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setId(userInfo.getId());
        userInfoVO.setUsername(userInfo.getUsername());
        return userInfoVO;
    }

    @Override
    public String getCode(String province) {
        HashMap<String, String> codeList = new HashMap<String, String>();
        codeList.put("北京", "1");
        codeList.put("天津", "2");
        codeList.put("河北", "3");
        codeList.put("山西", "4");
        codeList.put("内蒙古", "5");
        codeList.put("辽宁", "6");
        codeList.put("吉林", "7");
        codeList.put("黑龙江", "8");
        codeList.put("上海", "9");
        codeList.put("江苏", "10");
        codeList.put("浙江", "11");
        codeList.put("安徽", "12");
        codeList.put("福建", "13");
        codeList.put("江西", "14");
        codeList.put("山东", "15");
        codeList.put("河南", "16");
        codeList.put("湖北", "17");
        codeList.put("湖南", "18");
        codeList.put("广西", "20");
        codeList.put("海南", "21");
        codeList.put("四川", "22");
        codeList.put("重庆", "23");
        codeList.put("贵州", "24");
        codeList.put("云南", "25");
        codeList.put("西藏", "26");
        codeList.put("陕西", "27");
        codeList.put("甘肃", "28");
        codeList.put("青海", "29");
        codeList.put("宁夏", "30");
        codeList.put("新疆", "31");
        codeList.put("台湾", "32");
        codeList.put("香港", "33");
        codeList.put("澳门", "34");
        codeList.put("外籍", "35");
        String newProvince = null;
        if(province != null && province != ""){
            newProvince = province.replace("省", "").replace("市","");
        }
        String provinceCode = codeList.get(newProvince);
        codeList.clear();
        return provinceCode;
    }

    public String getRC014Code(String oldCode){
        HashMap<String, String> codeList = new HashMap<>();
        codeList.put("0-","0");
        codeList.put("1-1","1");
        codeList.put("1-2","2");
        codeList.put("1-3","3");
        codeList.put("1-4","10");
        codeList.put("2-1","4");
        codeList.put("2-2","5");
        codeList.put("2-3","6");
        codeList.put("2-4","20");
        codeList.put("3-1","7");
        codeList.put("3-2","8");
        codeList.put("3-3","9");
        codeList.put("3-4","30");
        return codeList.get(oldCode);
    }

}
