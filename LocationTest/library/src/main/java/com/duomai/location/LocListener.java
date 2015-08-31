package com.duomai.location;

import java.util.List;

/**
 * 定位回调
 * @author york
 * @date 8/27/15
 * @since 1.0.0
 */
public interface LocListener {

    /**
     * 定位回调，保证result始终第一个为首选结果
     * @param errCode
     * @param results
     */
    void onLocResult(ErrCode errCode, List<LocResult> results);
}
