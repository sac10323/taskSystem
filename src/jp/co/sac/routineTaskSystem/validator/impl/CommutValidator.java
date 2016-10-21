package jp.co.sac.routineTaskSystem.validator.impl;

import java.util.List;
import jp.co.sac.routineTaskSystem.constant.CommutConst;
import jp.co.sac.routineTaskSystem.entity.document.CommutDocument;
import jp.co.sac.routineTaskSystem.entity.document.Document;
import jp.co.sac.routineTaskSystem.entity.findings.Findings;
import jp.co.sac.routineTaskSystem.entity.staff.Staff;
import jp.co.sac.routineTaskSystem.validator.DocumentValidator;

/**
 *
 * @author shogo_saito
 */
public class CommutValidator extends DocumentValidator<CommutDocument> {

    @Override
    public List<Findings> doValidate(CommutDocument document) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Findings> doValidate(CommutDocument document, Staff inspector) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Findings> existDocument(CommutDocument document) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Findings> validateDocumentName(CommutDocument document, Staff inspector) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Findings> validateRegistrant(CommutDocument document, Staff inspector) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Findings> validateContents(CommutDocument document, Staff inspector) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Findings> getFindingsOfFileError(CommutDocument document) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isTargetOf(Document doc) {
        if (doc!= null) {
            return CommutConst.DocType.Commut == doc.getDocType();
        }
        return false;
    }
    
}
