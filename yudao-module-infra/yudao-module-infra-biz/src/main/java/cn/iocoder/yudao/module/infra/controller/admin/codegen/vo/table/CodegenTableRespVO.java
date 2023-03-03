package cn.iocoder.yudao.module.infra.controller.admin.codegen.vo.table;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 代码生成表定义 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CodegenTableRespVO extends CodegenTableBaseVO {

    @Schema(description = "编号", required = true, example = "1")
    private Long id;

    @Schema(description = "主键编号", required = true, example = "1024")
    private Integer dataSourceConfigId;

    @Schema(description = "创建时间", required = true)
    private LocalDateTime createTime;

    @Schema(description = "更新时间", required = true)
    private LocalDateTime updateTime;

    /**
     * 子业务对象管理主表的字段名 首字母大写驼峰
     */
    private String upParentId;

    /**
     * 子业务对象管理主表的字段名
     */
    private String parentId;

    /**
     * 主表主键java类型
     */
    private String parentJavaType;

    /**
     * 主业务对象类名
     */
    private String parentClassName;

    /**
     * 主业务对象类名 首字母小写驼峰
     */
    private String lowParentClassName;

    /**
     * 是否子业务对象
     */
    private boolean subTable;

}
