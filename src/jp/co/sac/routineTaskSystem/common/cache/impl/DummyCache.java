package jp.co.sac.routineTaskSystem.common.cache.impl;

import java.util.Date;

/**
 * キャッシュ試作
 *
 * @author shogo_saito
 */
public enum DummyCache {
    cache;
    private Date updateDate;

    private DummyCache() {
        updateDate = new Date(System.currentTimeMillis());
    }

    public Date getUpdateDate() {
        return updateDate;
    }
}
