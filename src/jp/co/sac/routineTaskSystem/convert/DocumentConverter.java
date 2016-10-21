package jp.co.sac.routineTaskSystem.convert;

import jp.co.sac.routineTaskSystem.entity.csv.CSVEntity;
import jp.co.sac.routineTaskSystem.entity.document.Document;

/**
 *
 * @author shogo_saito
 */
public abstract class DocumentConverter<D extends Document, C extends CSVEntity> implements SelectableConverter<Document, CSVEntity> {
    public abstract D toDocumentEntity(C csv);
    public abstract C toCSVEntity(D doc);
}
