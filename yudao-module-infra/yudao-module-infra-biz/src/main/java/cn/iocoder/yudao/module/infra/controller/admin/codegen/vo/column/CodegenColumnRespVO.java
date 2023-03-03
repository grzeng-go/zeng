package cn.iocoder.yudao.module.infra.controller.admin.codegen.vo.column;

import cn.iocoder.yudao.module.infra.enums.codegen.CodegenSceneEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 代码生成字段定义 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CodegenColumnRespVO extends CodegenColumnBaseVO {

    @Schema(description = "编号", required = true, example = "1")
    private Long id;

    @Schema(description = "创建时间", required = true)
    private LocalDateTime createTime;

    @Schema(description = "子业务对象要展示的字段", required = true)
    private List<CodegenColumnRespVO> subColumns;

    /**
     * 关联外键 首字母大写驼峰
     */
    private String upParentId;

    /**
     * java属性名 首字母大写驼峰
     */
    private String upJavaField;

    /**
     * 子业务对象类名 首字母小写驼峰
     */
    private String lowClassName;

    /**
     * 子业务对象类名 首字母大写驼峰
     */
    private String className;

    /**
     * 子业务对象主键JavaType
     */
    private CodegenColumnRespVO subPrimaryKeyColumn;

    // ${basePackage}.module.${table.moduleName}.controller.${sceneEnum.basePackage}.${table.businessName}

    private String subModuleName;

    private CodegenSceneEnum subSceneEnum;

    private String subBusinessName;

}
