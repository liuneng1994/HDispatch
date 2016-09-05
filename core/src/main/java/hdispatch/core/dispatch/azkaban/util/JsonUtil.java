package hdispatch.core.dispatch.azkaban.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

/**
 * Created by 刘能 on 2016/9/1.
 */
public class JsonUtil {
    private static Gson gson = new GsonBuilder().create();

    public static <T, P> Map<T, P> parseMap(String map, Class<T> keyType, Class<P> value) {
        return gson.fromJson(map, new TypeToken<Map<T, P>>() {
        }.getType());
    }

}
