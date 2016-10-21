package jp.co.sac.routineTaskSystem.validator;

import jp.co.sac.routineTaskSystem.entity.document.Document;

/**
 *
 * @author shogo_saito
 */
public abstract class DocumentValidator<T extends Document> implements DocumentValidatorIF<T>, SelectableValidator<Document> {
    
}
