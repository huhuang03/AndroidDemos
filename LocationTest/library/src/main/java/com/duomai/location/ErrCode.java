package com.duomai.location;

/**
 * @author york
 * @date 8/27/15
 * @since 1.0.0
 */
public enum ErrCode {
    OK, ERR_TIME_OUT,

    /**
     * 其他错误
     */
    ERR_OTHER,

    /**
     * 谷歌service框架需要更新
     */
    SERVICE_VERSION_UPDATE_REQUIRED
}
