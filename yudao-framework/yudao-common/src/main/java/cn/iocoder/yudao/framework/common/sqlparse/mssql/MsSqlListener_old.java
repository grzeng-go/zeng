package cn.iocoder.yudao.framework.common.sqlparse.mssql;

import cn.hutool.core.lang.Tuple;
import cn.iocoder.yudao.framework.common.sqlparse.SqlParseImpl_old;
import cn.iocoder.yudao.framework.common.sqlparse.mssql.gen.TSqlParser;
import cn.iocoder.yudao.framework.common.sqlparse.mssql.gen.TSqlParserBaseListener;
import lombok.Data;

import java.util.*;

@Data
public class MsSqlListener_old extends TSqlParserBaseListener {

    // 查询条件占位符格式化
    public static final String PLACEHOLDER_FORMATTER = " %s%s ";
    public static final String WHERE_PLACEHOLDER_FORMATTER = " where 1 = 1 %s%s ";
    // 表-别名格式化
    public static final String TABLE_ALIAS = "%s%s%s %s ";
    // 空格占位符
    public static final String EMPTY_PLACEHOLDER = " ";

    private Stack<Integer> tableQueue;

    private int level;

    private Map<Integer, String> insertIdxMap;

    /**
     * tuple存嵌套层级-表名-别名
     */
    public Map<String, List<Tuple>> tuples;

    public MsSqlListener_old() {
        this.tableQueue = new Stack<>();
        this.level = 0;
        // 倒序，方便插入占位符
        this.insertIdxMap = new TreeMap<>((y, x) -> (x < y) ? -1 : ((x == y) ? 0 : 1));
        this.tuples = new HashMap<>();
    }

    /**
     * 如果要复用该类，则调用前，要先清除数据
     */
    public void clear() {
        this.level = 0;
        this.insertIdxMap.clear();
        this.tableQueue.clear();
        this.tuples.clear();
    }

    @Override
    public void enterQuery_specification(TSqlParser.Query_specificationContext ctx) {
        level++;
        tableQueue.add(level);
    }

    @Override
    public void exitQuery_specification(TSqlParser.Query_specificationContext ctx) {
        String placeholder = ctx.WHERE() == null ? WHERE_PLACEHOLDER_FORMATTER : PLACEHOLDER_FORMATTER;

        if (ctx.stop != null && ctx.stop.getStopIndex() > 0)
            insertIdxMap.put(ctx.stop.getStopIndex(), String.format(placeholder, SqlParseImpl_old.PLACEHOLDER, tableQueue.peek()));

        tableQueue.pop();
    }

    @Override
    public void enterTable_source_item(TSqlParser.Table_source_itemContext ctx) {
        super.enterTable_source_item(ctx);

        TSqlParser.Full_table_nameContext tableNameContext = ctx.full_table_name();
        if (tableNameContext == null || tableNameContext.start == null || tableNameContext.start.getStartIndex() < 0 || tableNameContext.stop == null || tableNameContext.stop.getStopIndex() < 0) {
            return;
        }

        insertIdxMap.put(tableNameContext.start.getStartIndex() - 1, SqlParseImpl_old.TABLE_LEFT);
        insertIdxMap.put(tableNameContext.stop.getStopIndex(), SqlParseImpl_old.TABLE_RIGHT);

        String tableName = tableNameContext.getText();
        TSqlParser.As_table_aliasContext asTableAliasContext = ctx.as_table_alias();
        String alias = "";
        if (asTableAliasContext != null) {
            alias = asTableAliasContext.table_alias().getText();
        }

        Integer l = tableQueue.peek();
        List<Tuple> tupleList = this.tuples.get(getTablePlaceholder(l));
        if (tupleList == null) {
            tupleList = new ArrayList<>();
            this.tuples.put(getTablePlaceholder(l), tupleList);
        }
        tupleList.add(new Tuple(tableName, alias));
    }

    private String getTablePlaceholder(int i) {
        return SqlParseImpl_old.PLACEHOLDER + i;
    }
}
