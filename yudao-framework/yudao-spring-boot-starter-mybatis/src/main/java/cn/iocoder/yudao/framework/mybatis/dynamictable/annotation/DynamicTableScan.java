package cn.iocoder.yudao.framework.mybatis.dynamictable.annotation;

import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(DynamicTableScannerRegistrar.class)
@Repeatable(DynamicTableScans.class)
public @interface DynamicTableScan {
    /**
     * Alias for the {@link #basePackages()} attribute. Allows for more concise annotation declarations e.g.:
     * {@code @MapperScan("org.my.pkg")} instead of {@code @MapperScan(basePackages = "org.my.pkg"})}.
     *
     * @return base package names
     */
    @AliasFor("basePackages")
    String[] value() default {};

    /**
     * Base packages to scan for MyBatis interfaces. Note that only interfaces with at least one method will be
     * registered; concrete classes will be ignored.
     *
     * @return base package names for scanning mapper interface
     */
    @AliasFor("value")
    String[] basePackages() default {};
}
