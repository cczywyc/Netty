package time.geekbang.netty.util;

import com.google.gson.Gson;

/**
 * @author wangyc
 */
public final class JsonUtil {
    private static final Gson GSON = new Gson();

    private JsonUtil() {
        //no instance
    }

    public static <T> T fromJson(String jsonStr, Class<T> clazz) {
        return GSON.fromJson(jsonStr, clazz);
    }

    public static String toJson(Object object) {
        return GSON.toJson(object);
    }
}
