package cn.iocoder.yudao.framework.mybatis.dynamictable.annotation;

import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@TableName
public @interface DynamicTableName {

    /**
     * 实体对应的表名
     */
    @AliasFor(annotation = TableName.class)
    String value() default "";

    /**
     * schema
     * <p>
     * 配置此值将覆盖全局配置的 schema
     *
     * @since 3.1.1
     */
    @AliasFor(annotation = TableName.class)
    String schema() default "";

    /**
     * 是否保持使用全局的 tablePrefix 的值
     * <p> 只生效于 既设置了全局的 tablePrefix 也设置了上面 {@link #value()} 的值 </p>
     * <li> 如果是 false , 全局的 tablePrefix 不生效 </li>
     *
     * @since 3.1.1
     */
    @AliasFor(annotation = TableName.class)
    boolean keepGlobalPrefix() default false;

    /**
     * 实体映射结果集,
     * 只生效于 mp 自动注入的 method
     */
    @AliasFor(annotation = TableName.class)
    String resultMap() default "";

    /**
     * 是否自动构建 resultMap 并使用,
     * 只生效于 mp 自动注入的 method,
     * 如果设置 resultMap 则不会进行 resultMap 的自动构建并注入,
     * 只适合个别字段 设置了 typeHandler 或 jdbcType 的情况
     *
     * @since 3.1.2
     */
    @AliasFor(annotation = TableName.class)
    boolean autoResultMap() default false;

    /**
     * 需要排除的属性名
     *
     * @since 3.3.1
     */
    @AliasFor(annotation = TableName.class)
    String[] excludeProperty() default {};

    String tableNameHandler() default "default";

}
