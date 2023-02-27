package cn.iocoder.yudao.module.easygo.controller.admin.businessobjectprop.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 业务对象属性更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BusinessObjectPropUpdateReqVO extends BusinessObjectPropBaseVO {

    @Schema(description = "业务对象ID", required = true, example = "18267")
    @NotNull(message = "业务对象ID不能为空")
    private Long id;

}
