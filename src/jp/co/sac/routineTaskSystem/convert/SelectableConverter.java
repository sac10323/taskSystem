package jp.co.sac.routineTaskSystem.convert;

import jp.co.sac.routineTaskSystem.entity.csv.CSVEntity;
import jp.co.sac.routineTaskSystem.entity.document.Document;

/**
 *
 * @author shogo_saito
 */
public interface SelectableConverter<D extends Document, C extends CSVEntity> {
    boolean isTargetOf(D doc);
    boolean isTargetOf(C csv);
}
