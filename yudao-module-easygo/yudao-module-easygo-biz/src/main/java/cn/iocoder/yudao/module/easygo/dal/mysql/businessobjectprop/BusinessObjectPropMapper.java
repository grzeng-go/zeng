package cn.iocoder.yudao.module.easygo.dal.mysql.businessobjectprop;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.easygo.dal.dataobject.businessobjectprop.BusinessObjectPropDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.easygo.controller.admin.businessobjectprop.vo.*;

/**
 * 业务对象属性 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface BusinessObjectPropMapper extends BaseMapperX<BusinessObjectPropDO> {

    default PageResult<BusinessObjectPropDO> selectPage(BusinessObjectPropPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<BusinessObjectPropDO>()
                .likeIfPresent(BusinessObjectPropDO::getName, reqVO.getName())
                .likeIfPresent(BusinessObjectPropDO::getShowName, reqVO.getShowName())
                .eqIfPresent(BusinessObjectPropDO::getBoId, reqVO.getBoId())
                .likeIfPresent(BusinessObjectPropDO::getColumnName, reqVO.getColumnName())
                .eqIfPresent(BusinessObjectPropDO::getSearchHelpId, reqVO.getSearchHelpId())
                .eqIfPresent(BusinessObjectPropDO::getDictId, reqVO.getDictId())
                .eqIfPresent(BusinessObjectPropDO::getUiType, reqVO.getUiType())
                .eqIfPresent(BusinessObjectPropDO::getIsCond, reqVO.getIsCond())
                .eqIfPresent(BusinessObjectPropDO::getFormShow, reqVO.getFormShow())
                .eqIfPresent(BusinessObjectPropDO::getGridShow, reqVO.getGridShow())
                .eqIfPresent(BusinessObjectPropDO::getFormRow, reqVO.getFormRow())
                .eqIfPresent(BusinessObjectPropDO::getFormCol, reqVO.getFormCol())
                .eqIfPresent(BusinessObjectPropDO::getGridCol, reqVO.getGridCol())
                .eqIfPresent(BusinessObjectPropDO::getType, reqVO.getType())
                .eqIfPresent(BusinessObjectPropDO::getSubBoId, reqVO.getSubBoId())
                .eqIfPresent(BusinessObjectPropDO::getSort, reqVO.getSort())
                .betweenIfPresent(BusinessObjectPropDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(BusinessObjectPropDO::getId));
    }

    default List<BusinessObjectPropDO> selectList(BusinessObjectPropExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<BusinessObjectPropDO>()
                .likeIfPresent(BusinessObjectPropDO::getName, reqVO.getName())
                .likeIfPresent(BusinessObjectPropDO::getShowName, reqVO.getShowName())
                .eqIfPresent(BusinessObjectPropDO::getBoId, reqVO.getBoId())
                .likeIfPresent(BusinessObjectPropDO::getColumnName, reqVO.getColumnName())
                .eqIfPresent(BusinessObjectPropDO::getSearchHelpId, reqVO.getSearchHelpId())
                .eqIfPresent(BusinessObjectPropDO::getDictId, reqVO.getDictId())
                .eqIfPresent(BusinessObjectPropDO::getUiType, reqVO.getUiType())
                .eqIfPresent(BusinessObjectPropDO::getIsCond, reqVO.getIsCond())
                .eqIfPresent(BusinessObjectPropDO::getFormShow, reqVO.getFormShow())
                .eqIfPresent(BusinessObjectPropDO::getGridShow, reqVO.getGridShow())
                .eqIfPresent(BusinessObjectPropDO::getFormRow, reqVO.getFormRow())
                .eqIfPresent(BusinessObjectPropDO::getFormCol, reqVO.getFormCol())
                .eqIfPresent(BusinessObjectPropDO::getGridCol, reqVO.getGridCol())
                .eqIfPresent(BusinessObjectPropDO::getType, reqVO.getType())
                .eqIfPresent(BusinessObjectPropDO::getSubBoId, reqVO.getSubBoId())
                .eqIfPresent(BusinessObjectPropDO::getSort, reqVO.getSort())
                .betweenIfPresent(BusinessObjectPropDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(BusinessObjectPropDO::getId));
    }

}
