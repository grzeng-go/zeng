package cn.iocoder.yudao.framework.mybatis.dynamictable.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(DynamicTableScannerRegistrar.RepeatingRegistrar.class)
public @interface DynamicTableScans {
    DynamicTableScan[] value();
}
