package cn.iocoder.yudao.framework.common.sqlparse;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.Interval;

public interface AntlrUtil {

    static String getFullText(ParserRuleContext context) {
        if (context.start == null || context.stop == null || context.start.getStartIndex() < 0 || context.stop.getStopIndex() < 0)
            return context.getText();

        return context.start.getInputStream().getText(Interval.of(context.start.getStartIndex(), context.stop.getStopIndex()));
    }

}
