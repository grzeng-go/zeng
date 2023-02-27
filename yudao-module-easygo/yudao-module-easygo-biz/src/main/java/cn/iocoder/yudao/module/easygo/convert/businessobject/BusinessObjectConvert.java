package cn.iocoder.yudao.module.easygo.convert.businessobject;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.easygo.controller.admin.businessobject.vo.*;
import cn.iocoder.yudao.module.easygo.dal.dataobject.businessobject.BusinessObjectDO;

/**
 * 业务对象 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface BusinessObjectConvert {

    BusinessObjectConvert INSTANCE = Mappers.getMapper(BusinessObjectConvert.class);

    BusinessObjectDO convert(BusinessObjectCreateReqVO bean);

    BusinessObjectDO convert(BusinessObjectUpdateReqVO bean);

    BusinessObjectRespVO convert(BusinessObjectDO bean);

    List<BusinessObjectRespVO> convertList(List<BusinessObjectDO> list);

    PageResult<BusinessObjectRespVO> convertPage(PageResult<BusinessObjectDO> page);

    List<BusinessObjectExcelVO> convertList02(List<BusinessObjectDO> list);

}
