package time.geekbang.netty.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author wangyc
 */
public final class IdUtil {
    private static final AtomicLong IDX = new AtomicLong();

    private IdUtil() {
        //no instance
    }

    public static long nextId() {
        return IDX.incrementAndGet();
    }
}
