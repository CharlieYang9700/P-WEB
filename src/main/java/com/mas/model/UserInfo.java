package com.mas.model;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@HeadRowHeight(value = 20)//表头行高
@ContentRowHeight(value = 10) //内容行高
@TableName("user")
public class UserInfo implements Serializable {
    @TableId("id")
    @ExcelProperty("用户id")
    private Long id;
    /**
     * 用户名
     */
    @ExcelProperty("用户名")
    private String username;
    /**
     * 密码
     */
    @ExcelIgnore
    private String password;
    /**
     * 加密的盐
     */
    private String salt;
    /**
     * 是否删除时间戳
     */
    @TableField("at_time")
    private Long atTime;

    /**
     *电话号码
     */
    @TableField("phone")
    private String phone;
}
