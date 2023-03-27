package cn.iocoder.yudao.framework.common.sqlparse.mysql;

import cn.hutool.core.lang.Tuple;
import cn.iocoder.yudao.framework.common.sqlparse.SqlLoader;
import cn.iocoder.yudao.framework.common.sqlparse.SqlParse_old;
import cn.iocoder.yudao.framework.common.sqlparse.SqlParseImpl_old;
import cn.iocoder.yudao.framework.common.sqlparse.mysql.gen.MySqlLexer;
import cn.iocoder.yudao.framework.common.sqlparse.mysql.gen.MySqlParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class MySqlLoader implements SqlLoader {
    @Override
    public SqlParse_old load(String sql) {
        //词法分析器
        MySqlLexer mySqlLexer = new MySqlLexer(CharStreams.fromString(sql.toUpperCase()));
        //词法符号的缓冲区,用于存储词法分析器生成的词法符号
        CommonTokenStream commonTokenStream = new CommonTokenStream(mySqlLexer);
        //新建一个语法分析器，处理词法符号缓冲区内容
        MySqlParser mySqlParser = new MySqlParser(commonTokenStream);
        //获取出selectStatement
        ParseTree tree = mySqlParser.selectStatement();

        SqlParseImpl_old sqlParse = new SqlParseImpl_old(new StringBuilder(), new HashMap<>());
        Stack<Integer> tableQueue = new Stack<>();
        recursive(tree, sqlParse, tableQueue, 0);
        return sqlParse;
    }

    private void recursive(ParseTree aRoot, SqlParseImpl_old buf, Stack<Integer> tableQueue, int level) {
        level++;

        if (aRoot instanceof MySqlParser.FromClauseContext && ((MySqlParser.FromClauseContext)aRoot).FROM() != null) {
            tableQueue.add(level);
        }

        // 增加表名占位符、记录表别名
        if (aRoot instanceof MySqlParser.AtomTableItemContext && ((MySqlParser.FromClauseContext)aRoot).FROM() != null) {
            MySqlParser.AtomTableItemContext atomTableItemContext = (MySqlParser.AtomTableItemContext) aRoot;
            String tableName = atomTableItemContext.tableName().getText();
            MySqlParser.UidContext aliasContext = atomTableItemContext.alias;
            String alias = "";
            if (aliasContext != null) {
                alias = aliasContext.getText();
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

        // 增加查询条件占位符
        if (aRoot instanceof MySqlParser.FromClauseContext && ((MySqlParser.FromClauseContext)aRoot).FROM() != null) {
            MySqlParser.FromClauseContext fromClauseContext = (MySqlParser.FromClauseContext) aRoot;
            String placeholder = fromClauseContext.whereExpr == null ? WHERE_PLACEHOLDER_FORMATTER : PLACEHOLDER_FORMATTER;
            buf.getNativeSql().append(String.format(placeholder, SqlParseImpl_old.PLACEHOLDER, level));
            tableQueue.pop();
        }
    }
}
