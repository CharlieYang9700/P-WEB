package com.mas.model.vo;

import com.mas.annotation.Desensitization;
import com.mas.annotation.plan.PhonePlan;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户信息")
public class UserInfoVO {
    @ApiModelProperty("用户id")
    @Desensitization(method = PhonePlan.class)
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    private String phone;

    private String token;
}
