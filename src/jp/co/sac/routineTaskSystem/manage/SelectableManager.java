package jp.co.sac.routineTaskSystem.manage;

import jp.co.sac.routineTaskSystem.entity.document.Document;

/**
 *
 * @author shogo_saito
 */
public interface SelectableManager<T extends Document> {
    boolean isTargetOf(String title);
    boolean isTargetOf(T doc);
}
