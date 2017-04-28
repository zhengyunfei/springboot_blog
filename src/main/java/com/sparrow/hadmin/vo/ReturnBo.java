package com.sparrow.hadmin.vo;

/**
 * Created by 贤云 on 2017/4/28.
 * 封装返回数据类型
 */
public class ReturnBo implements java.io.Serializable {
    private Object Rows;//结果集
    private long Total;//总数

    public Object getRows() {
        return Rows;
    }

    public void setRows(Object Rows) {
        this.Rows = Rows;
    }

    public long getTotal() {
        return Total;
    }

    public void setTotal(long Total) {
        this.Total = Total;
    }
}
