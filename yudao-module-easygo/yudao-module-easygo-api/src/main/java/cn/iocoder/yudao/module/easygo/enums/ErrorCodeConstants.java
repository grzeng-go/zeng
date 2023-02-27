package cn.iocoder.yudao.module.easygo.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

public interface ErrorCodeConstants {
    ErrorCode BUSINESS_OBJECT_NOT_EXISTS = new ErrorCode(111, "业务对象不存在");

    ErrorCode BUSINESS_OBJECT_PROP_NOT_EXISTS = new ErrorCode(222, "业务对象属性不存在");
}
