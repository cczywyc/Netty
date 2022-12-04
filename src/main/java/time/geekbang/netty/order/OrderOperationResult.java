package time.geekbang.netty.order;

import time.geekbang.netty.common.OperationResult;

/**
 * @author wangyc
 */
public class OrderOperationResult extends OperationResult {
    private int tableId;
    private String dish;
    private boolean complete;

    public OrderOperationResult(int tableId, String dish, boolean complete) {
        this.tableId = tableId;
        this.dish = dish;
        this.complete = complete;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public String getDish() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}
