package link.kongyu.erp.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/12/3
 */
public class MapUtils {


    public static <K, V, M extends Map<K, V>> M mergeMap(Map<K, V> source, M target) {
        target.putAll(source);
        return target;
    }

    @SuppressWarnings("unchecked")
    public static <K, V> HashMap<K, V> getMapInstance(Object... args) {
        int length = args.length;
        if (length % 2 != 0) {
            throw new IllegalArgumentException("参数长度必须是 2 的整数倍");
        }
        HashMap<K, V> hashMap = new HashMap<>(length / 2);
        for (int i = 0; i < length; i++) {
            hashMap.put((K) args[i], (V) args[++i]);
        }
        return hashMap;
    }
}
