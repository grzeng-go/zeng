package cn.iocoder.yudao.framework.common.sqlparse.Fragment;

/**
 * sql解析器
 */
public interface SqlParse {

        /**
        * 解析sql
        *
        * @param sql sql
        * @return sql片段
        */
        SqlFragment parse(String sql);
}
