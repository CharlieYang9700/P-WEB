package com.mas.mybatis;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页类
 *
 * @author ping
 * @date 2021/3/25
 * @since V1.0.1
 */
@ApiModel("分页类")
@Setter
public class PageParam implements Serializable {
    private static final long serialVersionUID = -9017115272300236039L;
    private final static Integer DEFAULT_SIZE = 10;
    private final static Integer DEFAULT_INDEX = 1;
    /**
     * 每页显示数量
     */
    @ApiModelProperty("每页显示数量")
    private long limit;
    /**
     * 页数
     */
    @ApiModelProperty("当前页数")
    private long page;

    @ApiModelProperty(hidden = true,
    position = 1000)
    private List<OrderItem> orders;

    @ApiModelProperty(hidden = true,
    position = 1000)
    private Boolean isSearchCount;

    public PageParam() {
        this.limit = DEFAULT_SIZE;
        this.page = DEFAULT_INDEX;
        this.orders = new ArrayList<>();
        this.isSearchCount = true;
    }
    @ApiModelProperty(hidden = true,
    position = 1000)
    public long getLimit() {
        if (this.limit <= 0) {
            this.limit = DEFAULT_SIZE;
        }
        return this.limit;
    }
    @ApiModelProperty(hidden = true,
    position = 1000)
    public long getPage() {
        if (this.page <= 0) {
            this.page = DEFAULT_INDEX;
        }
        return this.page;
    }
    @ApiModelProperty(hidden = true,
    position = 1000)
    public List<OrderItem> getOrders() {
        return orders == null ? new ArrayList<>() : orders;
    }
    @ApiModelProperty(hidden = true,
    position = 1000)
    public boolean isSearchCount() {
        return isSearchCount == null ? true : isSearchCount;
    }
}
