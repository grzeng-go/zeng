package cn.iocoder.yudao.module.easygo.controller.admin.businessobjectprop.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
* 业务对象属性 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class BusinessObjectPropBaseVO {

    @Schema(description = "业务属性名称", required = true, example = "芋艿")
    @NotNull(message = "业务属性名称不能为空")
    private String name;

    @Schema(description = "业务属性前端展示名称", required = true, example = "张三")
    @NotNull(message = "业务属性前端展示名称不能为空")
    private String showName;

    @Schema(description = "业务对象ID", required = true, example = "19017")
    @NotNull(message = "业务对象ID不能为空")
    private Long boId;

    @Schema(description = "业务属性对应字段名", required = true, example = "赵六")
    @NotNull(message = "业务属性对应字段名不能为空")
    private String columnName;

    @Schema(description = "搜索帮助ID", required = true, example = "23858")
    @NotNull(message = "搜索帮助ID不能为空")
    private String searchHelpId;

    @Schema(description = "字典ID", required = true, example = "2347")
    @NotNull(message = "字典ID不能为空")
    private String dictId;

    @Schema(description = "组件类型", example = "1")
    private String uiType;

    @Schema(description = "是否作为查询条件")
    private Boolean isCond;

    @Schema(description = "是否在表单中展示", required = true)
    @NotNull(message = "是否在表单中展示不能为空")
    private Boolean formShow;

    @Schema(description = "是否在列表中展示", required = true)
    @NotNull(message = "是否在列表中展示不能为空")
    private Boolean gridShow;

    @Schema(description = "表单行位置")
    private Integer formRow;

    @Schema(description = "表单列位置")
    private Integer formCol;

    @Schema(description = "列表中位置")
    private Integer gridCol;

    @Schema(description = "属性类型：10：数据库字段；20：子表", required = true, example = "1")
    @NotNull(message = "属性类型：10：数据库字段；20：子表不能为空")
    private String type;

    @Schema(description = "子业务对象ID", required = true, example = "28631")
    @NotNull(message = "子业务对象ID不能为空")
    private Long subBoId;

    @Schema(description = "显示顺序", required = true)
    @NotNull(message = "显示顺序不能为空")
    private Integer sort;

}
