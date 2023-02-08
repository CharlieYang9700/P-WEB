package com.mas.model.param;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserRegisterParam {
    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty("用户名")
    private String username;

    @NotBlank(message = "密码不能卫空空")
    @ApiModelProperty("密码")
    private String password;
}
