package com.mas.mybatis;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.*;
import java.util.function.Function;

/**
 * 分页信息
 *
 * @author ping
 * @date 2021/3/25
 * @since V1.0.1
 */

@Data
@EqualsAndHashCode(callSuper = false)
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

    public Pager(IPage<?> iPage) {
        this(iPage, Collections.emptyList());
    }

    public Pager(IPage<?> iPage, List<T> records) {
        this(iPage.getCurrent(), iPage.getSize(), iPage.getTotal(), iPage.isSearchCount(), iPage.orders());
        this.items = records;
    }

    public <V> Pager(IPage<V> page, Function<V, T> resolver) {
        this.size = page.getSize();
        this.page = page.getCurrent();
        this.optimizeCountSql = page.optimizeCountSql();
        this.orders = page.orders();
        this.total = page.getTotal();
        this.isSearchCount = page.isSearchCount();
        if (isNotEmpty(page.getRecords())) {
            int size = page.getRecords().size();
            this.items = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                this.items.add(resolver.apply(page.getRecords().get(i)));
            }
        } else {
            this.items = Collections.emptyList();
        }
    }

    public Pager(long current, long size) {
        this(current, size, 0L, true, new ArrayList<>());
    }

    public Pager(long current, long size, Long total) {
        this(current, size, total, true, new ArrayList<>());
    }

    public Pager(long current, long size, boolean isSearchCount) {
        this(current, size, 0L, isSearchCount, new ArrayList<>());
    }

    public Pager(long page, long size, Long total, boolean isSearchCount, List<OrderItem> orders) {
        // pageParam.getPage(), pageParam.getLimit(), 0L, pageParam.isSearchCount(),  pageParam.getOrders()
        this.items = Collections.emptyList();
        this.page = page;
        this.optimizeCountSql = true;
        if (page < DEFAULT_PAGE) {
            this.page = DEFAULT_PAGE;
        }
        this.orders = orders;
        this.size = size;
        this.total = total;
        this.isSearchCount = isSearchCount;
    }

    public static <T> Pager<T> transform(PageParam pageParam) {
        return new Pager<>(pageParam.getPage(), pageParam.getLimit(), 0L, pageParam.isSearchCount(),
                pageParam.getOrders());
    }

    public static <T, R> Pager<R> transform(Pager<T> params) {
        Pager<R> pager = new Pager<>();
        pager.setCurrent(params.getCurrent());
        pager.setSize(params.getSize());
        pager.setTotal(params.getTotal());
        pager.setPage(params.getPage());
        pager.setPages(params.getPages());
        pager.setItems(new ArrayList<>(10));
        return pager;
    }

    public static <T, R> Pager<R> transform(Pager<T> params, Function<T, R> transform) {
        Pager<R> pager = new Pager<>();
        pager.setCurrent(params.getCurrent());
        pager.setSize(params.getSize());
        pager.setTotal(params.getTotal());
        pager.setPage(params.getPage());
        pager.setPages(params.getPages());
        pager.setItems(new ArrayList<>(10));
        if (Pager.isEmpty(params.getRecords())) {
            return pager;
        }
        pager.setItems(Pager.transform(params.getRecords(), transform));
        return pager;
    }

    public static <T, R> List<R> transform(List<T> values, Function<T, R> transform) {
        if (Pager.isEmpty(values)) {
            return new ArrayList<>(10);
        }
        List<R> result = new ArrayList<>(values.size());
        values.forEach(value -> result.add(transform.apply(value)));
        return result;
    }

    private static boolean isEmpty(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    public boolean hasPrevious() {
        return this.page > 1L;
    }

    public boolean hasNext() {
        return this.page < this.getPages();
    }

    @JsonIgnore
    @Override
    public List<T> getRecords() {
        if (Objects.isNull(this.items)) {
            return Collections.emptyList();
        }
        return this.items;
    }

    @Override
    public Pager<T> setRecords(List<T> records) {
        this.items = records;
        return this;
    }

    public List<T> getItems() {
        return getRecords();
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public Pager<T> setAndReturn(List<T> items) {
        this.items = items;
        return this;
    }

    @Override
    public long getTotal() {
        return this.total;
    }

    @Override
    public Pager<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    @Override
    public long getSize() {
        return this.size;
    }

    @Override
    public Pager<T> setSize(long size) {
        this.size = size;
        return this;
    }

    @JsonIgnore
    @Override
    public long getCurrent() {
        return this.page;
    }

    @Override
    public Pager<T> setCurrent(long current) {
        this.page = current;
        return this;
    }

    public long getPage() {
        return getCurrent();
    }

    @JsonIgnore
    @Override
    public boolean optimizeCountSql() {
        return this.optimizeCountSql;
    }

    public Pager<T> setOptimizeCountSql(boolean optimizeCountSql) {
        this.optimizeCountSql = optimizeCountSql;
        return this;
    }

    @Override
    public long getPages() {
        if (this.getSize() == 0L) {
            return 0L;
        } else {
            long pages = this.getTotal() / this.getSize();
            if (this.getTotal() % this.getSize() != 0L) {
                ++pages;
            }
            return pages;
        }
    }

    @Override
    public Pager<T> setPages(long pages) {
        this.pages = pages;
        return this;
    }

    public Pager<T> addOrder(OrderItem... items) {
        this.orders.addAll(Arrays.asList(items));
        return this;
    }

    public Pager<T> addOrder(List<OrderItem> items) {
        this.orders.addAll(items);
        return this;
    }

    @Override
    public List<OrderItem> orders() {
        return this.getOrders();
    }

    public List<OrderItem> getOrders() {
        return orders;
    }

    @Override
    public boolean isSearchCount() {
        return isSearchCount;
    }

    private boolean isNotEmpty(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }
}
