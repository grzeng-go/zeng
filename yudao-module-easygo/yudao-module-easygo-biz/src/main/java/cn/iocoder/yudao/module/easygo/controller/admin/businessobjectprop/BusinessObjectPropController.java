package cn.iocoder.yudao.module.easygo.controller.admin.businessobjectprop;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.easygo.controller.admin.businessobjectprop.vo.*;
import cn.iocoder.yudao.module.easygo.dal.dataobject.businessobjectprop.BusinessObjectPropDO;
import cn.iocoder.yudao.module.easygo.convert.businessobjectprop.BusinessObjectPropConvert;
import cn.iocoder.yudao.module.easygo.service.businessobjectprop.BusinessObjectPropService;

@Tag(name = "管理后台 - 业务对象属性")
@RestController
@RequestMapping("/easygo/business-object-prop")
@Validated
public class BusinessObjectPropController {

    @Resource
    private BusinessObjectPropService businessObjectPropService;

    @PostMapping("/create")
    @Operation(summary = "创建业务对象属性")
    @PreAuthorize("@ss.hasPermission('easygo:business-object-prop:create')")
    public CommonResult<Long> createBusinessObjectProp(@Valid @RequestBody BusinessObjectPropCreateReqVO createReqVO) {
        return success(businessObjectPropService.createBusinessObjectProp(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新业务对象属性")
    @PreAuthorize("@ss.hasPermission('easygo:business-object-prop:update')")
    public CommonResult<Boolean> updateBusinessObjectProp(@Valid @RequestBody BusinessObjectPropUpdateReqVO updateReqVO) {
        businessObjectPropService.updateBusinessObjectProp(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除业务对象属性")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('easygo:business-object-prop:delete')")
    public CommonResult<Boolean> deleteBusinessObjectProp(@RequestParam("id") Long id) {
        businessObjectPropService.deleteBusinessObjectProp(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得业务对象属性")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('easygo:business-object-prop:query')")
    public CommonResult<BusinessObjectPropRespVO> getBusinessObjectProp(@RequestParam("id") Long id) {
        BusinessObjectPropDO businessObjectProp = businessObjectPropService.getBusinessObjectProp(id);
        return success(BusinessObjectPropConvert.INSTANCE.convert(businessObjectProp));
    }

    @GetMapping("/list")
    @Operation(summary = "获得业务对象属性列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('easygo:business-object-prop:query')")
    public CommonResult<List<BusinessObjectPropRespVO>> getBusinessObjectPropList(@RequestParam("ids") Collection<Long> ids) {
        List<BusinessObjectPropDO> list = businessObjectPropService.getBusinessObjectPropList(ids);
        return success(BusinessObjectPropConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得业务对象属性分页")
    @PreAuthorize("@ss.hasPermission('easygo:business-object-prop:query')")
    public CommonResult<PageResult<BusinessObjectPropRespVO>> getBusinessObjectPropPage(@Valid BusinessObjectPropPageReqVO pageVO) {
        PageResult<BusinessObjectPropDO> pageResult = businessObjectPropService.getBusinessObjectPropPage(pageVO);
        return success(BusinessObjectPropConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出业务对象属性 Excel")
    @PreAuthorize("@ss.hasPermission('easygo:business-object-prop:export')")
    @OperateLog(type = EXPORT)
    public void exportBusinessObjectPropExcel(@Valid BusinessObjectPropExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<BusinessObjectPropDO> list = businessObjectPropService.getBusinessObjectPropList(exportReqVO);
        // 导出 Excel
        List<BusinessObjectPropExcelVO> datas = BusinessObjectPropConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "业务对象属性.xls", "数据", BusinessObjectPropExcelVO.class, datas);
    }

}
