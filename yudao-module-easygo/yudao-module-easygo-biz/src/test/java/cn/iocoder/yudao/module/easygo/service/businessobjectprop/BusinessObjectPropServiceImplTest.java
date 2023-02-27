package cn.iocoder.yudao.module.easygo.service.businessobjectprop;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;
import cn.iocoder.yudao.module.easygo.controller.admin.businessobjectprop.vo.BusinessObjectPropCreateReqVO;
import cn.iocoder.yudao.module.easygo.controller.admin.businessobjectprop.vo.BusinessObjectPropExportReqVO;
import cn.iocoder.yudao.module.easygo.controller.admin.businessobjectprop.vo.BusinessObjectPropPageReqVO;
import cn.iocoder.yudao.module.easygo.controller.admin.businessobjectprop.vo.BusinessObjectPropUpdateReqVO;
import cn.iocoder.yudao.module.easygo.dal.dataobject.businessobjectprop.BusinessObjectPropDO;
import cn.iocoder.yudao.module.easygo.dal.mysql.businessobjectprop.BusinessObjectPropMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.util.date.LocalDateTimeUtils.buildBetweenTime;
import static cn.iocoder.yudao.framework.common.util.object.ObjectUtils.cloneIgnoreId;
import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.assertPojoEquals;
import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.assertServiceException;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.randomLongId;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.randomPojo;
import static cn.iocoder.yudao.module.easygo.enums.ErrorCodeConstants.BUSINESS_OBJECT_PROP_NOT_EXISTS;
import static org.junit.jupiter.api.Assertions.*;

/**
* {@link BusinessObjectPropServiceImpl} 的单元测试类
*
* @author 芋道源码
*/
@Import(BusinessObjectPropServiceImpl.class)
public class BusinessObjectPropServiceImplTest extends BaseDbUnitTest {

    @Resource
    private BusinessObjectPropServiceImpl businessObjectPropService;

    @Resource
    private BusinessObjectPropMapper businessObjectPropMapper;

    @Test
    public void testCreateBusinessObjectProp_success() {
        // 准备参数
        BusinessObjectPropCreateReqVO reqVO = randomPojo(BusinessObjectPropCreateReqVO.class);

        // 调用
        Long businessObjectPropId = businessObjectPropService.createBusinessObjectProp(reqVO);
        // 断言
        assertNotNull(businessObjectPropId);
        // 校验记录的属性是否正确
        BusinessObjectPropDO businessObjectProp = businessObjectPropMapper.selectById(businessObjectPropId);
        assertPojoEquals(reqVO, businessObjectProp);
    }

