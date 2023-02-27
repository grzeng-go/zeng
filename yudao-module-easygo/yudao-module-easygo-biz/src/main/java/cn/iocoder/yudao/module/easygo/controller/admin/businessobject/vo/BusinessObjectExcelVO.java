package cn.iocoder.yudao.module.easygo.controller.admin.businessobject.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 业务对象 Excel VO
 *
 * @author 芋道源码
 */
@Data
public class BusinessObjectExcelVO {

    @ExcelProperty("业务对象ID")
    private Long id;

    @ExcelProperty("业务对象名称")
    private String name;

    @ExcelProperty("业务对象实体名称")
    private String boName;

    @ExcelProperty("业务对象对应表名")
    private String tableName;

    @ExcelProperty("数据来源")
    private String datasource;

    @ExcelProperty("显示顺序")
    private Integer sort;

    @ExcelProperty("后端代码路径")
    private Long javaPath;

    @ExcelProperty("前端代码路径")
    private String vuePath;

    @ExcelProperty("关联流程定义")
    private String processId;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
