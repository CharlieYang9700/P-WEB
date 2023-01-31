package com.mas.mybatis;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Pager<T> implements IPage<T> {

    private static final long serialVersionUID = -9017115272300236030L;
    private final static long DEFAULT_LIMIT = 10;
    private final static long DEFAULT_PAGE = 0;
    /**
     * 总数
     */
    private long total;

    /**
     * 总页数
     */
    private long pages;

    /**
     * 当前页数
     */
    private long page;

    /**
     * 页数
     */
    @JsonIgnore
    private long size;

    /**
     * 数据
     */
    private List<T> items;

    @JsonIgnore
    private boolean optimizeCountSql;

    private List<OrderItem> orders;

    private boolean isSearchCount;

    public Pager() {
        this.items = Collections.emptyList();
        this.total = 0L;
        this.size = DEFAULT_LIMIT;
        this.page = DEFAULT_PAGE;
        this.optimizeCountSql = true;
        this.orders = new ArrayList<>();
        this.isSearchCount = true;
    }
    @Override
    public List<OrderItem> orders() {
        return this.orders;
    }

    @Override
    public List<T> getRecords() {
        if (Objects.isNull(this.items)) {
            return Collections.emptyList();
        }
        return this.items;
    }

    @Override
    public IPage<T> setRecords(List<T> records) {
        return null;
    }

    @Override
    public long getTotal() {
        return 0;
    }

    @Override
    public IPage<T> setTotal(long total) {
        return null;
    }

    @Override
    public long getSize() {
        return 0;
    }

    @Override
    public IPage<T> setSize(long size) {
        return null;
    }

    @Override
    public long getCurrent() {
        return 0;
    }

    @Override
    public IPage<T> setCurrent(long current) {
        return null;
    }
}
