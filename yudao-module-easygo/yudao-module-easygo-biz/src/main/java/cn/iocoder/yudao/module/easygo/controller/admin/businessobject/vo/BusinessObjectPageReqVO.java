package cn.iocoder.yudao.module.easygo.controller.admin.businessobject.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 业务对象分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BusinessObjectPageReqVO extends PageParam {

    @Schema(description = "业务对象名称", example = "张三")
    private String name;

    @Schema(description = "业务对象实体名称", example = "李四")
    private String boName;

    @Schema(description = "业务对象对应表名", example = "芋艿")
    private String tableName;

    @Schema(description = "数据来源")
    private String datasource;

    @Schema(description = "显示顺序")
    private Integer sort;

    @Schema(description = "后端代码路径")
    private Long javaPath;

    @Schema(description = "前端代码路径")
    private String vuePath;

    @Schema(description = "关联流程定义", example = "3464")
    private String processId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
