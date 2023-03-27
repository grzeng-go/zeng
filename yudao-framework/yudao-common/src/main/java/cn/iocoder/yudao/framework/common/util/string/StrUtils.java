package cn.iocoder.yudao.framework.common.util.string;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字符串工具类
 *
 * @author 芋道源码
 */
public class StrUtils {

    public static String maxLength(CharSequence str, int maxLength) {
        return StrUtil.maxLength(str, maxLength - 3); // -3 的原因，是该方法会补充 ... 恰好
    }

    /**
     * 给定字符串是否以任何一个字符串开始
     * 给定字符串和数组为空都返回 false
     *
     * @param str      给定字符串
     * @param prefixes 需要检测的开始字符串
     * @since 3.0.6
     */
    public static boolean startWithAny(String str, Collection<String> prefixes) {
        if (StrUtil.isEmpty(str) || ArrayUtil.isEmpty(prefixes)) {
            return false;
        }

        for (CharSequence suffix : prefixes) {
            if (StrUtil.startWith(str, suffix, false)) {
                return true;
            }
        }
        return false;
    }

    public static List<Long> splitToLong(String value,  CharSequence separator) {
        long[] longs = StrUtil.splitToLong(value, separator);
        return Arrays.stream(longs).boxed().collect(Collectors.toList());
    }

    /**
     * 替换字符串中的子字符串
     *
     * @param input 输入字符串
     * @param startIndices 开始位置
     * @param endIndices 结束位置
     * @param replacements 替换字符串
     * @return 替换后的字符串
     */
    public static String replaceSubstrings(String input, int[] startIndices, int[] endIndices, String[] replacements) {
        // Check that the number of start indices, end indices, and replacements are equal.
        if (startIndices.length != endIndices.length || endIndices.length != replacements.length) {
            throw new IllegalArgumentException("Invalid number of start indices, end indices, or replacements.");
        }

        // Check that all start and end positions are within the bounds of the input string.
        for (int i = 0; i < startIndices.length; i++) {
            if (startIndices[i] < 0 || endIndices[i] > input.length() || startIndices[i] >= endIndices[i]) {
                throw new IllegalArgumentException("Invalid start or end position.");
            }
        }

        // Construct the new string with the replaced substrings.
        StringBuilder builder = new StringBuilder();
        int lastIndex = 0;
        for (int i = 0; i < startIndices.length; i++) {
            builder.append(input.substring(lastIndex, startIndices[i]));
            builder.append(replacements[i]);
            lastIndex = endIndices[i];
        }
        builder.append(input.substring(lastIndex));

        return builder.toString();
    }

}
