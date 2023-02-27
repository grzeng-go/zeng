package cn.iocoder.yudao.module.easygo.controller.admin.businessobjectprop.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 业务对象属性 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BusinessObjectPropRespVO extends BusinessObjectPropBaseVO {

    @Schema(description = "业务对象ID", required = true, example = "18267")
    private Long id;

    @Schema(description = "创建时间", required = true)
    private LocalDateTime createTime;

}
