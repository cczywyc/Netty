package time.geekbang.netty.order;

import time.geekbang.netty.common.Operation;
import time.geekbang.netty.common.OperationResult;

/**
 * @author wangyc
 */
public class OrderOperation extends Operation {
    private int tableId;
    private String dish;

    public OrderOperation(int tableId, String dish) {
        this.tableId = tableId;
        this.dish = dish;
    }


    @Override
    public OperationResult execute() {
        System.out.println("order's executing startup with orderRequest: ");
        //execute order logic
        System.out.println("order's executing complete");
        return new OrderOperationResult(tableId, dish, true);
    }
}
