package jp.co.sac.routineTaskSystem.validator;

import jp.co.sac.routineTaskSystem.entity.document.Document;

/**
 *
 * @author shogo_saito
 */
public interface SelectableValidator<T extends Document> {
    boolean isTargetOf(T doc);
}
