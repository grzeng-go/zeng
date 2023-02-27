package cn.iocoder.yudao.module.easygo.service.businessobject;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.easygo.controller.admin.businessobject.vo.*;
import cn.iocoder.yudao.module.easygo.dal.dataobject.businessobject.BusinessObjectDO;
import cn.iocoder.yudao.module.easygo.dal.mysql.businessobject.BusinessObjectMapper;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import javax.annotation.Resource;
import org.springframework.context.annotation.Import;
import java.util.*;
import java.time.LocalDateTime;

import static cn.hutool.core.util.RandomUtil.*;
import static cn.iocoder.yudao.framework.common.util.date.LocalDateTimeUtils.buildBetweenTime;
import static cn.iocoder.yudao.module.easygo.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.*;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.*;
import static cn.iocoder.yudao.framework.common.util.object.ObjectUtils.*;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
* {@link BusinessObjectServiceImpl} 的单元测试类
*
* @author 芋道源码
*/
@Import(BusinessObjectServiceImpl.class)
public class BusinessObjectServiceImplTest extends BaseDbUnitTest {

    @Resource
    private BusinessObjectServiceImpl businessObjectService;

    @Resource
    private BusinessObjectMapper businessObjectMapper;

    @Test
    public void testCreateBusinessObject_success() {
        // 准备参数
        BusinessObjectCreateReqVO reqVO = randomPojo(BusinessObjectCreateReqVO.class);

        // 调用
        Long businessObjectId = businessObjectService.createBusinessObject(reqVO);
        // 断言
        assertNotNull(businessObjectId);
        // 校验记录的属性是否正确
        BusinessObjectDO businessObject = businessObjectMapper.selectById(businessObjectId);
        assertPojoEquals(reqVO, businessObject);
    }

