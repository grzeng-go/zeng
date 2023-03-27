package cn.iocoder.yudao.framework.common.sqlparse.mssql;

import cn.hutool.core.lang.Tuple;
import cn.iocoder.yudao.framework.common.sqlparse.AntlrUtil;
import cn.iocoder.yudao.framework.common.sqlparse.Fragment.Fragment;
import cn.iocoder.yudao.framework.common.sqlparse.Fragment.SqlFragment;
import cn.iocoder.yudao.framework.common.sqlparse.mssql.gen.TSqlParser;
import cn.iocoder.yudao.framework.common.sqlparse.mssql.gen.TSqlParserBaseListener;
import lombok.Data;

import java.util.*;

@Data
public class MsSqlListener extends TSqlParserBaseListener {

    private Stack<Tuple> tableQueue;

    private int level;

    private int columnLevel;

    private SqlFragment sqlFragment;

    private Map<String, List<Fragment>> columns;

    public MsSqlListener() {
        this.tableQueue = new Stack<>();
        this.level = 0;
        this.columnLevel = 0;
        this.sqlFragment = new SqlFragment();
        columns = new HashMap<>();
    }

    /**
     * 如果要复用该类，则调用前，要先清除数据
     */
    public void clear() {
        this.level = 0;
        this.columnLevel = 0;
        this.tableQueue.clear();
        this.sqlFragment = new SqlFragment();
        columns.clear();
    }

    @Override
    public void enterQuery_specification(TSqlParser.Query_specificationContext ctx) {
        level++;
        if (this.columnLevel == 0) {
            this.columnLevel = level;
        }
        tableQueue.add(new Tuple(level, Fragment.buildWhere()));
    }

    @Override
    public void exitQuery_specification(TSqlParser.Query_specificationContext ctx) {
        boolean hasWhere = ctx.WHERE() == null ? false : true;

        Tuple pop = tableQueue.pop();
        Fragment fragment = pop.get(1);
        if (ctx.stop != null && ctx.stop.getStopIndex() > 0) {
            fragment.setHasWhere(hasWhere)
                            .setStartPos(ctx.stop.getStopIndex())
                                    .setRawSql(AntlrUtil.getFullText(ctx))
                                            .setEndPos(ctx.stop.getStopIndex());
            sqlFragment.add(fragment);
        }
    }

    @Override
    public void enterSelect_list_elem(TSqlParser.Select_list_elemContext ctx) {
        super.enterSelect_list_elem(ctx);
        // 获取select的字段, 目前只获取*和具体的字段，其他暂时忽略
        if (level == columnLevel && ctx.start != null && ctx.stop != null && ctx.stop.getStopIndex() > 0) {
            Fragment fragment = Fragment.buildColumn();
            fragment.setRawSql(AntlrUtil.getFullText(ctx));
            if (ctx.asterisk() != null) {
                fragment.setStar(true)
                        .setStartPos(ctx.start.getStartIndex())
                        .setEndPos(ctx.stop.getStopIndex());
            }

            String tableAlias = null;
            if (ctx.expression_elem() != null && ctx.expression_elem().expression() != null && ctx.expression_elem().expression().full_column_name() != null) {
                TSqlParser.Full_column_nameContext fullColumnNameContext = ctx.expression_elem().expression().full_column_name();

                fragment.setStartPos(fullColumnNameContext.start.getStartIndex())
                        .setEndPos(fullColumnNameContext.stop.getStopIndex())
                        .setRawSql(AntlrUtil.getFullText(fullColumnNameContext))
                        .setStar(false);

                TSqlParser.Id_Context columnName = fullColumnNameContext.column_name;
                if (columnName != null) {
                    fragment.setColumn(columnName.getText());
                }

                TSqlParser.Full_table_nameContext full_table_nameContext = fullColumnNameContext.full_table_name();
                if (full_table_nameContext != null && full_table_nameContext.start != null && full_table_nameContext.stop != null) {
                    tableAlias = AntlrUtil.getFullText(full_table_nameContext);
                }
            }

            if (fragment.getStartPos() < 0 || fragment.getEndPos() <= 0) {
                return;
            }
            sqlFragment.add(fragment);
            if (tableAlias != null) {
                List<Fragment> fragments = columns.get(tableAlias);
                if (fragments == null) {
                    fragments = new ArrayList<>();
                    columns.put(tableAlias, fragments);
                }
                fragments.add(fragment);
            }
        }
    }

    @Override
    public void enterTable_source_item(TSqlParser.Table_source_itemContext ctx) {
        super.enterTable_source_item(ctx);

        TSqlParser.Full_table_nameContext tableNameContext = ctx.full_table_name();
        if (tableNameContext == null || tableNameContext.start == null || tableNameContext.start.getStartIndex() < 0 || tableNameContext.stop == null || tableNameContext.stop.getStopIndex() < 0) {
            return;
        }

        String tableName = tableNameContext.getText();
        TSqlParser.As_table_aliasContext asTableAliasContext = ctx.as_table_alias();
        String alias = "";
        if (asTableAliasContext != null) {
            alias = asTableAliasContext.table_alias().getText();
        }

        // 维护where的tableAliasMap
        Tuple tuple = tableQueue.peek();
        Fragment fragment = tuple.get(1);
        fragment.addTableAlias(tableName, alias);

        // 维护column的tableAliasMap
        if (alias != null && alias != "" && Integer.compare(columnLevel, tuple.get(0)) == 0) {
            List<Fragment> fragments = columns.get(alias);
            if (fragments != null && !fragments.isEmpty()) {
                for (Fragment column : fragments) {
                    Map<String, String> tableAliasMap = column.getTableAliasMap();
                    tableAliasMap.put(tableName, alias);
                }
            }
        }

        // 保存tableFragment
        Fragment tableFragment = Fragment.buildTable(tableNameContext.start.getStartIndex(), tableNameContext.stop.getStopIndex());
        tableFragment.setRawSql(tableName)
                        .addTableAlias(tableName, alias);
        sqlFragment.add(tableFragment);
    }
}
