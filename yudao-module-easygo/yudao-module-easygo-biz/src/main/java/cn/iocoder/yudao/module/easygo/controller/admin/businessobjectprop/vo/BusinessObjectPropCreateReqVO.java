package cn.iocoder.yudao.module.easygo.controller.admin.businessobjectprop.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 业务对象属性创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BusinessObjectPropCreateReqVO extends BusinessObjectPropBaseVO {

}
