package cn.iocoder.yudao.module.easygo.service.businessobject;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.easygo.controller.admin.businessobject.vo.*;
import cn.iocoder.yudao.module.easygo.dal.dataobject.businessobject.BusinessObjectDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 业务对象 Service 接口
 *
 * @author 芋道源码
 */
public interface BusinessObjectService {

    /**
     * 创建业务对象
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createBusinessObject(@Valid BusinessObjectCreateReqVO createReqVO);

    /**
     * 更新业务对象
     *
     * @param updateReqVO 更新信息
     */
    void updateBusinessObject(@Valid BusinessObjectUpdateReqVO updateReqVO);

    /**
     * 删除业务对象
     *
     * @param id 编号
     */
    void deleteBusinessObject(Long id);

    /**
     * 获得业务对象
     *
     * @param id 编号
     * @return 业务对象
     */
    BusinessObjectDO getBusinessObject(Long id);

    /**
     * 获得业务对象列表
     *
     * @param ids 编号
     * @return 业务对象列表
     */
    List<BusinessObjectDO> getBusinessObjectList(Collection<Long> ids);

    /**
     * 获得业务对象分页
     *
     * @param pageReqVO 分页查询
     * @return 业务对象分页
     */
    PageResult<BusinessObjectDO> getBusinessObjectPage(BusinessObjectPageReqVO pageReqVO);

    /**
     * 获得业务对象列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 业务对象列表
     */
    List<BusinessObjectDO> getBusinessObjectList(BusinessObjectExportReqVO exportReqVO);

}