    @Test
    public void testUpdateBusinessObjectProp_success() {
        // mock 数据
        BusinessObjectPropDO dbBusinessObjectProp = randomPojo(BusinessObjectPropDO.class);
        businessObjectPropMapper.insert(dbBusinessObjectProp);// @Sql: 先插入出一条存在的数据
        // 准备参数
        BusinessObjectPropUpdateReqVO reqVO = randomPojo(BusinessObjectPropUpdateReqVO.class, o -> {
            o.setId(dbBusinessObjectProp.getId()); // 设置更新的 ID
        });

        // 调用
        businessObjectPropService.updateBusinessObjectProp(reqVO);
        // 校验是否更新正确
        BusinessObjectPropDO businessObjectProp = businessObjectPropMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, businessObjectProp);
    }

    @Test
    public void testUpdateBusinessObjectProp_notExists() {
        // 准备参数
        BusinessObjectPropUpdateReqVO reqVO = randomPojo(BusinessObjectPropUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> businessObjectPropService.updateBusinessObjectProp(reqVO), BUSINESS_OBJECT_PROP_NOT_EXISTS);
    }

    @Test
    public void testDeleteBusinessObjectProp_success() {
        // mock 数据
        BusinessObjectPropDO dbBusinessObjectProp = randomPojo(BusinessObjectPropDO.class);
        businessObjectPropMapper.insert(dbBusinessObjectProp);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbBusinessObjectProp.getId();

        // 调用
        businessObjectPropService.deleteBusinessObjectProp(id);
       // 校验数据不存在了
       assertNull(businessObjectPropMapper.selectById(id));
    }

    @Test
    public void testDeleteBusinessObjectProp_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> businessObjectPropService.deleteBusinessObjectProp(id), BUSINESS_OBJECT_PROP_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetBusinessObjectPropPage() {
       // mock 数据
       BusinessObjectPropDO dbBusinessObjectProp = randomPojo(BusinessObjectPropDO.class, o -> { // 等会查询到
           o.setName(null);
           o.setShowName(null);
           o.setBoId(null);
           o.setColumnName(null);
           o.setSearchHelpId(null);
           o.setDictId(null);
           o.setUiType(null);
           o.setIsCond(null);
           o.setFormShow(null);
           o.setGridShow(null);
           o.setFormRow(null);
           o.setFormCol(null);
           o.setGridCol(null);
           o.setType(null);
           o.setSubBoId(null);
           o.setSort(null);
           o.setCreateTime(null);
       });
       businessObjectPropMapper.insert(dbBusinessObjectProp);
       // 测试 name 不匹配
       businessObjectPropMapper.insert(cloneIgnoreId(dbBusinessObjectProp, o -> o.setName(null)));
       // 测试 showName 不匹配
       businessObjectPropMapper.insert(cloneIgnoreId(dbBusinessObjectProp, o -> o.setShowName(null)));
       // 测试 boId 不匹配
       businessObjectPropMapper.insert(cloneIgnoreId(dbBusinessObjectProp, o -> o.setBoId(null)));
       // 测试 columnName 不匹配
       businessObjectPropMapper.insert(cloneIgnoreId(dbBusinessObjectProp, o -> o.setColumnName(null)));
       // 测试 searchHelpId 不匹配
       businessObjectPropMapper.insert(cloneIgnoreId(dbBusinessObjectProp, o -> o.setSearchHelpId(null)));
       // 测试 dictId 不匹配
       businessObjectPropMapper.insert(cloneIgnoreId(dbBusinessObjectProp, o -> o.setDictId(null)));
       // 测试 uiType 不匹配
       businessObjectPropMapper.insert(cloneIgnoreId(dbBusinessObjectProp, o -> o.setUiType(null)));
       // 测试 isCond 不匹配
       businessObjectPropMapper.insert(cloneIgnoreId(dbBusinessObjectProp, o -> o.setIsCond(null)));
       // 测试 formShow 不匹配
       businessObjectPropMapper.insert(cloneIgnoreId(dbBusinessObjectProp, o -> o.setFormShow(null)));
       // 测试 gridShow 不匹配
       businessObjectPropMapper.insert(cloneIgnoreId(dbBusinessObjectProp, o -> o.setGridShow(null)));
       // 测试 formRow 不匹配
       businessObjectPropMapper.insert(cloneIgnoreId(dbBusinessObjectProp, o -> o.setFormRow(null)));
       // 测试 formCol 不匹配
       businessObjectPropMapper.insert(cloneIgnoreId(dbBusinessObjectProp, o -> o.setFormCol(null)));
       // 测试 gridCol 不匹配
       businessObjectPropMapper.insert(cloneIgnoreId(dbBusinessObjectProp, o -> o.setGridCol(null)));
       // 测试 type 不匹配
       businessObjectPropMapper.insert(cloneIgnoreId(dbBusinessObjectProp, o -> o.setType(null)));
       // 测试 subBoId 不匹配
       businessObjectPropMapper.insert(cloneIgnoreId(dbBusinessObjectProp, o -> o.setSubBoId(null)));
       // 测试 sort 不匹配
       businessObjectPropMapper.insert(cloneIgnoreId(dbBusinessObjectProp, o -> o.setSort(null)));
       // 测试 createTime 不匹配
       businessObjectPropMapper.insert(cloneIgnoreId(dbBusinessObjectProp, o -> o.setCreateTime(null)));
       // 准备参数
       BusinessObjectPropPageReqVO reqVO = new BusinessObjectPropPageReqVO();
       reqVO.setName(null);
       reqVO.setShowName(null);
       reqVO.setBoId(null);
       reqVO.setColumnName(null);
       reqVO.setSearchHelpId(null);
       reqVO.setDictId(null);
       reqVO.setUiType(null);
       reqVO.setIsCond(null);
       reqVO.setFormShow(null);
       reqVO.setGridShow(null);
       reqVO.setFormRow(null);
       reqVO.setFormCol(null);
       reqVO.setGridCol(null);
       reqVO.setType(null);
       reqVO.setSubBoId(null);
       reqVO.setSort(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<BusinessObjectPropDO> pageResult = businessObjectPropService.getBusinessObjectPropPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbBusinessObjectProp, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetBusinessObjectPropList() {
       // mock 数据
       BusinessObjectPropDO dbBusinessObjectProp = randomPojo(BusinessObjectPropDO.class, o -> { // 等会查询到
           o.setName(null);
           o.setShowName(null);
           o.setBoId(null);
           o.setColumnName(null);
           o.setSearchHelpId(null);
           o.setDictId(null);
           o.setUiType(null);
           o.setIsCond(null);
           o.setFormShow(null);
           o.setGridShow(null);
           o.setFormRow(null);
           o.setFormCol(null);
           o.setGridCol(null);
           o.setType(null);
           o.setSubBoId(null);
           o.setSort(null);
           o.setCreateTime(null);
       });
       businessObjectPropMapper.insert(dbBusinessObjectProp);
       // 测试 name 不匹配
       businessObjectPropMapper.insert(cloneIgnoreId(dbBusinessObjectProp, o -> o.setName(null)));
       // 测试 showName 不匹配
       businessObjectPropMapper.insert(cloneIgnoreId(dbBusinessObjectProp, o -> o.setShowName(null)));
       // 测试 boId 不匹配
       businessObjectPropMapper.insert(cloneIgnoreId(dbBusinessObjectProp, o -> o.setBoId(null)));
       // 测试 columnName 不匹配
       businessObjectPropMapper.insert(cloneIgnoreId(dbBusinessObjectProp, o -> o.setColumnName(null)));
       // 测试 searchHelpId 不匹配
       businessObjectPropMapper.insert(cloneIgnoreId(dbBusinessObjectProp, o -> o.setSearchHelpId(null)));
       // 测试 dictId 不匹配
       businessObjectPropMapper.insert(cloneIgnoreId(dbBusinessObjectProp, o -> o.setDictId(null)));
       // 测试 uiType 不匹配
       businessObjectPropMapper.insert(cloneIgnoreId(dbBusinessObjectProp, o -> o.setUiType(null)));
       // 测试 isCond 不匹配
       businessObjectPropMapper.insert(cloneIgnoreId(dbBusinessObjectProp, o -> o.setIsCond(null)));
       // 测试 formShow 不匹配
       businessObjectPropMapper.insert(cloneIgnoreId(dbBusinessObjectProp, o -> o.setFormShow(null)));
       // 测试 gridShow 不匹配
       businessObjectPropMapper.insert(cloneIgnoreId(dbBusinessObjectProp, o -> o.setGridShow(null)));
       // 测试 formRow 不匹配
       businessObjectPropMapper.insert(cloneIgnoreId(dbBusinessObjectProp, o -> o.setFormRow(null)));
       // 测试 formCol 不匹配
       businessObjectPropMapper.insert(cloneIgnoreId(dbBusinessObjectProp, o -> o.setFormCol(null)));
       // 测试 gridCol 不匹配
       businessObjectPropMapper.insert(cloneIgnoreId(dbBusinessObjectProp, o -> o.setGridCol(null)));
       // 测试 type 不匹配
       businessObjectPropMapper.insert(cloneIgnoreId(dbBusinessObjectProp, o -> o.setType(null)));
       // 测试 subBoId 不匹配
       businessObjectPropMapper.insert(cloneIgnoreId(dbBusinessObjectProp, o -> o.setSubBoId(null)));
       // 测试 sort 不匹配
       businessObjectPropMapper.insert(cloneIgnoreId(dbBusinessObjectProp, o -> o.setSort(null)));
       // 测试 createTime 不匹配
       businessObjectPropMapper.insert(cloneIgnoreId(dbBusinessObjectProp, o -> o.setCreateTime(null)));
       // 准备参数
       BusinessObjectPropExportReqVO reqVO = new BusinessObjectPropExportReqVO();
       reqVO.setName(null);
       reqVO.setShowName(null);
       reqVO.setBoId(null);
       reqVO.setColumnName(null);
       reqVO.setSearchHelpId(null);
       reqVO.setDictId(null);
       reqVO.setUiType(null);
       reqVO.setIsCond(null);
       reqVO.setFormShow(null);
       reqVO.setGridShow(null);
       reqVO.setFormRow(null);
       reqVO.setFormCol(null);
       reqVO.setGridCol(null);
       reqVO.setType(null);
       reqVO.setSubBoId(null);
       reqVO.setSort(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       List<BusinessObjectPropDO> list = businessObjectPropService.getBusinessObjectPropList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbBusinessObjectProp, list.get(0));
    }

}
