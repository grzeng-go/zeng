package cn.iocoder.yudao.framework.common.sqlparse.mssql;

import cn.hutool.core.lang.Tuple;
import cn.iocoder.yudao.framework.common.sqlparse.SqlLoader;
import cn.iocoder.yudao.framework.common.sqlparse.SqlParse_old;
import cn.iocoder.yudao.framework.common.sqlparse.SqlParseImpl_old;
import cn.iocoder.yudao.framework.common.sqlparse.mssql.gen.TSqlLexer;
import cn.iocoder.yudao.framework.common.sqlparse.mssql.gen.TSqlParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * 自己迭代，不适用antlr4自带的listener和visitor
 */
public class MsSqlLoader_old implements SqlLoader {
    @Override
    public SqlParse_old load(String sql) {
        //词法分析器
        TSqlLexer tSqlLexer = new TSqlLexer(CharStreams.fromString(sql.toUpperCase()));
        //词法符号的缓冲区,用于存储词法分析器生成的词法符号
        CommonTokenStream commonTokenStream = new CommonTokenStream(tSqlLexer);
        //新建一个语法分析器，处理词法符号缓冲区内容
        TSqlParser tSqlParser = new TSqlParser(commonTokenStream);
        //获取出selectStatement
        ParseTree tree = tSqlParser.select_statement_standalone();

        SqlParseImpl_old sqlParse = new SqlParseImpl_old(new StringBuilder(), new HashMap<>());
        Stack<Integer> tableQueue = new Stack<>();
        recursive(tree, sqlParse, tableQueue, 0);
        return sqlParse;
    }

    private void recursive(ParseTree aRoot, SqlParseImpl_old buf, Stack<Integer> tableQueue, int level) {
        level++;

        if (aRoot instanceof TSqlParser.Query_specificationContext && ((TSqlParser.Query_specificationContext)aRoot).FROM() != null) {
            tableQueue.add(level);
        }

        if (aRoot instanceof TSqlParser.Table_source_itemContext) {
            TSqlParser.Table_source_itemContext tableSourceItemContext = (TSqlParser.Table_source_itemContext) aRoot;
            String tableName = tableSourceItemContext.full_table_name().getText();
            TSqlParser.As_table_aliasContext asTableAliasContext = tableSourceItemContext.as_table_alias();
            String alias = "";
            if (asTableAliasContext != null) {
                alias = asTableAliasContext.table_alias().getText();
            }
            buf.getNativeSql().append(String.format(TABLE_ALIAS, SqlParseImpl_old.TABLE_LEFT, tableName, SqlParseImpl_old.TABLE_RIGHT, alias));
            Integer l = tableQueue.peek();
            List<Tuple> tupleList = buf.getTuples().get(getTablePlaceholder(l));
            if (tupleList == null) {
                tupleList = new ArrayList<>();
                buf.getTuples().put(getTablePlaceholder(l), tupleList);
            }
            tupleList.add(new Tuple(tableName, alias));
            return;
        }

        if (aRoot instanceof ParserRuleContext) {
            ParserRuleContext prc = (ParserRuleContext) aRoot;
            if (prc.children != null) {
                for (ParseTree child : prc.children) {
                    recursive(child, buf, tableQueue, level);
                }
            } else {
                buf.getNativeSql().append(getFullText(prc));
            }
        } else {
            buf.getNativeSql().append(aRoot.getText() + EMPTY_PLACEHOLDER);
        }

        if (aRoot instanceof TSqlParser.Query_specificationContext && ((TSqlParser.Query_specificationContext)aRoot).FROM() != null) {
            TSqlParser.Query_specificationContext querySpecificationContext = (TSqlParser.Query_specificationContext) aRoot;
            String placeholder = querySpecificationContext.WHERE() == null ? WHERE_PLACEHOLDER_FORMATTER : PLACEHOLDER_FORMATTER;
            buf.getNativeSql().append(String.format(placeholder, SqlParseImpl_old.PLACEHOLDER, level));
            tableQueue.pop();
        }
    }
}
