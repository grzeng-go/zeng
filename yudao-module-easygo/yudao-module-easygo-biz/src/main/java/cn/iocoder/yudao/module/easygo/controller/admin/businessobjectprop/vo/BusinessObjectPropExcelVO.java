package cn.iocoder.yudao.module.easygo.controller.admin.businessobjectprop.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 业务对象属性 Excel VO
 *
 * @author 芋道源码
 */
@Data
public class BusinessObjectPropExcelVO {

    @ExcelProperty("业务对象ID")
    private Long id;

    @ExcelProperty("业务属性名称")
    private String name;

    @ExcelProperty("业务属性前端展示名称")
    private String showName;

    @ExcelProperty("业务对象ID")
    private Long boId;

    @ExcelProperty("业务属性对应字段名")
    private String columnName;

    @ExcelProperty("搜索帮助ID")
    private String searchHelpId;

    @ExcelProperty("字典ID")
    private String dictId;

    @ExcelProperty("组件类型")
    private String uiType;

    @ExcelProperty("是否作为查询条件")
    private Boolean isCond;

    @ExcelProperty("是否在表单中展示")
    private Boolean formShow;

    @ExcelProperty("是否在列表中展示")
    private Boolean gridShow;

    @ExcelProperty("表单行位置")
    private Integer formRow;

    @ExcelProperty("表单列位置")
    private Integer formCol;

    @ExcelProperty("列表中位置")
    private Integer gridCol;

    @ExcelProperty("属性类型：10：数据库字段；20：子表")
    private String type;

    @ExcelProperty("子业务对象ID")
    private Long subBoId;

    @ExcelProperty("显示顺序")
    private Integer sort;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
