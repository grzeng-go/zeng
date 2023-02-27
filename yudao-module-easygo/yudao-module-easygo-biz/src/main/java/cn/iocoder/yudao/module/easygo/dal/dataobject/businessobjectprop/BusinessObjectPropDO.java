package cn.iocoder.yudao.module.easygo.dal.dataobject.businessobjectprop;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 业务对象属性 DO
 *
 * @author 芋道源码
 */
@TableName("easygo_business_object_prop")
@KeySequence("easygo_business_object_prop_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessObjectPropDO extends BaseDO {

    /**
     * 业务对象ID
     */
    @TableId
    private Long id;
    /**
     * 业务属性名称
     */
    private String name;
    /**
     * 业务属性前端展示名称
     */
    private String showName;
    /**
     * 业务对象ID
     */
    private Long boId;
    /**
     * 业务属性对应字段名
     */
    private String columnName;
    /**
     * 搜索帮助ID
     */
    private String searchHelpId;
    /**
     * 字典ID
     */
    private String dictId;
    /**
     * 组件类型
     */
    private String uiType;
    /**
     * 是否作为查询条件
     */
    private Boolean isCond;
    /**
     * 是否在表单中展示
     */
    private Boolean formShow;
    /**
     * 是否在列表中展示
     */
    private Boolean gridShow;
    /**
     * 表单行位置
     */
    private Integer formRow;
    /**
     * 表单列位置
     */
    private Integer formCol;
    /**
     * 列表中位置
     */
    private Integer gridCol;
    /**
     * 属性类型：10：数据库字段；20：子表
     */
    private String type;
    /**
     * 子业务对象ID
     */
    private Long subBoId;
    /**
     * 显示顺序
     */
    private Integer sort;

}
