package cn.iocoder.yudao.framework.common.sqlparse.Fragment;

import cn.hutool.core.lang.Tuple;
import cn.iocoder.yudao.framework.common.sqlparse.mssql.MsSqlListener;
import cn.iocoder.yudao.framework.common.sqlparse.mssql.gen.TSqlLexer;
import cn.iocoder.yudao.framework.common.sqlparse.mssql.gen.TSqlParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.HashMap;
import java.util.Map;

/**
 * sql片段处理服务
 */
public class SqlService {

    private FragmentHandler fragmentHandler;

    private SqlParse sqlParse;

    private Map<String, SqlFragment> map;

    public SqlService(FragmentHandler fragmentHandler, SqlParse sqlParse) {
        this.fragmentHandler = fragmentHandler;
        this.sqlParse = sqlParse;
        this.map = new HashMap<>();
    }

    public String parse(String sql) {
        SqlFragment sqlFragment = parseSql(sql);
        if (sqlFragment == null || sqlFragment.isEmpty()) {
            return sql;
        }

        StringBuilder newSql = new StringBuilder();
        int lastIndex = 0;
        Fragment[] fragments = sqlFragment.toArray();
        for (Fragment fragment : fragments) {
            String replaceSql = "";
            boolean needReplace = false;
            switch (fragment.getType()) {
                case TABLE:
                    replaceSql = fragmentHandler.handleTable(fragment.getTableName());
                    needReplace = true;
                    break;
                case COLUMN:
                    Tuple columnTableAlias = fragment.getColumnTableAlias();
                    if (!fragment.isStar() && columnTableAlias != null && fragment.getColumn() != null && !"".equals(fragment.getColumn())) {
                        replaceSql = fragmentHandler.handleCF(columnTableAlias.get(0), columnTableAlias.get(1), fragment.getColumn());
                        needReplace = true;
                    }
                    break;
                case WHERE:
                    replaceSql = fragmentHandler.handleWF(fragment.getTableAliasMap(), fragment.isHasWhere());
                    needReplace = true;
                    break;
            }
            if(!needReplace || replaceSql == null || "".equals(replaceSql)) {
                // where是插入，其rawSql是整个同级select * from test where 1=1 语句
                if (FragmentType.WHERE.equals(fragment.getType())) {
                    replaceSql = "";
                } else {
                    replaceSql = fragment.getRawSql();
                }
            }

            int copyEnd = fragment.getStartPos();
            // where是插入，其他是替换
            if(FragmentType.WHERE.equals(fragment.getType())) {
                copyEnd = copyEnd + 1;
                replaceSql = " " + replaceSql;
            }

            newSql.append(sql.substring(lastIndex, copyEnd))
                    .append(replaceSql);
            lastIndex = fragment.getEndPos() + 1;
        }
        newSql.append(sql.substring(lastIndex));
        return newSql.toString();
    }

    private SqlFragment parseSql(String sql) {
        if (sql == null || "".equals(sql)) {
            return null;
        }
        SqlFragment sqlFragment = map.get(sql);
        if (sqlFragment == null) {
            sqlFragment = sqlParse.parse(sql);
            map.put(sql, sqlFragment);
        }
        return sqlFragment;
    }

    public static void main(String[] args) {
        String sql = " SELECT DISTINCT PP.LASTNAME, PP.FIRSTNAME\n" +
                "FROM PERSON.PERSON PP\n" +
                "         JOIN HUMANRESOURCES.EMPLOYEE E ON E.BUSINESSENTITYID = PP.BUSINESSENTITYID\n" +
                "WHERE PP.BUSINESSENTITYID IN (SELECT SALESPERSONID\n" +
                "                              FROM SALES.SALESORDERHEADER\n" +
                "                              WHERE SALESORDERID IN\n" +
                "                                    (SELECT SALESORDERID\n" +
                "                                     FROM SALES.SALESORDERDETAIL\n" +
                "                                     WHERE PRODUCTID IN\n" +
                "                                           (SELECT PRODUCTID\n" +
                "                                            FROM PRODUCTION.PRODUCT P\n" +
                "                                            WHERE PRODUCTNUMBER = 'BK-M68B-42'))); ";
        FragmentHandler handler = new FragmentHandler() {
            @Override
            public String handleWF(Map<String, String> tableAliasMap, boolean hasWhere) {
                if (tableAliasMap != null && !tableAliasMap.isEmpty()) {
                    String join = String.join(",", tableAliasMap.keySet());
                    return "$Where$" + join + "$Where$";
                }
                return null;
            }

            @Override
            public String handleCF(String table, String alias, String column) {
                return alias + "." + column + "$";
            }

            @Override
            public String handleTable(String table) {
                return table +"_2023";
            }
        };

        SqlParse sp = new SqlParse() {
            @Override
            public SqlFragment parse(String sql) {
                //词法分析器
                TSqlLexer tSqlLexer = new TSqlLexer(CharStreams.fromString(sql.toUpperCase()));
                //词法符号的缓冲区,用于存储词法分析器生成的词法符号
                CommonTokenStream commonTokenStream = new CommonTokenStream(tSqlLexer);
                //新建一个语法分析器，处理词法符号缓冲区内容
                TSqlParser tSqlParser = new TSqlParser(commonTokenStream);
                //获取出selectStatement
                ParseTree tree = tSqlParser.select_statement_standalone();

                ParseTreeWalker walker = new ParseTreeWalker();
                MsSqlListener listener = new MsSqlListener();
                walker.walk(listener, tree);
                return listener.getSqlFragment();
            }
        };

        SqlService sqlService = new SqlService(handler, sp);
        System.out.println("newSql = " + sqlService.parse(sql));
    }

}
