package cn.iocoder.yudao.module.easygo.service.businessobject;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.easygo.controller.admin.businessobject.vo.*;
import cn.iocoder.yudao.module.easygo.dal.dataobject.businessobject.BusinessObjectDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.easygo.convert.businessobject.BusinessObjectConvert;
import cn.iocoder.yudao.module.easygo.dal.mysql.businessobject.BusinessObjectMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.easygo.enums.ErrorCodeConstants.*;

/**
 * 业务对象 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class BusinessObjectServiceImpl implements BusinessObjectService {

    @Resource
    private BusinessObjectMapper businessObjectMapper;

    @Override
    public Long createBusinessObject(BusinessObjectCreateReqVO createReqVO) {
        // 插入
        BusinessObjectDO businessObject = BusinessObjectConvert.INSTANCE.convert(createReqVO);
        businessObjectMapper.insert(businessObject);
        // 返回
        return businessObject.getId();
    }

    @Override
    public void updateBusinessObject(BusinessObjectUpdateReqVO updateReqVO) {
        // 校验存在
        validateBusinessObjectExists(updateReqVO.getId());
        // 更新
        BusinessObjectDO updateObj = BusinessObjectConvert.INSTANCE.convert(updateReqVO);
        businessObjectMapper.updateById(updateObj);
    }

    @Override
    public void deleteBusinessObject(Long id) {
        // 校验存在
        validateBusinessObjectExists(id);
        // 删除
        businessObjectMapper.deleteById(id);
    }

    private void validateBusinessObjectExists(Long id) {
        if (businessObjectMapper.selectById(id) == null) {
            throw exception(BUSINESS_OBJECT_NOT_EXISTS);
        }
    }

    @Override
    public BusinessObjectDO getBusinessObject(Long id) {
        return businessObjectMapper.selectById(id);
    }

    @Override
    public List<BusinessObjectDO> getBusinessObjectList(Collection<Long> ids) {
        return businessObjectMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<BusinessObjectDO> getBusinessObjectPage(BusinessObjectPageReqVO pageReqVO) {
        return businessObjectMapper.selectPage(pageReqVO);
    }

    @Override
    public List<BusinessObjectDO> getBusinessObjectList(BusinessObjectExportReqVO exportReqVO) {
        return businessObjectMapper.selectList(exportReqVO);
    }

}
