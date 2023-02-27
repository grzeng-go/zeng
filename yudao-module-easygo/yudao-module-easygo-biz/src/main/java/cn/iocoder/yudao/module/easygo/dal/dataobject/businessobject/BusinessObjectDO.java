package cn.iocoder.yudao.module.easygo.dal.dataobject.businessobject;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 业务对象 DO
 *
 * @author 芋道源码
 */
@TableName("easygo_business_object")
@KeySequence("easygo_business_object_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessObjectDO extends BaseDO {

    /**
     * 业务对象ID
     */
    @TableId
    private Long id;
    /**
     * 业务对象名称
     */
    private String name;
    /**
     * 业务对象实体名称
     */
    private String boName;
    /**
     * 业务对象对应表名
     */
    private String tableName;
    /**
     * 数据来源
     */
    private String datasource;
    /**
     * 显示顺序
     */
    private Integer sort;
    /**
     * 后端代码路径
     */
    private Long javaPath;
    /**
     * 前端代码路径
     */
    private String vuePath;
    /**
     * 关联流程定义
     */
    private String processId;

}
