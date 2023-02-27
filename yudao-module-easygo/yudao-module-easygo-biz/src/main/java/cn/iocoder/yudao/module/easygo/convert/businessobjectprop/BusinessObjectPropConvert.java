package cn.iocoder.yudao.module.easygo.convert.businessobjectprop;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.easygo.controller.admin.businessobjectprop.vo.*;
import cn.iocoder.yudao.module.easygo.dal.dataobject.businessobjectprop.BusinessObjectPropDO;

/**
 * 业务对象属性 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface BusinessObjectPropConvert {

    BusinessObjectPropConvert INSTANCE = Mappers.getMapper(BusinessObjectPropConvert.class);

    BusinessObjectPropDO convert(BusinessObjectPropCreateReqVO bean);

    BusinessObjectPropDO convert(BusinessObjectPropUpdateReqVO bean);

    BusinessObjectPropRespVO convert(BusinessObjectPropDO bean);

    List<BusinessObjectPropRespVO> convertList(List<BusinessObjectPropDO> list);

    PageResult<BusinessObjectPropRespVO> convertPage(PageResult<BusinessObjectPropDO> page);

    List<BusinessObjectPropExcelVO> convertList02(List<BusinessObjectPropDO> list);

}
