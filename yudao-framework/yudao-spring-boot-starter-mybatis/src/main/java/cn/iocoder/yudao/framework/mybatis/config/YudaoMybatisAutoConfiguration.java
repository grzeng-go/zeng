package cn.iocoder.yudao.framework.mybatis.config;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.mybatis.core.handler.DefaultDBFieldHandler;
import cn.iocoder.yudao.framework.mybatis.dynamictable.TableNameHandlerHolder;
import cn.iocoder.yudao.framework.mybatis.dynamictable.annotation.DynamicTableScan;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.extension.incrementer.H2KeyGenerator;
import com.baomidou.mybatisplus.extension.incrementer.KingbaseKeyGenerator;
import com.baomidou.mybatisplus.extension.incrementer.OracleKeyGenerator;
import com.baomidou.mybatisplus.extension.incrementer.PostgreKeyGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Map;

/**
 * MyBaits 配置类
 *
 * @author 芋道源码
 */
@AutoConfiguration
@DynamicTableScan("cn.iocoder.yudao.module.*.dal.dataobject")
@MapperScan(value = "${yudao.info.base-package}", annotationClass = Mapper.class,
        lazyInitialization = "${mybatis.lazy-initialization:false}") // Mapper 懒加载，目前仅用于单元测试
public class YudaoMybatisAutoConfiguration {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(@Autowired()Map<String, TableNameHandler> tableNameHandlerMap) {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor()); // 分页插件
        // 动态表名插件
        DynamicTableNameInnerInterceptor dynamicTabNameInterceptor = new DynamicTableNameInnerInterceptor();
        TableNameHandler tableNameHandler = new TableNameHandler() {
            @Override
            public String dynamicTableName(String sql, String tableName) {
                TableNameHandler handler = tableNameHandlerMap.get(TableNameHandlerHolder.get(tableName));
                return handler.dynamicTableName(sql, tableName);
            }
        };
        dynamicTabNameInterceptor.setTableNameHandler(tableNameHandler);
        mybatisPlusInterceptor.addInnerInterceptor(dynamicTabNameInterceptor);
        return mybatisPlusInterceptor;
    }

    @Bean(name = TableNameHandlerHolder.DEFAULT_HANDLER_NAME)
    public TableNameHandler defaultTableNameHandler() {
        return new TableNameHandler() {
            @Override
            public String dynamicTableName(String sql, String tableName) {
                return tableName;
            }
        };
    }

    @Bean
    public MetaObjectHandler defaultMetaObjectHandler(){
        return new DefaultDBFieldHandler(); // 自动填充参数类
    }

    @Bean
    @ConditionalOnProperty(prefix = "mybatis-plus.global-config.db-config", name = "id-type", havingValue = "INPUT")
    public IKeyGenerator keyGenerator(ConfigurableEnvironment environment) {
        DbType dbType = IdTypeEnvironmentPostProcessor.getDbType(environment);
        if (dbType != null) {
            switch (dbType) {
                case POSTGRE_SQL:
                    return new PostgreKeyGenerator();
                case ORACLE:
                case ORACLE_12C:
                    return new OracleKeyGenerator();
                case H2:
                    return new H2KeyGenerator();
                case KINGBASE_ES:
                    return new KingbaseKeyGenerator();
            }
        }
        // 找不到合适的 IKeyGenerator 实现类
        throw new IllegalArgumentException(StrUtil.format("DbType{} 找不到合适的 IKeyGenerator 实现类", dbType));
    }

}
