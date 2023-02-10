package com.mas.model.param;

import com.mas.mybatis.PageParam;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class UserInfoPageParam extends PageParam {
    private String username;
}
