package cn.iocoder.yudao.framework.mybatis.dynamictable.annotation;

import cn.iocoder.yudao.framework.mybatis.dynamictable.TableNameHandlerHolder;
import org.apache.ibatis.io.Resources;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.mybatis.spring.annotation.MapperScannerRegistrar;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.tokenizeToStringArray;

public class DynamicTableScannerRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicTableScannerRegistrar.class);

    private static final ResourcePatternResolver RESOURCE_PATTERN_RESOLVER = new PathMatchingResourcePatternResolver();
    private static final MetadataReaderFactory METADATA_READER_FACTORY = new CachingMetadataReaderFactory();

    private static String generateBaseBeanName(AnnotationMetadata importingClassMetadata, int index) {
        return importingClassMetadata.getClassName() + "#" + MapperScannerRegistrar.class.getSimpleName() + "#" + index;
    }



    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes annoAttrs = AnnotationAttributes
                .fromMap(importingClassMetadata.getAnnotationAttributes(DynamicTableScan.class.getName()));
        if (annoAttrs != null) {
            try {
                scanClasses(Arrays.stream(annoAttrs.getStringArray("basePackages")).filter(StringUtils::hasText)
                        .collect(Collectors.toList()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void scanClasses(List<String> packagePatternsList) throws IOException {
        for (String packagePatterns : packagePatternsList) {
            String[] packagePatternArray = tokenizeToStringArray(packagePatterns,
                    ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
            for (String packagePattern : packagePatternArray) {
                Resource[] resources = RESOURCE_PATTERN_RESOLVER.getResources(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                        + ClassUtils.convertClassNameToResourcePath(packagePattern) + "/**/*.class");
                for (Resource resource : resources) {
                    try {
                        ClassMetadata classMetadata = METADATA_READER_FACTORY.getMetadataReader(resource).getClassMetadata();
                        Class<?> clazz = Resources.classForName(classMetadata.getClassName());
                        DynamicTableName annotation = clazz.getAnnotation(DynamicTableName.class);
                        if (annotation != null) {
                            TableNameHandlerHolder.put(annotation.value(), annotation.tableNameHandler());
                        }
                    } catch (Throwable e) {
                        LOGGER.warn(() -> "Cannot load the '" + resource + "'. Cause by " + e.toString());
                    }
                }
            }
        }
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {

    }


    static class RepeatingRegistrar extends DynamicTableScannerRegistrar {
        /**
         * {@inheritDoc}
         */
        @Override
        public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
            AnnotationAttributes mapperScansAttrs = AnnotationAttributes
                    .fromMap(importingClassMetadata.getAnnotationAttributes(MapperScans.class.getName()));
            if (mapperScansAttrs != null) {
                AnnotationAttributes[] annotations = mapperScansAttrs.getAnnotationArray("value");
                for (int i = 0; i < annotations.length; i++) {
//                    registerBeanDefinitions(importingClassMetadata, annotations[i], registry,
//                            generateBaseBeanName(importingClassMetadata, i));
                }
            }
        }
    }
}
