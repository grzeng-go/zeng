package cn.iocoder.yudao.framework.common.sqlparse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlParseUtils_old {

    private static final Logger LOG = LoggerFactory.getLogger(SqlParseUtils_old.class);

    private static SqlCache<String, SqlParse_old> cache;

    private static SqlLoader sqlLoader;

    // 初始化时设置缓存（非必要，设置会提升性能）
    public static void setCache(SqlCache<String, SqlParse_old> cache) {
        SqlParseUtils_old.cache = cache;
    }

    public static void setSqlLoader(SqlLoader loader) {
        SqlParseUtils_old.sqlLoader = loader;
    }


    /**
     * 解析sql，生成sql解析器（如果有设置缓存的话，会优先从缓存中去取）
     * @param sql
     * @return
     */
    public static SqlParse_old get(String sql) {
        if (cache != null) {
            try {
                return cache.get(sql, () -> sqlLoader.load(sql));
            } catch (Exception e) {
                LOG.error("获取SQL解析器缓存失败，", e);
                return SqlParseImpl_old.empty(sql);
            }
        } else {
            return sqlLoader.load(sql);
        }
    }

}
