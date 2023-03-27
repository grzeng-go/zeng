package cn.iocoder.yudao.framework.common.sqlparse;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.Interval;

public interface SqlLoader {

    // 查询条件占位符格式化
    String PLACEHOLDER_FORMATTER = " %s%s ";
    String WHERE_PLACEHOLDER_FORMATTER = " where 1 = 1 %s%s ";
    // 表-别名格式化
    String TABLE_ALIAS = "%s%s%s %s ";
    // 空格占位符
    String EMPTY_PLACEHOLDER = " ";

    /**
     * 解析sql
     * @param sql
     * @return
     */
    public SqlParse_old load(String sql);

    default String getTablePlaceholder(int i) {
        return SqlParseImpl_old.PLACEHOLDER + i;
    }

    default String getFullText(ParserRuleContext context) {
        if (context.start == null || context.stop == null || context.start.getStartIndex() < 0 || context.stop.getStopIndex() < 0)
            return context.getText();

        return context.start.getInputStream().getText(Interval.of(context.start.getStartIndex(), context.stop.getStopIndex()));
    }

}
