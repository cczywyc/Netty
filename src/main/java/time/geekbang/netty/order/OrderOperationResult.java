package time.geekbang.netty.order;

import lombok.Data;
import time.geekbang.netty.common.OperationResult;

/**
 * @author wangyc
 */
@Data
public class OrderOperationResult extends OperationResult {
    private int tableId;
    private String dish;
    private boolean complete;

    public OrderOperationResult(int tableId, String dish, boolean complete) {
        this.tableId = tableId;
        this.dish = dish;
        this.complete = complete;
    }
}
