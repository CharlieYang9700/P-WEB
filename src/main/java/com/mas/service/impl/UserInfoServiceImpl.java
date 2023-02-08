package com.mas.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mas.mapper.UserInfoMapper;
import com.mas.model.UserInfo;
import com.mas.model.param.UserLoginParam;
import com.mas.model.param.UserRegisterParam;
import com.mas.model.vo.UserInfoVO;
import com.mas.mybatis.Pager;
import com.mas.model.param.UserInfoPageParam;
import com.mas.service.UserInfoService;
import com.mas.utils.BizException;
import com.mas.utils.IdGeneratorSnowflake;
import com.mas.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService  {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private RedisTemplate redisTemplate;

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
    public Pager<UserInfoVO> page(UserInfoPageParam param) {
        LambdaQueryWrapper<UserInfo> query = new LambdaQueryWrapper<>();
        query.isNotNull(UserInfo::getId);
        userInfoMapper.page(Pager.transform(param),query);
//        return Pager.transform(userInfoMapper.page(Pager.transform(param),query),this::transform);
        return null;
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
        if(Objects.isNull(dbUser)){
            throw new BizException("该用户名已经存在");
        }
        UserInfo userinfo = transform(registerParam);
        userInfoMapper.insert(userinfo);
        return this.transform(userinfo);
    }

    private UserInfoVO transform(UserInfo userInfo){
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setId(userInfo.getId());
        userInfoVO.setUsername(userInfo.getUsername());
        return userInfoVO;
    }

    private UserInfo transform(UserRegisterParam registerParam){
        UserInfo userInfo = new UserInfo();
        userInfo.setId(IdGeneratorSnowflake.generateId());
        userInfo.setUsername(registerParam.getUsername());
        userInfo.setPassword(this.getPassword(registerParam.getPassword()));
        userInfo.setAtTime(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        userInfo.setSalt(UUID.randomUUID().toString());
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
        redisTemplate.opsForValue().set(String.format("Auth:%s",userInfo.getId()),token,60*2, TimeUnit.MINUTES);
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setId(userInfo.getId());
        userInfoVO.setUsername(userInfo.getUsername());
        userInfoVO.setToken(token);
        return userInfoVO;
    }

}
