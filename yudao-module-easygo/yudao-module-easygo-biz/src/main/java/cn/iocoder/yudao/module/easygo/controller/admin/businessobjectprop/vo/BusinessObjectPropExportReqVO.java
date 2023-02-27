package cn.iocoder.yudao.module.easygo.controller.admin.businessobjectprop.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 业务对象属性 Excel 导出 Request VO，参数和 BusinessObjectPropPageReqVO 是一致的")
@Data
public class BusinessObjectPropExportReqVO {

    @Schema(description = "业务属性名称", example = "芋艿")
    private String name;

    @Schema(description = "业务属性前端展示名称", example = "张三")
    private String showName;

    @Schema(description = "业务对象ID", example = "19017")
    private Long boId;

    @Schema(description = "业务属性对应字段名", example = "赵六")
    private String columnName;

    @Schema(description = "搜索帮助ID", example = "23858")
    private String searchHelpId;

    @Schema(description = "字典ID", example = "2347")
    private String dictId;

    @Schema(description = "组件类型", example = "1")
    private String uiType;

    @Schema(description = "是否作为查询条件")
    private Boolean isCond;

    @Schema(description = "是否在表单中展示")
    private Boolean formShow;

    @Schema(description = "是否在列表中展示")
    private Boolean gridShow;

    @Schema(description = "表单行位置")
    private Integer formRow;

    @Schema(description = "表单列位置")
    private Integer formCol;

    @Schema(description = "列表中位置")
    private Integer gridCol;

    @Schema(description = "属性类型：10：数据库字段；20：子表", example = "1")
    private String type;

    @Schema(description = "子业务对象ID", example = "28631")
    private Long subBoId;

    @Schema(description = "显示顺序")
    private Integer sort;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
