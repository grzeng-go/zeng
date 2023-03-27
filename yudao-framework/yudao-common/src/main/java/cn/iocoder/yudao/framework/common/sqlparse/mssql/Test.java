package cn.iocoder.yudao.framework.common.sqlparse.mssql;

import cn.iocoder.yudao.framework.common.sqlparse.Fragment.SqlFragment;
import cn.iocoder.yudao.framework.common.sqlparse.mssql.gen.TSqlLexer;
import cn.iocoder.yudao.framework.common.sqlparse.mssql.gen.TSqlParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class Test {

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
        walker.walk(listener,tree );
        String fullText = getFullText((ParserRuleContext) tree);
        StringBuilder sb = new StringBuilder(fullText);
        SqlFragment sqlFragment = listener.getSqlFragment();
        System.out.println("fullText = " + sqlFragment.toString());

    }

    public static String getFullText(ParserRuleContext context) {
        if (context.start == null || context.stop == null || context.start.getStartIndex() < 0 || context.stop.getStopIndex() < 0)
            return context.getText();

        return context.start.getInputStream().getText(Interval.of(context.start.getStartIndex(), context.stop.getStopIndex()));
    }

}
