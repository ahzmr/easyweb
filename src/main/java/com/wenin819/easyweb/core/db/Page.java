package com.wenin819.easyweb.core.db;

import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 分页查询
 * Created by wenin819@gmail.com on 2014-10-07.
 */
public class Page<E> extends com.github.pagehelper.Page<E> {

    public Page() {
        super(1, 15, true);
    }

    public Page(int pageNum, int pageSize) {
        super(pageNum, pageSize);
    }

    public Page(int pageNum, int pageSize, boolean count) {
        super(pageNum, pageSize, count);
    }

    public Page(int pageNum, int pageSize, int total) {
        super(pageNum, pageSize, total);
    }

    public Page(RowBounds rowBounds, boolean count) {
        super(rowBounds, count);
    }

    public Page(RowBounds rowBounds, int total) {
        super(rowBounds, total);
    }
}
