package cn.iocoder.yudao.module.easygo.controller.admin.businessobject;

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

import cn.iocoder.yudao.module.easygo.controller.admin.businessobject.vo.*;
import cn.iocoder.yudao.module.easygo.dal.dataobject.businessobject.BusinessObjectDO;
import cn.iocoder.yudao.module.easygo.convert.businessobject.BusinessObjectConvert;
import cn.iocoder.yudao.module.easygo.service.businessobject.BusinessObjectService;

@Tag(name = "管理后台 - 业务对象")
@RestController
@RequestMapping("/easygo/business-object")
@Validated
public class BusinessObjectController {

    @Resource
    private BusinessObjectService businessObjectService;

    @PostMapping("/create")
    @Operation(summary = "创建业务对象")
    @PreAuthorize("@ss.hasPermission('easygo:business-object:create')")
    public CommonResult<Long> createBusinessObject(@Valid @RequestBody BusinessObjectCreateReqVO createReqVO) {
        return success(businessObjectService.createBusinessObject(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新业务对象")
    @PreAuthorize("@ss.hasPermission('easygo:business-object:update')")
    public CommonResult<Boolean> updateBusinessObject(@Valid @RequestBody BusinessObjectUpdateReqVO updateReqVO) {
        businessObjectService.updateBusinessObject(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除业务对象")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('easygo:business-object:delete')")
    public CommonResult<Boolean> deleteBusinessObject(@RequestParam("id") Long id) {
        businessObjectService.deleteBusinessObject(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得业务对象")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('easygo:business-object:query')")
    public CommonResult<BusinessObjectRespVO> getBusinessObject(@RequestParam("id") Long id) {
        BusinessObjectDO businessObject = businessObjectService.getBusinessObject(id);
        return success(BusinessObjectConvert.INSTANCE.convert(businessObject));
    }

    @GetMapping("/list")
    @Operation(summary = "获得业务对象列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('easygo:business-object:query')")
    public CommonResult<List<BusinessObjectRespVO>> getBusinessObjectList(@RequestParam("ids") Collection<Long> ids) {
        List<BusinessObjectDO> list = businessObjectService.getBusinessObjectList(ids);
        return success(BusinessObjectConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得业务对象分页")
    @PreAuthorize("@ss.hasPermission('easygo:business-object:query')")
    public CommonResult<PageResult<BusinessObjectRespVO>> getBusinessObjectPage(@Valid BusinessObjectPageReqVO pageVO) {
        PageResult<BusinessObjectDO> pageResult = businessObjectService.getBusinessObjectPage(pageVO);
        return success(BusinessObjectConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出业务对象 Excel")
    @PreAuthorize("@ss.hasPermission('easygo:business-object:export')")
    @OperateLog(type = EXPORT)
    public void exportBusinessObjectExcel(@Valid BusinessObjectExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<BusinessObjectDO> list = businessObjectService.getBusinessObjectList(exportReqVO);
        // 导出 Excel
        List<BusinessObjectExcelVO> datas = BusinessObjectConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "业务对象.xls", "数据", BusinessObjectExcelVO.class, datas);
    }

}
