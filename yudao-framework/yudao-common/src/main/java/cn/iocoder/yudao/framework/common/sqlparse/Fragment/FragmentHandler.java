package cn.iocoder.yudao.framework.common.sqlparse.Fragment;

import java.util.Map;

/**
 * sql片段处理器
 */
public interface FragmentHandler {

    /**
     * 处理sql查询片段
     * @param tableAliasMap 表别名映射
     * @param hasWhere 是否有where语句，如果没有，要补充where关键字
     * @return
     */
    public String handleWF(Map<String, String> tableAliasMap, boolean hasWhere);

    /**
     * 处理sql查询字段片段，如果指定表的某个字段是敏感字段，可以用此方法进行处理成任意值，例如：1
     * @param table 表名
     * @param column 列名
     * @return
     */
    public String handleCF(String table, String alias, String column);

    /**
     * 处理sql表名片段，可以动态变更表名，例如：t_user_2020，t_user_2021
     * @param table 表名
     * @return
     */
    public String handleTable(String table);

}
