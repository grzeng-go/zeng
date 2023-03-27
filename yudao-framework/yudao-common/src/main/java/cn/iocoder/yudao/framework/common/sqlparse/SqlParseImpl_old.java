package cn.iocoder.yudao.framework.common.sqlparse;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Tuple;
import cn.hutool.core.map.MapUtil;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class SqlParseImpl_old implements SqlParse_old {

    public static final String PLACEHOLDER = "$PLACEHOLDER$";
    public static final String TABLE_LEFT = "$TABLE_LEFT$";
    public static final String TABLE_RIGHT = "$TABLE_RIGHT$";
    public static final String ALIAS = "$ALIAS$";

    public static final String ALIAS_FORMAT = "%S.";

    public static final String AND = "AND";
    public static final String OR = "OR";

    public static final String COL_FORMAT = "%s %s %s";
    public static final String AND_LEFT = " and (";
    public static final String AND_RIGHT = " ) ";
    public static final String SPACE = " ";
    public static final String EMPTY = "";
    public static final String LEFT = " (";
    public static final String RIGHT = ") ";
    public static final String OR_LEFT = " OR (";

    /**
     * 解析后的sql
     */
    @Getter
    private StringBuilder nativeSql;

    /**
     * tuple存嵌套层级-表名-别名
     */
    @Getter
    public Map<String, List<Tuple>> tuples;

    public Map<String, String> whereExprTemp;

    public Map<String, String> tableTemp;

    public SqlParseImpl_old(StringBuilder nativeSql, Map<String, List<Tuple>> tuples) {
        this.tableTemp = new LinkedHashMap<>();
        this.whereExprTemp = new HashMap<>();
    }

    public static SqlParse_old empty(String sql) {
        SqlParseImpl_old sqlParse = new SqlParseImpl_old(new StringBuilder(sql), null);
        return sqlParse;
    }

    @Override
    public SqlParse_old replaceTableName(String oldTableName, String newTableName) {
        if (StringUtils.isNotBlank(oldTableName) && StringUtils.isNotBlank(newTableName)) {
            tableTemp.put(TABLE_LEFT + oldTableName.toUpperCase() + TABLE_RIGHT, newTableName);
        }
        return this;
    }

    @Override
    public SqlParse_old addWhereExpr(Map<String, Tuple> whereExpr) {
        if (MapUtil.isNotEmpty(whereExpr)) {
            whereExpr.forEach((k, v) -> {
                if (StringUtils.isBlank(k) || v == null) {
                    return;
                }
                String expr = v.get(1);
                if (StringUtils.isNotBlank(expr)) {
                    String op = v.get(0);
                    if (StringUtils.isBlank(op)) {
                        op = AND;
                    }
                    String finalOp = op;
                    whereExprTemp.merge(k.toUpperCase(), expr, (v1, v2) -> String.format(COL_FORMAT, v1, finalOp, v2));
                }
            });
        }
        return this;
    }

    @Override
    public SqlParse_old addWhereExpr(String table, Tuple whereExpr) {
        if (StringUtils.isBlank(table) || whereExpr == null) {
            return this;
        }
        String expr = whereExpr.get(1);
        if (StringUtils.isNotBlank(expr)) {
            String op = whereExpr.get(0);
            if (StringUtils.isBlank(op)) {
                op = AND;
            }
            String finalOp = op;
            whereExprTemp.merge(table.toUpperCase(), expr, (v1, v2) -> String.format(COL_FORMAT, v1, finalOp, v2));
        }
        return this;
    }

    @Override
    public String sql() {
        Map<String, String> params = new LinkedHashMap<>();
        if (MapUtil.isNotEmpty(tuples)) {
            tuples.forEach((k, v) -> {
                if (CollectionUtil.isNotEmpty(v)) {
                    AtomicBoolean flag = new AtomicBoolean(true);
                    String expr = params.get(k);
                    if (StringUtils.isBlank(expr)) {
                        expr = AND_LEFT;
                        params.put(k, expr);
                    }
                    v.forEach(t -> {
                        String value = whereExprTemp.get(t.get(0));
                        if (StringUtils.isNotBlank(value)) {
                            String alias = t.get(1);
                            alias = StringUtils.isBlank(alias) ? alias : String.format(ALIAS_FORMAT, alias);
                            value = StringUtils.replace(value, ALIAS, alias);
                            params.merge(k, value, (v1, v2) -> {
                                String left = OR_LEFT;
                                if (AND_LEFT.equals(v1)) {
                                    left = LEFT;
                                }
                                return v1 + left + v2 + RIGHT;
                            });
                            flag.set(false);
                        }
                    });
                    if (flag.get()) {
                        params.put(k, EMPTY);
                    } else {
                        expr = params.get(k);
                        if (StringUtils.isNotBlank(expr)) {
                            expr = AND_RIGHT;
                            params.merge(k, expr, (v1, v2) -> v1 + v2);
                        }
                    }
                } else {
                    params.put(k, EMPTY);
                }
            });
        }

        String sql = StringUtils.replaceEach(this.nativeSql.toString(), params.keySet().toArray(new String[0]), params.values().toArray(new String[0]));
        sql = StringUtils.replaceEach(sql, tableTemp.keySet().toArray(new String[0]), tableTemp.values().toArray(new String[0]));
        sql = StringUtils.replace(sql, TABLE_LEFT, EMPTY);
        sql = StringUtils.replace(sql, TABLE_RIGHT, EMPTY);
        // SqlParseImpl会缓存，但是每次要插入的查询条件，要清掉
        tableTemp.clear();
        whereExprTemp.clear();
        return sql;
    }

}
