package time.geekbang.netty.order;

import lombok.Data;
import lombok.extern.java.Log;
import time.geekbang.netty.common.Operation;
import time.geekbang.netty.common.OperationResult;

/**
 * @author wangyc
 */
@Data
@Log
public class OrderOperation extends Operation {
    private int tableId;
    private String dish;

    public OrderOperation(int tableId, String dish) {
        this.tableId = tableId;
        this.dish = dish;
    }


    @Override
    public OperationResult execute() {
        log.info("order's executing startup with orderRequest: ");
        //execute order logic
        log.info("order's executing complete");
        return new OrderOperationResult(tableId, dish, true);
    }
}
