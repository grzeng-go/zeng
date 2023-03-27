package cn.iocoder.yudao.framework.common.sqlparse;

import cn.hutool.core.lang.Tuple;

import java.util.Map;

public interface SqlParse_old {

    /**
     * 替换表名
     * @param oldTableName
     * @param newTableName
     * @return
     */
    public SqlParse_old replaceTableName(String oldTableName, String newTableName);

    /**
     * 增加where条件
     * @param whereExpr 查询条件 示例  tableA: $alias$column = 'xxx'  $alias$ 表别名占位符
     * @return
     */
    public SqlParse_old addWhereExpr(Map<String, Tuple> whereExpr);

    /**
     * 增加where条件
     * @param table 查询条件
     * @param whereExpr 查询条件
     * @return
     */
    public SqlParse_old addWhereExpr(String table, Tuple whereExpr);

    /**
     * 获取最终的sql
     * @return
     */
    public String sql();

}
