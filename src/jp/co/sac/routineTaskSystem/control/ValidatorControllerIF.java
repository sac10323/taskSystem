/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.sac.routineTaskSystem.control;

import java.util.List;
import java.util.Map;
import jp.co.sac.routineTaskSystem.entity.document.Document;
import jp.co.sac.routineTaskSystem.entity.findings.Findings;
import jp.co.sac.routineTaskSystem.entity.staff.Staff;

/**
 *
 * @author shogo_saito
 */
public interface ValidatorControllerIF<T extends Document, F extends Findings> {
    public List<F> doValidate(T document, Staff inspector);
}
