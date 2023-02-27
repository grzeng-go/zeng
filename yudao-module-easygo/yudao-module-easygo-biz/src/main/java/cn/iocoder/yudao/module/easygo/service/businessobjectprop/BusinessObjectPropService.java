package cn.iocoder.yudao.module.easygo.service.businessobjectprop;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.easygo.controller.admin.businessobjectprop.vo.*;
import cn.iocoder.yudao.module.easygo.dal.dataobject.businessobjectprop.BusinessObjectPropDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 业务对象属性 Service 接口
 *
 * @author 芋道源码
 */
public interface BusinessObjectPropService {

    /**
     * 创建业务对象属性
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createBusinessObjectProp(@Valid BusinessObjectPropCreateReqVO createReqVO);

    /**
     * 更新业务对象属性
     *
     * @param updateReqVO 更新信息
     */
    void updateBusinessObjectProp(@Valid BusinessObjectPropUpdateReqVO updateReqVO);

    /**
     * 删除业务对象属性
     *
     * @param id 编号
     */
    void deleteBusinessObjectProp(Long id);

    /**
     * 获得业务对象属性
     *
     * @param id 编号
     * @return 业务对象属性
     */
    BusinessObjectPropDO getBusinessObjectProp(Long id);

    /**
     * 获得业务对象属性列表
     *
     * @param ids 编号
     * @return 业务对象属性列表
     */
    List<BusinessObjectPropDO> getBusinessObjectPropList(Collection<Long> ids);

    /**
     * 获得业务对象属性分页
     *
     * @param pageReqVO 分页查询
     * @return 业务对象属性分页
     */
    PageResult<BusinessObjectPropDO> getBusinessObjectPropPage(BusinessObjectPropPageReqVO pageReqVO);

    /**
     * 获得业务对象属性列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 业务对象属性列表
     */
    List<BusinessObjectPropDO> getBusinessObjectPropList(BusinessObjectPropExportReqVO exportReqVO);

}
