package cn.iocoder.yudao.framework.mybatis.dynamictable;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class TableNameHandlerHolder {

    private static final Map<String, String> TABLE_NAME_HANDLER_MAP = new HashMap<>();

    public static final String DEFAULT_HANDLER_NAME = "default";

    public static void put(String tableName, String handler) {
        TABLE_NAME_HANDLER_MAP.put(tableName, handler);
    }

    public static String get(String tableName) {
        String handlerName = TABLE_NAME_HANDLER_MAP.get(tableName);
        return StringUtils.isBlank(handlerName) ? DEFAULT_HANDLER_NAME : handlerName;
    }

}
