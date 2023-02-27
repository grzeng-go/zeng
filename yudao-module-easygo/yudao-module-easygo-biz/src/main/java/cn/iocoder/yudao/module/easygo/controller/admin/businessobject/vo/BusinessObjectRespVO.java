package cn.iocoder.yudao.module.easygo.controller.admin.businessobject.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 业务对象 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BusinessObjectRespVO extends BusinessObjectBaseVO {

    @Schema(description = "业务对象ID", required = true, example = "24469")
    private Long id;

    @Schema(description = "创建时间", required = true)
    private LocalDateTime createTime;

}
