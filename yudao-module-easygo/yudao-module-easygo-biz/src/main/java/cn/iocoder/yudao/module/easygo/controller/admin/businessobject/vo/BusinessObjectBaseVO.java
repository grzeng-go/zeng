package cn.iocoder.yudao.module.easygo.controller.admin.businessobject.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
* 业务对象 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class BusinessObjectBaseVO {

    @Schema(description = "业务对象名称", required = true, example = "张三")
    @NotNull(message = "业务对象名称不能为空")
    private String name;

    @Schema(description = "业务对象实体名称", required = true, example = "李四")
    @NotNull(message = "业务对象实体名称不能为空")
    private String boName;

    @Schema(description = "业务对象对应表名", required = true, example = "芋艿")
    @NotNull(message = "业务对象对应表名不能为空")
    private String tableName;

    @Schema(description = "数据来源", required = true)
    @NotNull(message = "数据来源不能为空")
    private String datasource;

    @Schema(description = "显示顺序", required = true)
    @NotNull(message = "显示顺序不能为空")
    private Integer sort;

    @Schema(description = "后端代码路径", required = true)
    @NotNull(message = "后端代码路径不能为空")
    private Long javaPath;

    @Schema(description = "前端代码路径")
    private String vuePath;

    @Schema(description = "关联流程定义", example = "3464")
    private String processId;

}
