package cn.iocoder.yudao.module.easygo.service.businessobjectprop;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.easygo.controller.admin.businessobjectprop.vo.*;
import cn.iocoder.yudao.module.easygo.dal.dataobject.businessobjectprop.BusinessObjectPropDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.easygo.convert.businessobjectprop.BusinessObjectPropConvert;
import cn.iocoder.yudao.module.easygo.dal.mysql.businessobjectprop.BusinessObjectPropMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.easygo.enums.ErrorCodeConstants.*;

/**
 * 业务对象属性 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class BusinessObjectPropServiceImpl implements BusinessObjectPropService {

    @Resource
    private BusinessObjectPropMapper businessObjectPropMapper;

    @Override
    public Long createBusinessObjectProp(BusinessObjectPropCreateReqVO createReqVO) {
        // 插入
        BusinessObjectPropDO businessObjectProp = BusinessObjectPropConvert.INSTANCE.convert(createReqVO);
        businessObjectPropMapper.insert(businessObjectProp);
        // 返回
        return businessObjectProp.getId();
    }

    @Override
    public void updateBusinessObjectProp(BusinessObjectPropUpdateReqVO updateReqVO) {
        // 校验存在
        validateBusinessObjectPropExists(updateReqVO.getId());
        // 更新
        BusinessObjectPropDO updateObj = BusinessObjectPropConvert.INSTANCE.convert(updateReqVO);
        businessObjectPropMapper.updateById(updateObj);
    }

    @Override
    public void deleteBusinessObjectProp(Long id) {
        // 校验存在
        validateBusinessObjectPropExists(id);
        // 删除
        businessObjectPropMapper.deleteById(id);
    }

    private void validateBusinessObjectPropExists(Long id) {
        if (businessObjectPropMapper.selectById(id) == null) {
            throw exception(BUSINESS_OBJECT_PROP_NOT_EXISTS);
        }
    }

    @Override
    public BusinessObjectPropDO getBusinessObjectProp(Long id) {
        return businessObjectPropMapper.selectById(id);
    }

    @Override
    public List<BusinessObjectPropDO> getBusinessObjectPropList(Collection<Long> ids) {
        return businessObjectPropMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<BusinessObjectPropDO> getBusinessObjectPropPage(BusinessObjectPropPageReqVO pageReqVO) {
        return businessObjectPropMapper.selectPage(pageReqVO);
    }

    @Override
    public List<BusinessObjectPropDO> getBusinessObjectPropList(BusinessObjectPropExportReqVO exportReqVO) {
        return businessObjectPropMapper.selectList(exportReqVO);
    }

}
