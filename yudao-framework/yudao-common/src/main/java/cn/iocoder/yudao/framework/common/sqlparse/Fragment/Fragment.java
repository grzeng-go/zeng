package cn.iocoder.yudao.framework.common.sqlparse.Fragment;

import cn.hutool.core.lang.Tuple;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * sql片段
 */
@Data
public class Fragment implements Comparable<Fragment> {

    /**
     * 开始位置
     */
    private int startPos;

    /**
     * 结束位置
     */
    private int endPos;

    /**
     * 类型
     */
    private FragmentType type;

    /**
     * 是否有where条件语句，仅当type为Where时，才有意义
     */
    private boolean hasWhere;

    /**
     * 字段是否是*，仅当type为Column时，才有意义
     */
    private boolean isStar;

    /**
     * 字段名
     */
    private String column;

    /**
     * 表别名
     */
    private Map<String, String> tableAliasMap;

    /**
     * 原始sql
     */
    private String rawSql;

    public static Fragment buildTable(int startPos, int endPos) {
        return new Fragment(startPos, endPos, FragmentType.TABLE, new HashMap<>());
    }

    public static Fragment buildColumn() {
        return new Fragment(0, 0, FragmentType.COLUMN, new HashMap<>());
    }
    public static Fragment buildColumn(int startPos, int endPos) {
        return new Fragment(startPos, endPos, FragmentType.COLUMN, new HashMap<>());
    }

    public static Fragment buildWhere() {
        return new Fragment(0, 0, FragmentType.WHERE, new HashMap<>());
    }

    public static Fragment buildWhere(int startPos, int endPos) {
        return new Fragment(startPos, endPos, FragmentType.WHERE, new HashMap<>());
    }

    public Fragment(int startPos, int endPos, FragmentType type, Map<String, String> tableAliasMap) {
        this.startPos = startPos;
        this.endPos = endPos;
        this.type = type;
        this.tableAliasMap = tableAliasMap;
    }

    public void addTableAlias(String tableName, String alias) {
        this.tableAliasMap.put(tableName, alias);
    }

    /**
     * 获取表名
     * @return 表名
     */
    public String getTableName() {
        if(FragmentType.TABLE.equals(this.type)) {
            return this.tableAliasMap.keySet().stream().findFirst().orElse("");
        } else {
            return "";
        }
    }

    public Tuple getColumnTableAlias() {
        if (FragmentType.COLUMN.equals(this.type)) {
            String table = this.tableAliasMap.keySet().stream().findFirst().orElse(null);
            if (table == null) {
                return null;
            } else {
                return new Tuple(table, this.tableAliasMap.get(table));
            }
        } else {
            return null;
        }
    }

    @Override
    public int compareTo(Fragment o) {
        // 校验是否有交集，有交集则抛出异常
        if (hasIntersection(this.startPos, this.endPos, o.startPos, o.endPos)) {
            throw new IllegalArgumentException("sql片段有交集");
        }
        return Integer.compare(this.startPos, o.startPos);
    }

    /**
     * 判断两个区间是否有交集
     *
     * @param a1 区间1的起始值
     * @param b1 区间1的结束值
     * @param a2 区间2的起始值
     * @param b2 区间2的结束值
     * @return 是否有交集
     */
    public static boolean hasIntersection(int a1, int b1, int a2, int b2) {
        if (a1 > b1 || a2 > b2) {
            return true;
        }

        return Math.max(a1, a2) <= Math.min(b1, b2);
    }
}