    @Test
    public void testUpdateBusinessObject_success() {
        // mock 数据
        BusinessObjectDO dbBusinessObject = randomPojo(BusinessObjectDO.class);
        businessObjectMapper.insert(dbBusinessObject);// @Sql: 先插入出一条存在的数据
        // 准备参数
        BusinessObjectUpdateReqVO reqVO = randomPojo(BusinessObjectUpdateReqVO.class, o -> {
            o.setId(dbBusinessObject.getId()); // 设置更新的 ID
        });

        // 调用
        businessObjectService.updateBusinessObject(reqVO);
        // 校验是否更新正确
        BusinessObjectDO businessObject = businessObjectMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, businessObject);
    }

    @Test
    public void testUpdateBusinessObject_notExists() {
        // 准备参数
        BusinessObjectUpdateReqVO reqVO = randomPojo(BusinessObjectUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> businessObjectService.updateBusinessObject(reqVO), BUSINESS_OBJECT_NOT_EXISTS);
    }

    @Test
    public void testDeleteBusinessObject_success() {
        // mock 数据
        BusinessObjectDO dbBusinessObject = randomPojo(BusinessObjectDO.class);
        businessObjectMapper.insert(dbBusinessObject);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbBusinessObject.getId();

        // 调用
        businessObjectService.deleteBusinessObject(id);
       // 校验数据不存在了
       assertNull(businessObjectMapper.selectById(id));
    }

    @Test
    public void testDeleteBusinessObject_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> businessObjectService.deleteBusinessObject(id), BUSINESS_OBJECT_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetBusinessObjectPage() {
       // mock 数据
       BusinessObjectDO dbBusinessObject = randomPojo(BusinessObjectDO.class, o -> { // 等会查询到
           o.setName(null);
           o.setBoName(null);
           o.setTableName(null);
           o.setDatasource(null);
           o.setSort(null);
           o.setJavaPath(null);
           o.setVuePath(null);
           o.setProcessId(null);
           o.setCreateTime(null);
       });
       businessObjectMapper.insert(dbBusinessObject);
       // 测试 name 不匹配
       businessObjectMapper.insert(cloneIgnoreId(dbBusinessObject, o -> o.setName(null)));
       // 测试 boName 不匹配
       businessObjectMapper.insert(cloneIgnoreId(dbBusinessObject, o -> o.setBoName(null)));
       // 测试 tableName 不匹配
       businessObjectMapper.insert(cloneIgnoreId(dbBusinessObject, o -> o.setTableName(null)));
       // 测试 datasource 不匹配
       businessObjectMapper.insert(cloneIgnoreId(dbBusinessObject, o -> o.setDatasource(null)));
       // 测试 sort 不匹配
       businessObjectMapper.insert(cloneIgnoreId(dbBusinessObject, o -> o.setSort(null)));
       // 测试 javaPath 不匹配
       businessObjectMapper.insert(cloneIgnoreId(dbBusinessObject, o -> o.setJavaPath(null)));
       // 测试 vuePath 不匹配
       businessObjectMapper.insert(cloneIgnoreId(dbBusinessObject, o -> o.setVuePath(null)));
       // 测试 processId 不匹配
       businessObjectMapper.insert(cloneIgnoreId(dbBusinessObject, o -> o.setProcessId(null)));
       // 测试 createTime 不匹配
       businessObjectMapper.insert(cloneIgnoreId(dbBusinessObject, o -> o.setCreateTime(null)));
       // 准备参数
       BusinessObjectPageReqVO reqVO = new BusinessObjectPageReqVO();
       reqVO.setName(null);
       reqVO.setBoName(null);
       reqVO.setTableName(null);
       reqVO.setDatasource(null);
       reqVO.setSort(null);
       reqVO.setJavaPath(null);
       reqVO.setVuePath(null);
       reqVO.setProcessId(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<BusinessObjectDO> pageResult = businessObjectService.getBusinessObjectPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbBusinessObject, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetBusinessObjectList() {
       // mock 数据
       BusinessObjectDO dbBusinessObject = randomPojo(BusinessObjectDO.class, o -> { // 等会查询到
           o.setName(null);
           o.setBoName(null);
           o.setTableName(null);
           o.setDatasource(null);
           o.setSort(null);
           o.setJavaPath(null);
           o.setVuePath(null);
           o.setProcessId(null);
           o.setCreateTime(null);
       });
       businessObjectMapper.insert(dbBusinessObject);
       // 测试 name 不匹配
       businessObjectMapper.insert(cloneIgnoreId(dbBusinessObject, o -> o.setName(null)));
       // 测试 boName 不匹配
       businessObjectMapper.insert(cloneIgnoreId(dbBusinessObject, o -> o.setBoName(null)));
       // 测试 tableName 不匹配
       businessObjectMapper.insert(cloneIgnoreId(dbBusinessObject, o -> o.setTableName(null)));
       // 测试 datasource 不匹配
       businessObjectMapper.insert(cloneIgnoreId(dbBusinessObject, o -> o.setDatasource(null)));
       // 测试 sort 不匹配
       businessObjectMapper.insert(cloneIgnoreId(dbBusinessObject, o -> o.setSort(null)));
       // 测试 javaPath 不匹配
       businessObjectMapper.insert(cloneIgnoreId(dbBusinessObject, o -> o.setJavaPath(null)));
       // 测试 vuePath 不匹配
       businessObjectMapper.insert(cloneIgnoreId(dbBusinessObject, o -> o.setVuePath(null)));
       // 测试 processId 不匹配
       businessObjectMapper.insert(cloneIgnoreId(dbBusinessObject, o -> o.setProcessId(null)));
       // 测试 createTime 不匹配
       businessObjectMapper.insert(cloneIgnoreId(dbBusinessObject, o -> o.setCreateTime(null)));
       // 准备参数
       BusinessObjectExportReqVO reqVO = new BusinessObjectExportReqVO();
       reqVO.setName(null);
       reqVO.setBoName(null);
       reqVO.setTableName(null);
       reqVO.setDatasource(null);
       reqVO.setSort(null);
       reqVO.setJavaPath(null);
       reqVO.setVuePath(null);
       reqVO.setProcessId(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       List<BusinessObjectDO> list = businessObjectService.getBusinessObjectList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbBusinessObject, list.get(0));
    }

}
