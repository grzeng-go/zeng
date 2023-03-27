package cn.iocoder.yudao.framework.common.sqlparse.mssql;

import cn.iocoder.yudao.framework.common.sqlparse.SqlLoader;
import cn.iocoder.yudao.framework.common.sqlparse.SqlParse_old;
import cn.iocoder.yudao.framework.common.sqlparse.SqlParseImpl_old;
import cn.iocoder.yudao.framework.common.sqlparse.mssql.gen.TSqlLexer;
import cn.iocoder.yudao.framework.common.sqlparse.mssql.gen.TSqlParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.Map;

public class MsSqlLoader implements SqlLoader {
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

        ParseTreeWalker walker = new ParseTreeWalker();
        MsSqlListener_old listener = new MsSqlListener_old();
        walker.walk(listener,tree );
        String fullText = getFullText((ParserRuleContext) tree);
        StringBuilder sb = new StringBuilder(fullText);
        Map<Integer, String> insertIdxMap = listener.getInsertIdxMap();
        if (insertIdxMap != null && !insertIdxMap.isEmpty()) {
            for (Integer idx : insertIdxMap.keySet()) {
                sb.insert(idx, insertIdxMap.get(idx));
            }
        }
        return new SqlParseImpl_old(sb, listener.getTuples());
    }
}
