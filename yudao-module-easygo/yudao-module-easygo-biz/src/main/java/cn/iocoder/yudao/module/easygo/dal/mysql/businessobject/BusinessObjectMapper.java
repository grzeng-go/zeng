package cn.iocoder.yudao.module.easygo.dal.mysql.businessobject;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.easygo.dal.dataobject.businessobject.BusinessObjectDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.easygo.controller.admin.businessobject.vo.*;

/**
 * 业务对象 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface BusinessObjectMapper extends BaseMapperX<BusinessObjectDO> {

    default PageResult<BusinessObjectDO> selectPage(BusinessObjectPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<BusinessObjectDO>()
                .likeIfPresent(BusinessObjectDO::getName, reqVO.getName())
                .likeIfPresent(BusinessObjectDO::getBoName, reqVO.getBoName())
                .likeIfPresent(BusinessObjectDO::getTableName, reqVO.getTableName())
                .eqIfPresent(BusinessObjectDO::getDatasource, reqVO.getDatasource())
                .eqIfPresent(BusinessObjectDO::getSort, reqVO.getSort())
                .eqIfPresent(BusinessObjectDO::getJavaPath, reqVO.getJavaPath())
                .eqIfPresent(BusinessObjectDO::getVuePath, reqVO.getVuePath())
                .eqIfPresent(BusinessObjectDO::getProcessId, reqVO.getProcessId())
                .betweenIfPresent(BusinessObjectDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(BusinessObjectDO::getId));
    }

    default List<BusinessObjectDO> selectList(BusinessObjectExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<BusinessObjectDO>()
                .likeIfPresent(BusinessObjectDO::getName, reqVO.getName())
                .likeIfPresent(BusinessObjectDO::getBoName, reqVO.getBoName())
                .likeIfPresent(BusinessObjectDO::getTableName, reqVO.getTableName())
                .eqIfPresent(BusinessObjectDO::getDatasource, reqVO.getDatasource())
                .eqIfPresent(BusinessObjectDO::getSort, reqVO.getSort())
                .eqIfPresent(BusinessObjectDO::getJavaPath, reqVO.getJavaPath())
                .eqIfPresent(BusinessObjectDO::getVuePath, reqVO.getVuePath())
                .eqIfPresent(BusinessObjectDO::getProcessId, reqVO.getProcessId())
                .betweenIfPresent(BusinessObjectDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(BusinessObjectDO::getId));
    }

}
